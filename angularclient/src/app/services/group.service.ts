import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Group } from '../models/group';
import { User } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private userServiceApiUrl = 'http://localhost:8080/api/users/';
  private url= 'http://localhost:8080/api/groups';


  constructor(private http : HttpClient) { }

  getUser(id: number){
    return this.http.get(this.userServiceApiUrl +  id,
      {
      observe:'response',
      responseType: 'json'
      }  
    );
  }

  getGroup(id: number) {
   
    return this.http.get(this.url + "/" + id,
      {
        observe : 'response',
        responseType: 'json'
      }
    );
  }

  getAllGroups(){
   
    this.http.get(this.url,
      {
        observe : 'response',
        responseType: 'json'
      }
    ).subscribe(response =>{
      let group : Group = <Group>response.body;
      console.log(group);
    });
  }

  addGroup(group : Group, user? : User){
    
    if(user){
      group.users = [{
        id: user.id,
        username: user.username,
        password: user.password
      }];
    }

    return this.http.post(this.url,group,
      {
        observe : 'response',
        responseType: 'json'
      }
    );
  }
  
  updateGroup(group : Group){
    
    this.http.put(this.url,group,
      {
        observe : 'response',
        responseType: 'json'
      }
    ).subscribe(response =>{
      let group : Group = <Group>response.body;
      console.log(group);
    });
  }

  deleteGroup(id: number){
   
    this.http.delete(this.url + "/" + id,
      {
        observe : 'response',
        responseType: 'text'
      }
    ).subscribe(response =>{
      console.log(response);
    });
  }

}
