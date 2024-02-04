import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-otpconfig',
  templateUrl: './otpconfig.component.html',
  styleUrls: ['./otpconfig.component.css'],
})
export class OtpconfigComponent implements OnInit {
  algorithm:string = '';
  digits: string = '';
  @Output() dataToSendEmitter = new EventEmitter<{digits: string,algorithm:string }>();
  constructor() {}

  ngOnInit(): void {}

  setValues() {
    this.dataToSendEmitter.emit({digits: this.digits, algorithm: this.algorithm});
  }
}
