import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TokenInterceptor} from "./TokenInterceptor";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {RegistrationComponent} from "./layout/registration/registration.component";
import {AuthorizationComponent} from "./layout/authorization/authorization.component";

const routes: Routes = [

  {path: 'sign-up', component: RegistrationComponent},
  {path: 'sign-in', component: AuthorizationComponent}
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
