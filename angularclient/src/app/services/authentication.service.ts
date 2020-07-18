import { Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {map} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt'
import { BehaviorSubject, Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private authenticationUrl = 'http://localhost:8080/authenticate';
  
  private isUserLoggedIn:BehaviorSubject<boolean>;

  constructor( 
    private http : HttpClient,
    private router : Router
    ) {
    this.isUserLoggedIn = new BehaviorSubject<boolean>(false);
  }

  login(requestUsername:string, requestPassword:string) {
    let authRequestBody = {
      username :  requestUsername,
      password : requestPassword
    }
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    return this.http.post(
      this.authenticationUrl,
      JSON.stringify(authRequestBody),
      {
        headers : headers,
        responseType: 'text'
      }
    ).pipe(map(response => {
      let result = JSON.parse(response);
      if(result && result.jwt) {
        localStorage.setItem('token', result.jwt);
        this.isUserLoggedIn.next(true);
        return true;
      }
      this.isUserLoggedIn.next(false);
      return false;
    }));
  }

  logout() {
    this.isUserLoggedIn.next(false);
    if(this.isLoggedIn()){
      localStorage.removeItem('token');
    }
    this.router.navigate(['/login']);
  }

  isLoggedIn() {
    let jwtHelper = new JwtHelperService();
    let token = localStorage.getItem('token');
    if(!token) {
      this.isUserLoggedIn.next(false);
      return false;
    }
    let expirationDate = jwtHelper.getTokenExpirationDate(token);
    let isExpired = jwtHelper.isTokenExpired(token);
    this.isUserLoggedIn.next(!isExpired);
    return !isExpired;
  }

  CurrentUserId() {
    let token = localStorage.getItem('token');
    if(!token)
      return null;
    let jwtHelper = new JwtHelperService();
    return jwtHelper.decodeToken(token).userid;
  }

  CurrentUsername() {
    let token = localStorage.getItem('token');
    if(!token)
      return null;
    let jwtHelper = new JwtHelperService();
    return jwtHelper.decodeToken(token).sub;
  }

  getLoginStatus() : Observable<boolean> {
    return this.isUserLoggedIn.asObservable();
  }

}
