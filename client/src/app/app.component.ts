import { Component, OnInit } from '@angular/core';
import { AppInfoService } from './service/app-info.service';
import { Title } from '@angular/platform-browser';
import { MatDialog } from '@angular/material';
import { AddMeetingDialogComponent } from './component/add-meeting-dialog/add-meeting-dialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  animal: string;
  name: string;

  constructor(private appInfoService: AppInfoService, private titleService: Title
  , public dialog: MatDialog) { }

  ngOnInit() {
    // this.appInfoService.getVersion().subscribe(appName => {
    //   localStorage.setItem('appName', appName.text());
    //   this.titleService.setTitle(appName.text());
    // },
    //   error => {
    //     console.log(error);
    //   });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddMeetingDialogComponent, {
      data: { name: this.name, animal: this.animal },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.animal = result;
    });
  }

}
