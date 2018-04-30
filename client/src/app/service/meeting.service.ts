import { Injectable } from '@angular/core';
import { Headers, RequestOptions, Response } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Meeting } from '../class/meeting';

@Injectable()
export class MeetingService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(this.apiUrl + '/meeting');
  }

  getByMeetingName(meetingName: string) {
    return this.http.get(this.apiUrl + '/meeting/' + meetingName);
  }

  create(meeting: Meeting) {
    return this.http.post(this.apiUrl + '/meeting/add', meeting);
  }

  update(meeting: Meeting) {
    return this.http.put(this.apiUrl + '/meeting/' + meeting.id, meeting);
  }

  delete(meeting: Meeting) {
    return this.http.delete(this.apiUrl + '/meeting/' + meeting.id);
  }

}
