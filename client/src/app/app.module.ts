import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpModule } from '@angular/http';
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
} from '@angular/material';

import { AppComponent } from './app.component';

import { AppInfoService } from './service/app-info.service';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { AddMeetingDialogComponent } from './component/add-meeting-dialog/add-meeting-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    AddMeetingDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
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
  ],
  schemas: [NO_ERRORS_SCHEMA],
  providers: [
    AppInfoService
  ],
  entryComponents: [
    AddMeetingDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
