import { Component, OnInit, Input, ViewChild, TemplateRef } from '@angular/core';
import { NgbModal, ModalDismissReasons, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';
import { KudosService } from '../services/kudos.service';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
import { Kudos} from '../models/kudos';
import { Room} from '../models/room';
import { User} from '../models/user';

@Component({
  selector: 'kudo-modal',
  templateUrl: './kudo-modal.component.html',
  styleUrls: ['./kudo-modal.component.css']
})

export class KudoModalComponent implements OnInit {
  closeResult = '';
  theKudo:Kudos;
  @Input() theRoom : Room;
  userlist : {
    user : {id: number, username: string, password: string},
    checked: boolean
  }[];
  isUserListEmpty: boolean;
  modalRef : NgbModalRef;
  @ViewChild('send_kudo_form', { static: true }) send_kudo_form;

  newuserList : {id: number, username: string, password: string}[];
  
	constructor(private modalService: NgbModal, 
				private kudoService: KudosService,
				private authService: AuthenticationService,
				private userService: UserService) { }

  ngOnInit(): void {
    this.isUserListEmpty = false;
    this.cleanUserlist();
    console.log(this.userlist);
  }

  ngAfterViewInit() {
    // this.content is NOW valid !!
 }

  open(content) {
    this.cleanUserlist();
    this.modalRef = this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
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


  onSubmit(form: NgForm){
	let theKudo:Kudos={content:"", users:[], rooms:[], author:null};

	theKudo.content=form.value.content;
	
    let userId : number = this.authService.CurrentUserId();
    let selectedUsersList = this.newuserList;
	  this.userService.getUser(userId)	
        .subscribe(response => {
          let newuser : User = <User>response.body;
          theKudo.author={
							id: newuser.id,
							username: newuser.username,
							password: newuser.password
								};
    
    console.log(selectedUsersList)
    theKudo.users=selectedUsersList;
    console.log(theKudo)

		theKudo.rooms=[];
		theKudo.rooms.push(
    	    {
        	  id: this.theRoom.id,
	          roomname: this.theRoom.roomname,
			  password: this.theRoom.password
        });
        
    console.log("the kudo to be added ");
    this.kudoService.addKudo(theKudo)
        .subscribe(response => {
            let newKudo : Kudos = <Kudos>response.body;
            console.log(newKudo);
          });
        });
        console.log(this.selectedOptions);
    // this.theKudo.content=form.value.name;
    // this.kudoService.addKudo(this.theKudo);
    this.cleanUserlist();
  }

  get selectedOptions() { // right now: ['1','3']
    return this.userlist
              .filter(opt => opt.checked)
              .map(opt => opt.user)
  }

  testIfEmpty(){
    if(this.selectedOptions.length == 0) {
      this.isUserListEmpty = true;
    }
    else{
      this.isUserListEmpty = false;
      this.newuserList= Object.assign([], this.selectedOptions);
      this.modalRef.close();
      this.open(this.send_kudo_form);
    }
		
  }

  cleanUserlist(){
    this.userlist = new Array();
    for (let index = 0; index < this.theRoom.users.length; index++) {
      if(this.theRoom.users[index].username != this.authService.CurrentUsername())
        this.userlist.push({user : this.theRoom.users[index], checked : false});
    }
    this.isUserListEmpty=false;
  }
}
