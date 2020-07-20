import { Group } from './../models/group';
import { Component, OnInit, Input, ViewChild, TemplateRef } from '@angular/core';
import { NgbModal, ModalDismissReasons, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';
import { KudosService } from '../services/kudos.service';
import { Kudos} from '../models/kudos';

@Component({
  selector: 'kudo-modal',
  templateUrl: './kudo-modal.component.html',
  styleUrls: ['./kudo-modal.component.css']
})

export class KudoModalComponent implements OnInit {
  closeResult = '';
  theKudo:Kudos;
  @Input() theGroup : Group;
  userlist : {
    user : {id: number, username: string, password: string},
    checked: boolean
  }[];
  isUserListEmpty: boolean;
  modalRef : NgbModalRef;
  @ViewChild('content', { static: true }) content;

  constructor(private modalService: NgbModal, private kudoService: KudosService) { }

  ngOnInit(): void {
    this.isUserListEmpty = false;
    this.userlist = new Array();
    console.log(this.theGroup)
    for (let index = 0; index < this.theGroup.users.length; index++) {
      this.userlist[index] = {user : this.theGroup.users[index], checked : false};
    }
  }

  ngAfterViewInit() {
    // this.content is NOW valid !!
 }

  open(content) {
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

    console.log(this.selectedOptions);
    // this.theKudo.content=form.value.name;
    // this.kudoService.addKudo(this.theKudo);
    for (let index = 0; index < this.theGroup.users.length; index++) {
      this.userlist[index] = {user : this.theGroup.users[index], checked : false};
    }
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
      this.modalRef.close();
      this.open(this.content);
    }
  }
}
