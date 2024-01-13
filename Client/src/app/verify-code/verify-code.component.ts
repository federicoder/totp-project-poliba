import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationService } from '../services/registration.service';

@Component({
  selector: 'app-verify-code',
  templateUrl: './verify-code.component.html',
  styleUrls: ['./verify-code.component.css']
})
export class VerifyCodeComponent implements OnInit {

  constructor(private service: RegistrationService, private router: Router) { }
  src: string = '';
  code: string = '';
  username: string = '';
  async ngOnInit() {
    this.src = await this.service.getQRCode();
  }

  async onverify() {
    const res = await this.service.verifyQRCode(this.code, this.username);
    if (res == 'true') {
      this.router.navigate(['/home']);
    }
    else {
      this.router.navigate(['/login']);
    }
  }
}
