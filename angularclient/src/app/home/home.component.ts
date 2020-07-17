import { Component, OnInit } from '@angular/core';
import { SampleGroupService } from '../dummy-services/sample-group.service';
import { SampleKudoService } from '../dummy-services/sample-kudo.service';
import { GroupService } from '../services/group.service';
import { Group } from '../models/group';
import { User } from '../models/user';
import { Observable, forkJoin } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { delay, map } from 'rxjs/operators';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentJustify='fill'
  clicked=false;

  kudos:String[];

  userGroupsObsv : Observable<Group[]>;
  userGroupsObsvArray : Observable<Group>[] = [];

  constructor(private groupService: GroupService,
              private authService: AuthenticationService,
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

    this.groupService.getUser(userId)
    .subscribe(response => {
      let user : User = <User>response.body;
      let groupList : Group[] = <Group[]> user.groups;

      this.userGroupsObsvArray=[];
      groupList.forEach(group => {
        this.userGroupsObsvArray.push(this.groupService.getGroupObsv(group.id));
      })

      this.userGroupsObsv = forkJoin(this.userGroupsObsvArray);
      console.log(this.userGroupsObsv)
    });

    // //////////////////////////////////////
    // let group : Group={
    //   groupname : 'group10',
    //   password : 'pass10',
    //   users :[
    //     {
    //       id : 1,
    //       username: 'user1',
    //       password: 'pass1'
    //     }
    //   ]
    // };

    // delay(5000);

    // //////////////////////////////////////////
    // this.groupService.addGroup(group);

  }

  onShare(groupname: String){
    alert("Sharing "+groupname);
  }
}
