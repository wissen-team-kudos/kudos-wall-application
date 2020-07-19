import { AuthenticationService } from './authentication.service';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
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
    this.http.get(this.url + "/" + id,
       {
        observe:'response',
        responseType: 'json'
       }  
    ).subscribe(response => {
      let user : User = <User>response.body;
      console.log(user);
    });
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
    this.http.get(this.url + "/username/" + username,
    {
      observe : 'response',
      responseType : 'json'
    }
    ).subscribe(response=> {
      let user : User = <User>response.body;
      console.log(user);
    });
  }

  /*
    ReturnType : HttpResponse
    if groupname and password are valid combination: status=200
    else if groupname does not exist : status=404
    else if password is invalid: status=401
    else if user already present in group: status=400 
  */
  addGroupToUser(userid: number, groupname: string, groupPassword: string) {
    this.http.put(this.url + "/groupname/" + userid,
      {
        groupname: groupname,
        password: groupPassword
      },
      {
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe(response => {
      if(response.status==404){
        console.log("Not found: Invalid groupname");
      }
      else if(response.status==401){
        console.log("Not auhorized: invalid password");
      }
      else if(response.status==400){
        console.log("Bad request. user already present in group");
      }
      else{
        let user : User = <User> response.body;
        console.log(user);
      }
      
    });
  }
}
