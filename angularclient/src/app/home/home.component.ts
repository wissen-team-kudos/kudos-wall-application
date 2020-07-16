import { UserService, User } from './../services/user.service';
import { AuthenticationService } from './../services/authentication.service';
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

  constructor(private sampleGroupService: SampleGroupService,private sampleKudoService: SampleKudoService,
    private userService:UserService) {

    this.kudos= this.sampleKudoService.getKudos();
   }

  ngOnInit(): void {
  }
  
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
