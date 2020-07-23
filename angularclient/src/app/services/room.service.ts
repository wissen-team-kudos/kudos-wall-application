import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Room } from '../models/room';
import { User } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private userServiceApiUrl = 'http://localhost:8080/api/users/';
  private url= 'http://localhost:8080/api/rooms';


  constructor(private http : HttpClient) { }

  getUser(id: number){
    return this.http.get(this.userServiceApiUrl +  id,
      {
      observe:'response',
      responseType: 'json'
      }  
    );
  }

  getRoom(id: number) {
   
    return this.http.get(this.url + "/" + id,
      {
        observe : 'response',
        responseType: 'json'
      }
    );
  }

  getAllRooms(){
   
    this.http.get(this.url,
      {
        observe : 'response',
        responseType: 'json'
      }
    ).subscribe(response =>{
      let room : Room = <Room>response.body;
      console.log(room);
    });
  }

  addRoom(room : Room){

    return this.http.post(this.url,room,
      {
        observe : 'response',
        responseType: 'json'
      }
    );
  }
  
  updateRoom(room : Room){
    
    return this.http.put(this.url,room,
      {
        observe : 'response',
        responseType: 'json'
      }
    );
  }

  deleteRoom(id: number){
   
    return this.http.delete(this.url + "/" + id,
      {
        observe : 'response',
        responseType: 'text'
      }
    );
  }

  addUserToRoom(userid: number, roomname: string, roomPassword: string) {
    this.http.put(this.url + "/roomname/" + userid,
      {
        roomname: roomname,
        password: roomPassword
      },
      {
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe((response :HttpResponse<Room>) => {
      let room : Room = <Room> response.body;
      console.log(room);      
    },
    (error : HttpErrorResponse)=>{
      if(error.status==404){
        console.log("Not found: Invalid roomname");
      }
      else if(error.status==401){
        console.log("Not auhorized: invalid password");
      }
      else if(error.status==400){
        console.log("Bad request. user already present in room");
      }
    });
  }

}
