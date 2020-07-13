import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  clicked=false;
  kudos=[];
  groups=[];
  constructor() {
    this.kudos=["kudo1","kudo2","kudo3"];
   }

  ngOnInit(): void {
  }
  
  showKudos(){
    this.clicked=true;
    console.log(this.kudos)
  }

  showGroups(){
    this.clicked=true;
    this.groups=["group1","group2","group3"];
    console.log(this.groups)
  }
}
