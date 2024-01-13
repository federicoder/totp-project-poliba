import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/User';
import { RegistrationService } from '../services/registration.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  user = new User();
  msg = '';
  userID: number = 0;

  constructor(private service: RegistrationService, private router: Router) { }

  ngOnInit(): void {
    console.log("hello");
  }

  async loginUser() {
    console.log('i am here login');
    console.log(this.user);
    const data = await this.service.loginUserFromRemote(this.user);
    if (data) this.router.navigate(['/2fa']);

  }

}
