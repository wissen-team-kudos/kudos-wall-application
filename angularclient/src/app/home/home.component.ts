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
    /*Author: Mandar --- (For testing User service)
    this.userService.getUser(1);
    this.userService.getAllUsers();
    let user : User = {
      username : 'user4',
      password : 'pass4'
    };
    this.userService.addUser(user);
    let userToUpdate : User = {
      id : 6,
      username : 'user4',
      password : 'pass5'
    }
    this.userService.updateUser(userToUpdate);
    this.userService.deleteUser(6);
    */

    let userId : number = this.authService.CurrentUserId();
   
	this.userService.getKudosOfUser(userId)
	.subscribe(response=>{
	this.kudos=<Kudos[]>response.body;
    console.log(this.kudos)
	});
  }

  showRooms(){
    this.clicked=true;

//////////////////// TESTING OF ROOM APIS /////////////////////////

    // this.roomService.getRoom(1);
    // this.roomService.getAllRooms();

    // let room : Room={
    //   roomname : 'room10',
    //   password : 'pass10'
    // };
    // this.roomService.addRoom(room);

    // let room : Room={
    //   id: 3,
    //   roomname : 'room15',
    //   password : 'pass15'
    // };
    // this.roomService.updateRoom(room);

    // this.roomService.deleteRoom(13);
    // this.roomService.deleteRoom(14);

//////////////////////////////////////////////////////////////

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
  
}
