import { Component, OnInit } from '@angular/core';
import { SampleGroupService } from '../dummy-services/sample-group.service';
import { SampleKudoService } from '../dummy-services/sample-kudo.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentJustify='fill'
  clicked=false;

  kudos:String[];
  groups:String[];

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

  showGroups(){
    this.clicked=true;

    this.groups= this.sampleGroupService.getGroups();
    console.log(this.groups)
  }

  onShare(group){
    alert("Sharing "+group);
  }
}
