import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatTableDataSource, MatSort, MatTable } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Constants } from '../../class/constants';
import { Member } from '../../class/member';
import { AddMemberDialogComponent } from '../add-member-dialog/add-member-dialog.component';
import { Meeting } from '../../class/meeting';
import { User } from '../../class/user';

@Component({
  selector: 'app-add-meeting-dialog',
  templateUrl: './add-meeting-dialog.component.html',
  styleUrls: ['./add-meeting-dialog.component.scss']
})
export class AddMeetingDialogComponent implements OnInit {
  public firstFormGroup: FormGroup;
  public secondFormGroup: FormGroup;
  public memberType: Object;
  public displayedColumns = ['id', 'name', 'type', 'reason', 'edit'];
  public dataSource: MatTableDataSource<Member>;

  public ELEMENT_DATA: Member[] = [];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<any>;

  constructor(
    public dialogRef: MatDialogRef<AddMemberDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _formBuilder: FormBuilder,
    public memberAddDialog: MatDialog) {
    this.memberType = Constants.memberType;
    this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
  }

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      meetingNameCtrl: ['', Validators.required],
      mtgDateCtrl: ['', Validators.required],
      startTimeCtrl: ['', Validators.required],
      endTimeCtrl: ['', Validators.required],
      meetingRoomCtrl: [''],
      meetingDescription: ['', Validators.required],
      meetingGoal: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
    this.firstFormGroup.controls.mtgDateCtrl.disable();
  }

  register(): void {
    const bufTime = new Date(0);
    bufTime.setHours(1, 1);
    const bufDate = new Date(this.firstFormGroup.controls.mtgDateCtrl.value);
    const sendDate = bufDate.getFullYear().toString() + '/' + bufDate.getMonth().toString() + '/' + bufDate.getDate().toString();
    const mainUser = new Member(localStorage.getItem('userId'),
      localStorage.getItem('userName'), localStorage.getItem('userAddress'), true);
    this.ELEMENT_DATA = [];
    this.ELEMENT_DATA.push(mainUser);
    const mtg = new Meeting(0,
      this.firstFormGroup.controls.meetingNameCtrl.value,
      this.firstFormGroup.controls.meetingRoomCtrl.value,
      this.firstFormGroup.controls.meetingDescription.value,
      this.firstFormGroup.controls.meetingGoal.value,
      sendDate,
      this.firstFormGroup.controls.startTimeCtrl.value + ':00',
      this.firstFormGroup.controls.endTimeCtrl.value + ':00',
      this.ELEMENT_DATA,
      [], false);
    this.dialogRef.close(mtg);
  }

  openMemberAddDialog(): void {
    const dialogRef = this.memberAddDialog.open(AddMemberDialogComponent, {
      data: { length: this.ELEMENT_DATA.length, member: null },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.ELEMENT_DATA.push(result);
        this.table.renderRows();
      }
    });
  }

  openMemberEditDialog(idx: number): void {
    const dialogRef = this.memberAddDialog.open(AddMemberDialogComponent, {
      data: { length: this.ELEMENT_DATA.length, member: this.ELEMENT_DATA[idx - 1] },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.ELEMENT_DATA[idx - 1] = result;
        this.table.renderRows();
      }
    });
  }


}
