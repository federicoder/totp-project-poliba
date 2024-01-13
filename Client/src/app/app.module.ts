import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { SignUpComponent } from './sign-up/sign-up.component';
import { HttpRequestInterceptor } from './http-request-interceptor';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { VerifyCodeComponent } from './verify-code/verify-code.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    SignUpComponent,
    LandingPageComponent,
    VerifyCodeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    HttpClientModule,
    MatButtonModule,
    FormsModule
  ],
  providers: [
    [
      {
        provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor
        , multi: true
      }
    ]
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
