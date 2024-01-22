import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-otpconfig',
  templateUrl: './otpconfig.component.html',
  styleUrls: ['./otpconfig.component.css'],
})
export class OtpconfigComponent implements OnInit {
  algorithm = '';
  digits = '';
  period = '';
  @Output() dataToSendEmitter = new EventEmitter<{digits: string,algorithm:string, period:string }>();
  constructor() {}

  ngOnInit(): void {}
  setValueOfAlgorithm($event: any) {
    this.algorithm = $event.value;
  }
  setValueOfPeriod($event: any) {
    this.period =this.period.concat($event.data) ;
  }
  setValueOfDigits($event: any) {
    this.digits = $event.data;
  }
  setValues() {
    this.dataToSendEmitter.emit({digits: this.digits, algorithm: this.algorithm, period: this.period});
  }
}
