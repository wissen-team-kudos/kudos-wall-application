import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(
    private authService: AuthenticationService,
    private router: Router
    ) { }

  canActivate() {
    if(this.authService.isLoggedIn()){
      this.router.navigate(['/']);
      return false; 
    }
    console.log(localStorage.getItem('token'));
    localStorage.clear();
    return true;
    
  }


}
