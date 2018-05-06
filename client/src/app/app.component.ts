import { UserService } from './service/user.service';
import { Component, OnInit } from '@angular/core';
import { AppInfoService } from './service/app-info.service';
import { Title } from '@angular/platform-browser';
import { MatDialog } from '@angular/material';
import { AddMeetingDialogComponent } from './component/add-meeting-dialog/add-meeting-dialog.component';
import { MeetingService } from './service/meeting.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  public userName: string;

  constructor(
    private appInfoService: AppInfoService,
    private userService: UserService,
    private titleService: Title,
    private meetingService: MeetingService,
    public dialog: MatDialog) { }

  public ngOnInit() {
    this.appInfoService.getVersion().subscribe(appName => {
      localStorage.setItem('appName', appName['applicationName'] + ' ' + appName['version']);
      console.log(appName);

      this.titleService.setTitle(appName['applicationName'] + ' ' + appName['version']);
    },
      error => {
        console.log(error);
      });
    this.userService.getByMe().subscribe(user => {
      localStorage.setItem('userId', user['id']);
      localStorage.setItem('userName', user['name']);
      localStorage.setItem('userAddress', user['email']);
      console.log(user);
      this.userName = user['name'];
    },
      error => {
        console.log(error);
      });
  }

  public openDialog(): void {
    this.userService.getByMe().subscribe(user => {
      localStorage.setItem('userId', user['id']);
      localStorage.setItem('userName', user['name']);
      localStorage.setItem('userAddress', user['email']);
      console.log(user);
      this.userName = user['name'];
      const dialogRef = this.dialog.open(AddMeetingDialogComponent, {
        data: {},
        disableClose: true
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log(result);
        this.meetingService.create(result).subscribe(res => {
          console.log(res);
          location.reload();
        }, error => {
          console.log(error);
        });
      });
    },
      error => {
        console.log(error);
        this.login();
      });
  }

  public login(): void {
    location.href = './oauth2/authorization/google';
  }

  public logout(): void {
    this.appInfoService.sendLogout().subscribe(res => {
      location.reload();
    }, error => {
      location.reload();
      console.log(error);
    });
  }

}
