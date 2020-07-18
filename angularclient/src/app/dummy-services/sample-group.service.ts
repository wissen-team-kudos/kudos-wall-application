import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SampleGroupService {

  groups: String[];
  
  constructor() { 
    this.groups=["room1","room2","room3","room4"];
  }

  getGroups(){
    return this.groups;
  }

  addGroup(group: String){

    if(group!="")
      this.groups.push(group);
    console.log(this.groups);
  }
}
