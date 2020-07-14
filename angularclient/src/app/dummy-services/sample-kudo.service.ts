import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SampleKudoService {

  kudos: String[];

  constructor() { 
    this.kudos=["kudo1","kudo2","kudo3"];

  }

  getKudos(){
    return this.kudos;
  }
  
  addKudo(kudo: String){
    this.kudos.push(kudo);
    console.log(this.kudos);
  }
}
