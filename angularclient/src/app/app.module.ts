import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { BsNavbarComponent } from './bs-navbar/bs-navbar.component';
import { NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { GroupModalComponent } from './group-modal/group-modal.component';
import { FormsModule } from "@angular/forms";
import { KudoModalComponent } from './kudo-modal/kudo-modal.component';
import { SampleGroupService } from './sample-group.service';
import { SampleKudoService } from './sample-kudo.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BsNavbarComponent,
    GroupModalComponent,
    KudoModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule
  ],
  providers: [
    SampleGroupService, 
    SampleKudoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
