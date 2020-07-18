import { AuthenticationService } from './authentication.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Kudos } from '../models/kudos';

@Injectable({
  providedIn: 'root'
})
export class KudosService {

  private url: string = "http://localhost:8080/api/kudos";

  constructor(
    private http: HttpClient,
    private authService: AuthenticationService
    ) { }

  getKudo(id:number){
    let headers = new HttpHeaders();
    let token = localStorage.getItem('token');
    let tokenHeader = "Bearer " + token;
    headers = headers.set("Authorization", tokenHeader);
    console.log(headers.get("Authorization"));
    this.http.get(this.url + "/" + id,
       {
        headers: headers,
        observe:'response',
        responseType: 'json'
       }  
    ).subscribe(response => {
      let kudo : Kudos = <Kudos>response.body;
      console.log(kudo);
    });
  }

  getAllKudos(){
    let headers = new HttpHeaders();
    let token = localStorage.getItem('token');
    let tokenHeader = "Bearer " + token;
    headers = headers.set("Authorization", tokenHeader);
    console.log(headers.get("Authorization"));
    this.http.get(this.url,
       {
        headers: headers,
        observe:'response',
        responseType: 'json'
       }  
    ).subscribe(response => {
      let kudos : Kudos[] = <Kudos[]> response.body;
      console.log(kudos);
    });
  }

  addKudo(kudo : Kudos) {
    let headers = new HttpHeaders();
    let token = localStorage.getItem('token');
    let tokenHeader = 'Bearer ' + token;
    headers = headers.set('Authorization', tokenHeader);
    this.http.post(this.url, kudo,
      {
        headers : headers,
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe(response => {
      let kudo : Kudos = <Kudos> response.body;
      console.log(kudo);
    });
  }

  updateKudo(kudo : Kudos) {
    let headers = new HttpHeaders();
    let token = localStorage.getItem('token');
    let tokenHeader = 'Bearer ' + token;
    headers = headers.set('Authorization', tokenHeader);
    this.http.put(this.url, kudo,
      {
        headers : headers,
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe(response => {
      let kudo : Kudos = <Kudos>response.body;
      console.log(kudo);
    });
  }

  deleteKudo(id : number) {
    let headers = new HttpHeaders();
    let token = localStorage.getItem('token');
    let tokenHeader = 'Bearer ' + token;
    headers = headers.set('Authorization', tokenHeader);
    this.http.delete(this.url + '/' + id,
      {
        headers : headers,
        observe : 'response',
        responseType : 'text'
      }
    ).subscribe(response => {
      console.log(response);
    })
  }
}
