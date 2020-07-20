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
    ) { }

  getKudo(id:number){
    return this.http.get(this.url + "/" + id,
       {
        observe:'response',
        responseType: 'json'
       }  
    ).subscribe(response => {
      let kudo : Kudos = <Kudos>response.body;
      console.log(kudo);
    });
  }

  getAllKudos(){
    return this.http.get(this.url,
       {
        observe:'response',
        responseType: 'json'
       }  
    ).subscribe(response => {
      let kudos : Kudos[] = <Kudos[]> response.body;
      console.log(kudos);
    });
  }

  addKudo(kudo : Kudos) {
    return this.http.post(this.url, kudo,
      {
        observe : 'response',
        responseType : 'json'
      }
    )
  }

  updateKudo(kudo : Kudos) {
    return this.http.put(this.url, kudo,
      {
        observe : 'response',
        responseType : 'json'
      }
    ).subscribe(response => {
      let kudo : Kudos = <Kudos>response.body;
      console.log(kudo);
    });
  }

  deleteKudo(id : number) {
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
