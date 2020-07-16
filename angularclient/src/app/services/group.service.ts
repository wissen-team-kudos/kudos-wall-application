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

    console.log("Fetching users");

    let headers = new HttpHeaders();

    return this.http.get(
      this.apiUrl,
      {
        responseType: 'text'
      }
    ).pipe(map(response => {
      let result = JSON.parse(response);
      return result;
    }));

  }
}
