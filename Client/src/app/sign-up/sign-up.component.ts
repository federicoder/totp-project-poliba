import { Router } from '@angular/router';
import { RegistrationService } from './../services/registration.service';
import { User } from './../models/User';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  user: User = new User();
  constructor(private service: RegistrationService, private router: Router) { }

  ngOnInit(): void {
  }

  async signUp() {
    const data = await this.service.signUp(this.user);
    if (data == "OK") this.router.navigate(['login']);
    else console.log("some error");

  }

}
