import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/User';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) { }

  public loginUserFromRemote(user: User): Promise<any> {
    return new Promise((res, rej) => {
      this.http.post(`http://localhost:9000/login`, user, { responseType: 'text' })
        .subscribe(data => {
          console.log(data);
          res(data)
        }),
        (err: any) => rej(err)
    });
  }

  public signUp(user: User): Promise<any> {
    return new Promise((res, rej) => {
      this.http.post('http://localhost:9000/new', user, { responseType: 'text' })
        .subscribe(data => {
          console.log(data);
          res(data);
        },
          data => rej(data.ok))

    });
  }

  public getUser(): Promise<any> {
    return new Promise((res, rej) => {
      this.http.get('http://localhost:9000/name', { responseType: 'text' })
        .subscribe(data => {
          console.log(data);
          res(data);
        },
          data => rej(data.ok))

    });
  }

  public logOut(): Promise<any> {
    return new Promise((res, rej) => {
      this.http.post(`http://localhost:9000/logout`, { responseType: 'text' })
        .subscribe(data => res(data)),
        (err: any) => rej("error")
    });
  }

  public getQRCode(): Promise<any> {
    return new Promise((res, rej) => {
      this.http.get('http://localhost:9000/2fa', { responseType: 'text' })
        .subscribe(data => {
          console.log("qr code" + data);
          res(data);
        },
          data => rej(data.ok))

    });
  }

  public verifyQRCode(code: String, username: string): Promise<any> {
    return new Promise((res, rej) => {
      this.http.post('http://localhost:9000/verify', { code: code, username: username }, { responseType: 'text' })
        .subscribe(data => {
          console.log(data);
          res(data);
        },
          data => {
            console.log(data);
            rej(data.ok)
          })

    });
  }



}
