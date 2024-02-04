import { Component, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { User } from '../models/User';
import { RegistrationService } from '../services/registration.service';
import { UtilService } from '../util.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent implements OnInit {
  user = new User();
  msg = '';
  userID: number = 0;
  parametrize = false;
  constructor(private service: RegistrationService, private router: Router, private utilService: UtilService) {}

  ngOnInit(): void {
    
  }
  setParameterOn() {
    this.parametrize = !this.parametrize;
  }

  async loginUser() {
    const data = await this.service.loginUserFromRemote(this.user);
    if (data) {
      if (this.parametrize) {
        this.utilService.getParametrizationSub.next(true);
      } else {
        this.utilService.getParametrizationSub.next(false);
      }

      localStorage.setItem("mail", this.user.mail);
      this.router.navigate(['/2fa'], { queryParams: { parametrize: this.parametrize } });
    }
  }
}
