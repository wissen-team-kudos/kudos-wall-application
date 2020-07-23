import { UserService } from './../services/user.service';
import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { KudosService } from '../services/kudos.service';
import { KudoCardComponent } from '../kudo-card/kudo-card.component';
import { Kudos } from '../models/kudos';
import { RoomService } from '../services/room.service';
import { Room } from '../models/room';
import { User } from '../models/user';
import { Observable, forkJoin, EMPTY } from 'rxjs';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { delay, map } from 'rxjs/operators';
import { SharedService } from '../shared/shared.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentJustify='fill'
  clicked=false;

  kudos:Kudos[]=[];
  userRooms : Room[]=[];
 

  constructor(private roomService: RoomService,
    private authService: AuthenticationService,
    private sharedService: SharedService,
    private userService:UserService,
    private kudosService:KudosService,
				) {
  }


  ngOnInit(): void { 
    this.showKudos();
    this.showRooms();
   }
  
  showKudos(){
    this.clicked=true;
    let userId : number = this.authService.CurrentUserId();
   
	this.userService.getKudosOfUser(userId)
	.subscribe(response=>{
	this.kudos=<Kudos[]>response.body;
    console.log(this.kudos)
	});
  }

  showRooms(){
    this.clicked=true;
    let userId : number = this.authService.CurrentUserId();

    this.userRooms=[];

    let sub = this.userService.getUser(userId)
    .subscribe(response => {
      let user : User= <User>response.body;
      let roomList : Room[] = <Room[]>user.rooms;

      roomList.forEach(room => {
        this.roomService.getRoom(room.id)
        .subscribe(response => {
          this.userRooms.push(<Room>response.body)
        });
      });

      this.sharedService.roomAdded
      .subscribe(newRoom => {
        if(!this.userRooms.includes(newRoom)){
          this.userRooms.push(newRoom);
          console.log(this.userRooms);
        }
      });
     
      console.log(this.userRooms);
    });
  }

  refresh(){
    this.showKudos();
    this.showRooms();
  }
  
}
