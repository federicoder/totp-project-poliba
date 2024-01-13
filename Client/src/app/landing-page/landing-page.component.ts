import { Router } from '@angular/router';
import { RegistrationService } from './../services/registration.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  constructor(private service: RegistrationService, private router: Router) { }
  userName: string = '';

  async ngOnInit() {
    try {
      this.userName = await this.service.getUser();
    }

    catch (err) {
      this.router.navigate(['/login']);
    }

    console.log(this.userName)
  }

  async logout() {
    const data = await this.service.logOut();
    if (data) window.location.reload();
  }
}
