import { Router } from '@angular/router';
import { AuthenticationService } from './../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

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

  login(f) {
    this.authService.login(f.value.username, f.value.password)
    .subscribe(result => {
      if(result) {
        console.log("Successful");
        this.invalidLogin = false;
        this.router.navigate(['/']);
        console.log(this.authService.isLoggedIn());
        console.log("Logged in user is : " ,this.authService.CurrentUserId() ,this.authService.CurrentUsername());
      }
      else {
        this.invalidLogin = true;
      }
    },(error: HttpErrorResponse) =>{
      console.log(error)
      if(error.status == 401)
        this.invalidLogin=true;
    });
  }
}
