import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './layout/registration/registration.component';
import {ReactiveFormsModule} from "@angular/forms";
import {UserService} from "./service/UserService";
import {HttpClientModule} from "@angular/common/http";
import { AuthorizationComponent } from './layout/authorization/authorization.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    AuthorizationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
