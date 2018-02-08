import { Component, OnInit } from '@angular/core';
import { AppInfoService } from './service/app-info.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(private appInfoService: AppInfoService, private titleService: Title) { }

  ngOnInit() {
    // this.appInfoService.getVersion().subscribe(appName => {
    //   localStorage.setItem('appName', appName.text());
    //   this.titleService.setTitle(appName.text());
    // },
    //   error => {
    //     console.log(error);
    //   });
  }

}
