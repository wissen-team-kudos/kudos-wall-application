import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import {NgbModal, ModalDismissReasons, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';
import { SampleGroupService } from '../dummy-services/sample-group.service';
import { GroupService } from '../services/group.service';
import { Group } from '../models/group';
import { map, delay } from 'rxjs/operators';
import { User } from '../models/user';
import { AuthenticationService } from '../services/authentication.service';
import { SharedService } from '../shared/shared.service';
import { UserService } from '../services/user.service';
import { error } from 'protractor';

@Component({
  selector: 'group-modal',
  templateUrl: './group-modal.component.html',
  styleUrls: ['./group-modal.component.css'],
})
export class GroupModalComponent {
  closeResult = '';
  private modalRef: NgbModalRef;
  formError : {
    groupnameExists : boolean,
    userAlreadyPresent : boolean,
    groupnameNotFound : boolean,
    passwordError : boolean
  };

  constructor(private modalService: NgbModal, 
              private groupService: GroupService,
              private userService: UserService,
              private authService: AuthenticationService,
              private sharedService: SharedService,
              private sampleGroupService: SampleGroupService) {
                this.formError = {
                  groupnameExists : false,
                  userAlreadyPresent : false,
                  groupnameNotFound : false,
                  passwordError : false
                };
              }

  open(content) {
    this.modalRef = this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'})
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
      groupnameExists : false,
      userAlreadyPresent : false,
      groupnameNotFound : false,
      passwordError : false
    };
    let userId : number = this.authService.CurrentUserId();

    let group : Group = {
      groupname : form.value.name,
      password : form.value.password
    };

    if(value == 0){
    
      console.log("Joining Group "+group.groupname);

      this.userService.addGroupToUser(userId,group.groupname,group.password)
      .subscribe(response =>{
        this.modalRef.close();
        let user : User = <User>response.body
        let userGroup : Group = <Group>user.groups.slice(-1)[0];
        
        this.groupService.getGroup(userGroup.id)
        .subscribe(response =>{
          let newGroup : Group = <Group>response.body;
          console.log(newGroup);
          this.sharedService.groupAdded.next(newGroup);
        })
      },
      (error : HttpErrorResponse)=>{
        if(error.status==404){
          this.formError.groupnameNotFound = true;
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
      
      console.log("Creating Group "+group.groupname);

      // this.groupService.addGroup(group)
      // .subscribe(response =>{
      //   let group : Group = <Group>response.body;
      //   console.log(group);
      //   this.sharedService.groupAdded.next(group);
      // });
      
      this.userService.getUser(userId)
      .subscribe(response => {
        let user : User = <User>response.body; 
        group.users = [{
          id: user.id,
          username: user.username,
          password: user.password
        }];

        this.groupService.addGroup(group)
        .subscribe(response => {
          this.modalRef.close();
          let newGroup : Group = <Group>response.body;
          console.log(newGroup);
          this.sharedService.groupAdded.next(newGroup);
        },
        (error: HttpErrorResponse)=>{
          if(error.status==400){
            this.formError.groupnameExists = true;
          }
        });
      });
    }
  }

}