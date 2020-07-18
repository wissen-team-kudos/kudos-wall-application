import { UserService } from './../services/user.service';
import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { SampleGroupService } from '../dummy-services/sample-group.service';
import { SampleKudoService } from '../dummy-services/sample-kudo.service';
import { KudosService } from '../services/kudos.service';
import { KudoCardComponent } from '../kudo-card/kudo-card.component';
import { Kudos } from '../models/kudos';
import { GroupService } from '../services/group.service';
import { Group } from '../models/group';
import { User } from '../models/user';
import { Observable, forkJoin, EMPTY } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { delay, map } from 'rxjs/operators';
import { SharedService } from '../shared/shared.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentJustify='fill'
  clicked=false;

  kudos:Kudos[];
  userGroups : Group[]=[];
 

  constructor(private groupService: GroupService,
    private authService: AuthenticationService,
    private sharedService: SharedService,
    private sampleGroupService: SampleGroupService,
    private sampleKudoService: SampleKudoService,
    private userService:UserService,
    private kudosService:KudosService,
    private sampleKudosService:SampleKudoService
				) {
    this.kudos= this.sampleKudosService.getKudos();

    /*Author: Mandar. Testing API*/
    userService.getUserByUsername("user1");
    userService.getUserByUsername("user2");
    userService.getUserByUsername("user3");

    userService.addGroupToUser(authService.CurrentUserId(), "room3");
    /*Testing finished here: Mandar*/
  }


  ngOnInit(): void {  }
  
  showKudos(){
    this.clicked=true;
    /*Author: Mandar --- (For testing User service)
    this.userService.getUser(1);
    this.userService.getAllUsers();
    let user : User = {
      username : 'user4',
      password : 'pass4'
    };
    this.userService.addUser(user);
    let userToUpdate : User = {
      id : 6,
      username : 'user4',
      password : 'pass5'
    }
    this.userService.updateUser(userToUpdate);
    this.userService.deleteUser(6);
    */
   this.kudos= this.sampleKudosService.getKudos();
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

      this.sharedService.groupAdded
      .subscribe(newGroup => {
          this.userGroups.push(newGroup);
          console.log(this.userGroups);
      });
      
      console.log(this.userGroups);
    });
  }

  onShare(groupname: String){
    alert("Sharing "+groupname);
  }

}
