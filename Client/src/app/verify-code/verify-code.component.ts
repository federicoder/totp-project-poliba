import { Component, OnInit, OnChanges, SimpleChanges, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RegistrationService } from '../services/registration.service';
import { UtilService } from '../util.service';

@Component({
  selector: 'app-verify-code',
  templateUrl: './verify-code.component.html',
  styleUrls: ['./verify-code.component.css'],
})
export class VerifyCodeComponent implements OnInit, OnChanges {
  constructor(private service: RegistrationService, private router: Router,private actRouter: ActivatedRoute, private utilService: UtilService) {}
  @Input() src: string = '';
  code: string = '';
  username: string = '';
  @Input() isParametrized = true;
  parametrizationComplete = false;

  data: { digits: string; algorithm: string; period: string } = {
    digits: '',
    algorithm: '',
    period: '',
  };

  ngOnInit() {
    let parametrize: any = this.actRouter.snapshot.queryParamMap.get('parametrize');

    if(parametrize === "true"){
      parametrize = true;
    }
    else{
      parametrize = false;
    }
    this.isParametrized = parametrize;
      // Recupera il parametro booleano dalla query

  // Converte il valore in booleano (se necessario)
    if (this.isParametrized) {
    } else {
      this.parametrizationComplete = true;
       this.service.getQRCode().subscribe(res =>  this.src  = res.toString());
    }
    // this.utilService.getParametrizationObs().subscribe(async (res: boolean) => {
    //   this.isParametrized = res;

    //   if (this.isParametrized) {
    //   } else {
    //     this.parametrizationComplete = true;
    //      this.service.getQRCode().subscribe(res =>  this.src  = res.toString());
    //   }
    // });
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes) {
      if (changes.isParametrized) {
        if (changes.isParametrized.currentValue) {
          this.isParametrized = changes.isParametrized.currentValue;
        }
      }
      if (changes.src) {
        if (changes.src.currentValue) {
          this.src = changes.src.currentValue;
        }
      }
    }
  }
  async setParameters($event: any) {
    this.data = {
      digits: $event.digits,
      algorithm: $event.algorithm,
      period: $event.period,
    };
    this.service.getQRCode(this.data).subscribe(res => this.src = res.toString())
    this.parametrizationComplete = true;

  }
  async onverify() {
    const res = await this.service.verifyQRCode(this.code, this.username);
    if (res == 'true') {
      this.router.navigate(['/home']);
    } else {
      this.router.navigate(['/login']);
    }
  }

}
