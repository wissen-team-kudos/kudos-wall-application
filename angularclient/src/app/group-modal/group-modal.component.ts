import { Component, OnInit } from '@angular/core';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';
import { SampleGroupService } from '../dummy-services/sample-group.service';
import { GroupService } from '../services/group.service';
import { Group } from '../models/group';
import { map, delay } from 'rxjs/operators';
import { User } from '../models/user';
import { AuthenticationService } from '../services/authentication.service';
import { SharedService } from '../shared/shared.service';

@Component({
  selector: 'group-modal',
  templateUrl: './group-modal.component.html',
  styleUrls: ['./group-modal.component.css'],
})
export class GroupModalComponent {
  closeResult = '';

  constructor(private modalService: NgbModal, 
              private groupService: GroupService,
              private authService: AuthenticationService,
              private sharedService: SharedService,
              private sampleGroupService: SampleGroupService) {}

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
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

    let userId : number = this.authService.CurrentUserId();

    let group : Group = {
      groupname : form.value.name,
      password : form.value.password,
      users : [
        {
          id: 1,  
          username : 'user1',
          password : 'pass1'
        }
      ]
    };

    if(value == 0)
    console.log("Joining Group "+group.groupname);

    if(value == 1){
      
      console.log("Creating Group "+group.groupname);

      this.groupService.addGroup(group)
      .subscribe(response =>{
        let group : Group = <Group>response.body;
        console.log(group);
        this.sharedService.groupAdded.next(group);
      });
      
      // this.groupService.getUser(userId)
      // .subscribe(response => {
      //   let user : User = <User>response.body; 
      //   console.log(user);

      //   let group : Group = {
      //     groupname : form.value.name,
      //     password : form.value.password,
      //     users : [
      //       {
      //         id: user.id,  
      //         username : user.username,
      //         password : user.password
      //       }
      //     ]
      //   }
      //   console.log(group)
      // });

      // this.groupService.getUser(userId)
      // .pipe(map(response => {
      //   r
      // }))
    }
  }

}