import { LoginGuard } from './services/login-guard.service';
import { AuthGuard } from './services/auth-guard.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {path:'', component:HomeComponent, canActivate: [AuthGuard]},
  {path:'login', component:LoginComponent, canActivate: [LoginGuard]},
  {path:'**', redirectTo:''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
