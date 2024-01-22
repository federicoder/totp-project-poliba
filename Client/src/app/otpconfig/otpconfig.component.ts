import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-otpconfig',
  templateUrl: './otpconfig.component.html',
  styleUrls: ['./otpconfig.component.css'],
})
export class OtpconfigComponent implements OnInit {
  algorithm = '';
  digits = '';
  period = '';
  constructor() {}

  ngOnInit(): void {}
  getValueOfAlgorithm($event: any) {
    this.algorithm = $event;
  }
  setValues() {}
}
