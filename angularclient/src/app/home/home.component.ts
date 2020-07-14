import { Component, OnInit } from '@angular/core';
import { SampleGroupService } from '../sample-group.service';
import { SampleKudoService } from '../sample-kudo.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentJustify='fill'
  clicked=false;

  kudos:String[];
  rooms:String[];

  constructor(private sampleGroupService: SampleGroupService,private sampleKudoService: SampleKudoService) {

    this.kudos= this.sampleKudoService.getKudos();
   }

  ngOnInit(): void {
  }
  
  showKudos(){
    this.clicked=true;
    
    this.kudos= this.sampleKudoService.getKudos();
    console.log(this.kudos)
  }

  showRooms(){
    this.clicked=true;

    this.rooms= this.sampleGroupService.getRooms();
    console.log(this.rooms)
  }

  onShare(room){
    alert("Sharing "+room);
  }
}
