import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public isFirstTime(optionalData?: {email:string}): Observable<boolean> {
    return this.http.get<boolean>(`http://localhost:9000/firstime`, { params: optionalData});
  }

}
