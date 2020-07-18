import { AuthenticationService } from './authentication.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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

}
