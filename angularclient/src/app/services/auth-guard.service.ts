import { Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate{

  constructor(
    private authService: AuthenticationService,
    private router: Router
    ) { }

  canActivate() {
    if(this.authService.isLoggedIn())
      return true;
    this.router.navigate(['/login']);
    return false;
  }
}
