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
    // let headers = new HttpHeaders();
    // let token = localStorage.getItem('token');
    // let tokenHeader = "Bearer " + token;
    // headers = headers.set("Authorization", tokenHeader);
    // console.log(headers.get("Authorization"));
    this.http.get(this.url + "/" + id,
       {
        // headers: headers,
        observe:'response',
        responseType: 'json'
       }  
    ).subscribe(response => {
      let user : User = <User>response.body;
      console.log(user);
    });
  }

  getAllUsers(){
    // let headers = new HttpHeaders();
    // let token = localStorage.getItem('token');
    // let tokenHeader = "Bearer " + token;
    // headers = headers.set("Authorization", tokenHeader);
    // console.log(headers.get("Authorization"));
    this.http.get(this.url,
       {
        // headers: headers,
        observe:'response',
        responseType: 'json'
       }  
    ).subscribe(response => {
      let user : User[] = <User[]> response.body;
      console.log(user);
    });
  }

  addUser(user : User) {
    // let headers = new HttpHeaders();
    // let token = localStorage.getItem('token');
    // let tokenHeader = 'Bearer ' + token;
    // headers = headers.set('Authorization', tokenHeader);
    this.http.post(this.url, user,
      {
        // headers : headers,
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe(response => {
      let user : User = <User> response.body;
      console.log(user);
    });
  }

  updateUser(user : User) {
    // let headers = new HttpHeaders();
    // let token = localStorage.getItem('token');
    // let tokenHeader = 'Bearer ' + token;
    // headers = headers.set('Authorization', tokenHeader);
    this.http.put(this.url, user,
      {
        // headers : headers,
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe(response => {
      let user : User = <User>response.body;
      console.log(user);
    });
  }

  deleteUser(id : number) {
    // let headers = new HttpHeaders();
    // let token = localStorage.getItem('token');
    // let tokenHeader = 'Bearer ' + token;
    // headers = headers.set('Authorization', tokenHeader);
    this.http.delete(this.url + '/' + id,
      {
        // headers : headers,
        observe : 'response',
        responseType : 'text'
      }
    ).subscribe(response => {
      console.log(response);
    })
  }

}