import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { environment } from '../../environments/environment';

@Injectable()
export class AppInfoService {
  private apiUrl = environment.apiUrl;

  constructor(private http: Http) { }

  getVersion() {
    return this.http.get(this.apiUrl + '/version');
  }

}
