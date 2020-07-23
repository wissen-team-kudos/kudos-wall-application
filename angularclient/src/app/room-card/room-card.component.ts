import { HttpResponse } from '@angular/common/http';
import { RoomService } from './../services/room.service';
import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Room } from '../models/room';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'room-card',
  templateUrl: './room-card.component.html',
  styleUrls: ['./room-card.component.css']
})
export class RoomCardComponent implements OnInit {

  closeResult = '';
  
  @Input() theRoom : Room;
  @Output('userLeft') userLeft = new EventEmitter();

  constructor(private modalService: NgbModal,
    private authService: AuthenticationService,
    private roomService: RoomService) { }

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

  onShare(roomname: String){
    alert("Sharing "+roomname);
  }

  leaveRoom(){
    console.log("Leave room");
    console.log(this.theRoom);
    for(let i = 0; i < this.theRoom.users.length; i++){
      if(this.theRoom.users[i].id == this.authService.CurrentUserId()){
        this.theRoom.users.splice(i, 1);
        break;
      }
    }
    console.log(this.theRoom);
    this.roomService.updateRoom(this.theRoom)
    .subscribe((response: HttpResponse<Room>) => {
      this.userLeft.emit();
      this.theRoom = response.body;
      if(this.theRoom.users == null){
        this.roomService.deleteRoom(this.theRoom.id)
        .subscribe((response: HttpResponse<string>) =>{
          console.log("Deleted Room ", this.theRoom.id);
        });
      }
    });
  }
}
