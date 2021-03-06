import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {
  MatToolbarModule,
  MatMenuModule,
  MatButtonModule,
  MatDialogModule,
  MatInputModule,
  MatStepperModule,
  MatIconModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSelectModule,
  MatTableModule,
  MatSortModule,
} from '@angular/material';

import { AppComponent } from './app.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { AddMeetingDialogComponent } from './component/add-meeting-dialog/add-meeting-dialog.component';
import { AddMemberDialogComponent } from './component/add-member-dialog/add-member-dialog.component';

import { AppInfoService } from './service/app-info.service';
import { UserService } from './service/user.service';
import { MeetingService } from './service/meeting.service';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    AddMeetingDialogComponent,
    AddMemberDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatMenuModule,
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    MatStepperModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatTableModule,
    MatSortModule,
  ],
  schemas: [NO_ERRORS_SCHEMA],
  providers: [
    AppInfoService,
    UserService,
    MeetingService
  ],
  entryComponents: [
    AddMeetingDialogComponent,
    AddMemberDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
