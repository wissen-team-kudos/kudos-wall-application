import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {map} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt'
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private authenticationUrl = 'http://localhost:8080/authenticate';
  
  constructor( 
    private http : HttpClient
    ) {
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
        return true;
      }
      return false;
    }))
  }

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn() {
    let jwtHelper = new JwtHelperService();
    let token = localStorage.getItem('token');
    if(!token)
      return false;
    let expirationDate = jwtHelper.getTokenExpirationDate(token);
    let isExpired = jwtHelper.isTokenExpired(token);
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
}
