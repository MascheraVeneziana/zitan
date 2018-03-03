import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { User } from '../class/user';

@Injectable()
export class UserService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(this.apiUrl + '/users');
  }

  getByUserName(userName: string) {
    return this.http.get(this.apiUrl + '/users/' + userName);
  }

  getByMe() {
    return this.http.get(this.apiUrl + '/users/me');
  }

  create(user: User) {
    return this.http.post(this.apiUrl + '/users', user);
  }

  update(user: User) {
    return this.http.put(this.apiUrl + '/users/' + user.id, user);
  }

  delete(user: User) {
    return this.http.delete(this.apiUrl + '/users/' + user.id);
  }

}
