import { AuthenticationService } from './authentication.service';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = "http://localhost:8080/api/users";

  constructor(
    private http: HttpClient,
    private authService: AuthenticationService
    ) { }

  getUser(id:number){
    return this.http.get(this.url + "/" + id,
       {
        observe:'response',
        responseType: 'json'
       }  
    );
  }

  getAllUsers(){
    this.http.get(this.url,
       {
        observe:'response',
        responseType: 'json'
       }  
    ).subscribe(response => {
      let user : User[] = <User[]> response.body;
      console.log(user);
    });
  }

  addUser(user : User) {
    this.http.post(this.url, user,
      {
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe(response => {
      let user : User = <User> response.body;
      console.log(user);
    });
  }

  updateUser(user : User) {
    this.http.put(this.url, user,
      {
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe(response => {
      let user : User = <User>response.body;
      console.log(user);
    });
  }

  deleteUser(id : number) {
    this.http.delete(this.url + '/' + id,
      {
        observe : 'response',
        responseType : 'text'
      }
    ).subscribe(response => {
      console.log(response);
    })
  }

  getUserByUsername(username: string) {
 	return   this.http.get(this.url + "/username/" + username,
    {
      observe : 'response',
      responseType : 'json'
    } )
  }

  /*
    ReturnType : HttpResponse
    if roomname and password are valid combination: status=200
    else if roomname does not exist : status=404
    else if password is invalid: status=401
    else if user already present in room: status=400 
  */
  addRoomToUser(userid: number, roomname: string, roomPassword: string) {
    return this.http.put(this.url + "/roomname/" + userid,
      {
        roomname: roomname,
        password: roomPassword
      },
      {
        observe : 'response',
        responseType : 'json'
      }
    );
    // .subscribe((response :HttpResponse<User>) => {
    //     let user : User = <User> response.body;
    //     console.log(user);      
    // },
    // (error : HttpErrorResponse)=>{
    //   if(error.status==404){
    //     console.log("Not found: Invalid roomname");
    //   }
    //   else if(error.status==401){
    //     console.log("Not auhorized: invalid password");
    //   }
    //   else if(error.status==400){
    //     console.log("Bad request. user already present in room");
    //   }
    // });
  }
	getKudosOfUser(id:number){
		return this.http.get(this.url+ "/userid/"+ id ,
		 {
    	    observe:'response',
        	responseType: 'json'
	     }  
		);
}
}
