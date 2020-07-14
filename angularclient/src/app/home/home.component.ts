import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentJustify='fill'
  clicked=false;
  kudos=[];
  rooms=[];
  constructor() {
    this.kudos=["kudo1","kudo2","kudo3"];
   }

  ngOnInit(): void {
  }
  
  showKudos(){
    this.clicked=true;
    console.log(this.kudos)
  }

  showRooms(){
    this.clicked=true;
    this.rooms=["room1","room2","room3","room4"];
    console.log(this.rooms)
  }

  addRooms(room){
    this.rooms.push(room);
    console.log(this.rooms);
  }
}
