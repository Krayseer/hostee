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
import { ReportComponent } from './layout/report/report.component';
import {ReportService} from "./service/ReportService";
import { HeaderComponent } from './components/header/header.component';
import { UsersViewComponent } from './layout/users-view/users-view.component';
import { RegisterChannelComponent } from './layout/register-channel/register-channel.component';
import { VideoComponent } from './layout/video/video.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    AuthorizationComponent,
    ChannelComponent,
    UserComponent,
    ReportComponent,
    HeaderComponent,
    UserComponent,
    RegisterChannelComponent,
    UserComponent,
    VideoComponent,
    HeaderComponent,
    UsersViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [UserService, ChannelService, ReportService, provideAnimationsAsync()],
  bootstrap: [AppComponent]
})
export class AppModule { }
