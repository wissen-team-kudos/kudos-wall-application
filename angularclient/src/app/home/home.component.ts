import { Component, OnInit } from '@angular/core';
import { SampleGroupService } from '../dummy-services/sample-group.service';
import { SampleKudoService } from '../dummy-services/sample-kudo.service';
import { GroupService } from '../services/group.service';
import { Group } from '../models/group';
import { User } from '../models/user';
import { Observable, forkJoin, EMPTY } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { delay, map } from 'rxjs/operators';
import { AuthenticationService } from '../services/authentication.service';
import { SharedService } from '../shared/shared.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentJustify='fill'
  clicked=false;

  kudos:String[];

  userGroups : Group[]=[];

  constructor(private groupService: GroupService,
              private authService: AuthenticationService,
              private sharedService: SharedService,
              private sampleGroupService: SampleGroupService,
              private sampleKudoService: SampleKudoService) {

    this.kudos= this.sampleKudoService.getKudos();
  }


  ngOnInit(): void {  }
  
  showKudos(){
    this.clicked=true;
    
    this.kudos= this.sampleKudoService.getKudos();
    console.log(this.kudos)
  }

  showGroups(){
    this.clicked=true;

//////////////////// TESTING OF GROUP APIS /////////////////////////

    // this.groupService.getGroup(1);
    // this.groupService.getAllGroups();

    // let group : Group={
    //   groupname : 'group10',
    //   password : 'pass10'
    // };
    // this.groupService.addGroup(group);

    // let group : Group={
    //   id: 3,
    //   groupname : 'group15',
    //   password : 'pass15'
    // };
    // this.groupService.updateGroup(group);

    // this.groupService.deleteGroup(13);
    // this.groupService.deleteGroup(14);

//////////////////////////////////////////////////////////////

    let userId : number = this.authService.CurrentUserId();

    this.userGroups=[];

    let sub = this.groupService.getUser(userId)
    .subscribe(response => {
      let user : User= <User>response.body;
      let groupList : Group[] = <Group[]>user.groups;

      groupList.forEach(group => {
        this.groupService.getGroup(group.id)
        .subscribe(response => {
          this.userGroups.push(<Group>response.body)
        });
      });

      console.log(this.userGroups);
    });

    this.sharedService.groupAdded
    .subscribe(response => {
        this.userGroups.push(response);
        console.log(this.userGroups);
    });
  }

  onShare(groupname: String){
    alert("Sharing "+groupname);
  }

}
