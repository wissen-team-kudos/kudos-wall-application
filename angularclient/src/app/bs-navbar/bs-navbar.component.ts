import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { subscribeOn } from 'rxjs/operators';

@Component({
  selector: 'bs-navbar',
  templateUrl: './bs-navbar.component.html',
  styleUrls: ['./bs-navbar.component.css']
})
export class BsNavbarComponent implements OnInit {

  isUserLoggedIn:boolean;
  constructor(private authService : AuthenticationService) {
  }

  ngOnInit(): void {
    
    this.authService.getLoginStatus()
    .subscribe((value : boolean) => {
      this.isUserLoggedIn = value;
    });
  }

  logout() {
    this.authService.logout();
  }

  getUsername() {
    return this.authService.CurrentUsername();
  }

}
