import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SampleGroupService {

  rooms: String[];
  
  constructor() { 
    this.rooms=["room1","room2","room3","room4"];
  }

  getRooms(){
    return this.rooms;
  }

  addRoom(room: String){
    this.rooms.push(room);
    console.log(this.rooms);
  }
}
