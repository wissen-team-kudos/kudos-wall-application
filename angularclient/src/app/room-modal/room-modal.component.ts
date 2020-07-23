import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import {NgbModal, ModalDismissReasons, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';
import { RoomService } from '../services/room.service';
import { Room } from '../models/room';
import { map, delay } from 'rxjs/operators';
import { User } from '../models/user';
import { AuthenticationService } from '../services/authentication.service';
import { SharedService } from '../shared/shared.service';
import { UserService } from '../services/user.service';
import { error } from 'protractor';

@Component({
  selector: 'room-modal',
  templateUrl: './room-modal.component.html',
  styleUrls: ['./room-modal.component.css'],
})
export class RoomModalComponent {
  closeResult = '';
  private modalRef: NgbModalRef;
  formError : {
    roomnameExists : boolean,
    userAlreadyPresent : boolean,
    roomnameNotFound : boolean,
    passwordError : boolean
  };

  constructor(private modalService: NgbModal, 
              private roomService: RoomService,
              private userService: UserService,
              private authService: AuthenticationService,
              private sharedService: SharedService) {
                this.formError = {
                  roomnameExists : false,
                  userAlreadyPresent : false,
                  roomnameNotFound : false,
                  passwordError : false
                };
              }

  open(content) {
    this.formError = {
      roomnameExists : false,
      userAlreadyPresent : false,
      roomnameNotFound : false,
      passwordError : false
    };
    this.modalRef = this.modalService.open(content, {size: 'sm',centered:true, ariaLabelledBy: 'modal-basic-title'});
    this.modalRef.result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  onSubmit(form: NgForm, value: number){

    this.formError = {
      roomnameExists : false,
      userAlreadyPresent : false,
      roomnameNotFound : false,
      passwordError : false
    };
    let userId : number = this.authService.CurrentUserId();

    let room : Room = {
      roomname : form.value.name,
      password : form.value.password
    };

    if(value == 0){
    
      console.log("Joining Room "+room.roomname);

      this.userService.addRoomToUser(userId,room.roomname,room.password)
      .subscribe(response =>{
        this.modalRef.close();
        let user : User = <User>response.body
        let userRoom : Room = <Room>user.rooms.slice(-1)[0];
        
        this.roomService.getRoom(userRoom.id)
        .subscribe(response =>{
          let newRoom : Room = <Room>response.body;
          console.log(newRoom);
          this.sharedService.roomAdded.next(newRoom);
        })
      },
      (error : HttpErrorResponse)=>{
        if(error.status==404){
          this.formError.roomnameNotFound = true;
        }
        else if(error.status==401){
          this.formError.passwordError = true;
        }
        else if(error.status==400){
          this.formError.userAlreadyPresent = true;
        }
      });
    }

    if(value == 1){
      
      console.log("Creating Room "+room.roomname);

      this.userService.getUser(userId)
      .subscribe(response => {
        let user : User = <User>response.body; 
        room.users = [{
          id: user.id,
          username: user.username,
          password: user.password
        }];

        this.roomService.addRoom(room)
        .subscribe(response => {
          this.modalRef.close();
          let newRoom : Room = <Room>response.body;
          console.log(newRoom);
          this.sharedService.roomAdded.next(newRoom);
        },
        (error: HttpErrorResponse)=>{
          if(error.status==400){
            this.formError.roomnameExists = true;
          }
        });
      });
    }
  }

}