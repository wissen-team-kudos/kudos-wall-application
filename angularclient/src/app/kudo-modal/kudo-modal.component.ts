import { Component, OnInit } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';
import { KudosService } from '../services/kudos.service';
import { Kudos} from '../models/kudos-interface';

@Component({
  selector: 'kudo-modal',
  templateUrl: './kudo-modal.component.html',
  styleUrls: ['./kudo-modal.component.css']
})

export class KudoModalComponent implements OnInit {
  closeResult = '';
	theKudo:Kudos;

  constructor(private modalService: NgbModal, private kudoService: KudosService) { }

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

    this.theKudo.content=form.value.name;
    this.kudoService.addKudo(theKudo);
  }
}
