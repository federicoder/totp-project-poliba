import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UtilService {
  getParametrizationSub = new Subject<boolean>();
  parametrizationObs = this.getParametrizationSub.asObservable();
  constructor() {}

  getParametrizationObs() {
    return this.parametrizationObs;
  }
}
