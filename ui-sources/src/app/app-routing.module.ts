import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TokenInterceptor} from "./TokenInterceptor";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {RegistrationComponent} from "./layout/registration/registration.component";
import {AuthorizationComponent} from "./layout/authorization/authorization.component";
import {ChannelComponent} from "./layout/channel/channel.component";
import {UserComponent} from "./layout/user/user.component";
import {ReportComponent} from "./layout/report/report.component";
import {RegisterChannelComponent} from "./layout/register-channel/register-channel.component";
import {UsersViewComponent} from "./layout/users-view/users-view.component";
import {MainPageComponent} from "./layout/main-page/main-page.component";
import {VideoComponent} from "./layout/video/video.component";

const routes: Routes = [

  {path: 'sign-up', component: RegistrationComponent},
  {path: 'sign-in', component: AuthorizationComponent},
  {path: 'channel', component: ChannelComponent},
  {path: 'user', component: UserComponent},
  {path: 'report', component: ReportComponent},
  {path: 'users', component: UsersViewComponent},
  {path: 'report', component: ReportComponent},
  {path: 'user', component: UserComponent},
  {path: 'register-channel', component: RegisterChannelComponent},
  {path: 'main', component: MainPageComponent},
  {path: 'video/:uuid', component: VideoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
  ]
})
export class AppRoutingModule {
}
