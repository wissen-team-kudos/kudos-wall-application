import { KudosService } from './services/kudos.service';
import { LoginGuard } from './services/login-guard.service';
import { AuthGuard } from './services/auth-guard.service';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthenticationService } from './services/authentication.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { BsNavbarComponent } from './bs-navbar/bs-navbar.component';
import { NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { RoomModalComponent } from './room-modal/room-modal.component';
import { FormsModule } from "@angular/forms";
import { LoginComponent } from './login/login.component';
import { KudoModalComponent } from './kudo-modal/kudo-modal.component';
import { KudoCardComponent } from './kudo-card/kudo-card.component';
import { RoomService } from './services/room.service';
import { BasicAuthHttpInterceptorService } from './services/basic-auth-http-interceptor.service';
import { UserService } from './services/user.service';
import { SharedService } from './shared/shared.service';
import { RoomCardComponent } from './room-card/room-card.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BsNavbarComponent,
    RoomModalComponent,
    LoginComponent,
    KudoCardComponent,
    KudoModalComponent,
    RoomCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    HttpClient,
    AuthenticationService,
    AuthGuard,
    LoginGuard,
    RoomService,
    UserService,
    KudosService,
    SharedService,
    {provide: HTTP_INTERCEPTORS, useClass: BasicAuthHttpInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
