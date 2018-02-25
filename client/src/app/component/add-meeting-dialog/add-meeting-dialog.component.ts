import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatTableDataSource, MatSort, MatTable } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Constants } from '../../class/constants';
import { Member } from '../../class/member';
import { AddMemberDialogComponent } from '../add-member-dialog/add-member-dialog.component';

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
      meetingRoomCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  openMemberAddDialog(): void {
    const dialogRef = this.memberAddDialog.open(AddMemberDialogComponent, {
      data: { length: this.ELEMENT_DATA.length, member: null },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ELEMENT_DATA.push(result);
      this.table.renderRows();
    });
  }

  openMemberEditDialog(idx: number): void {
    const dialogRef = this.memberAddDialog.open(AddMemberDialogComponent, {
      data: { length: this.ELEMENT_DATA.length, member: this.ELEMENT_DATA[idx - 1] },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ELEMENT_DATA[idx - 1] = result;
      this.table.renderRows();
    });
  }


}
