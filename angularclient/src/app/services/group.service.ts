import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private apiUrl = 'http://localhost:8080/api/users/1';

  constructor(private http : HttpClient) { }

  getGroups(){

    var authJWT = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTU5NDg2NjA5MywidXNlcmlkIjoxLCJpYXQiOjE1OTQ4MzAwOTN9.XH3-kUMuwyaqZEdZS_WgdOTG7QvwL60XWi0l50QmhOc';
    
    console.log("Fetching users");

    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer '+authJWT);

    return this.http.get(
      this.apiUrl,
      {
        // headers : headers,
        responseType: 'text'
      }
    ).pipe(map(response => {
      let result = JSON.parse(response);
      return result;
    }));

  }
}
