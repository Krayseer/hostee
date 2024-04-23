import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './layout/registration/registration.component';
import {ReactiveFormsModule} from "@angular/forms";
import {UserService} from "./service/UserService";
import {HttpClientModule} from "@angular/common/http";
import { AuthorizationComponent } from './layout/authorization/authorization.component';
import { ChannelComponent } from './layout/channel/channel.component';
import { UserComponent } from './layout/user/user.component';
import {ChannelService} from "./service/ChannelService";

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    AuthorizationComponent,
    ChannelComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [UserService, ChannelService],
  bootstrap: [AppComponent]
})
export class AppModule { }
