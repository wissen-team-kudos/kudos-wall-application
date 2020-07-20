
import { Component, OnInit, Input } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';
import { KudosService } from '../services/kudos.service';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
import { Kudos} from '../models/kudos';
import { Group} from '../models/group';
import { User} from '../models/user';
import { SampleUserService } from '../dummy-services/sample-user.service';

@Component({
  selector: 'kudo-modal',
  templateUrl: './kudo-modal.component.html',
  styleUrls: ['./kudo-modal.component.css']
})

export class KudoModalComponent implements OnInit {
	closeResult = '';
	@Input() currentGroup:Group;

	constructor(private modalService: NgbModal, 
				private kudoService: KudosService,
				private authService: AuthenticationService,
				private sampleUserService: SampleUserService,
				private userService: UserService) { }

  ngOnInit(): void {
  }

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


  onSubmit(form: NgForm){
	let theKudo:Kudos={content:"", users:[], groups:[], author:null};

	theKudo.content=form.value.content;
	
    let userId : number = this.authService.CurrentUserId();

	this.userService.getUser(userId)	
        .subscribe(response => {
          let newuser : User = <User>response.body;
          theKudo.author={
							id: newuser.id,
							username: newuser.username,
							password: newuser.password
								};
	
		theKudo.users=[
							{ 	id: 1,
								username: "user1",
								password: "pass1"
							}
							];

		theKudo.groups=[];
		theKudo.groups.push(
    	    {
        	  id: this.currentGroup.id,
	          groupname: this.currentGroup.groupname,
			  password: this.currentGroup.password
			  });

		console.log("the kudo to be added ");
		this.kudoService.addKudo(theKudo)
				.subscribe(response => {
	          let newKudo : Kudos = <Kudos>response.body;
    	      console.log(newKudo);
        	});
        });
  }
}
