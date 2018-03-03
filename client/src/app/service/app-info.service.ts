import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable()
export class AppInfoService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getVersion() {
    return this.http.get(this.apiUrl + '/system');
  }

}
