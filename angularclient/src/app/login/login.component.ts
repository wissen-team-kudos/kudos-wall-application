import { Router } from '@angular/router';
import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  invalidLogin: boolean

  constructor(
    private authService:AuthenticationService,
    private router : Router
    ){
  }

  ngOnInit(): void {
  }

  login(username : HTMLInputElement, password: HTMLInputElement) {
    this.authService.login(username.value, password.value)
    .subscribe(result => {
      if(result) {
        console.log("Successful")
        this.invalidLogin = false;
        this.router.navigate(['/'])
        console.log(this.authService.isLoggedIn());
        console.log("Logged in user is : " ,this.authService.CurrentUserId() ,this.authService.CurrentUsername())
      }
      else {
        this.invalidLogin = true;
      }
    })
  }
}
