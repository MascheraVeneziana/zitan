import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Constants } from '../../class/constants';
import { Member } from '../../class/member';

@Component({
  selector: 'app-add-member-dialog',
  templateUrl: './add-member-dialog.component.html',
  styleUrls: ['./add-member-dialog.component.scss']
})
export class AddMemberDialogComponent implements OnInit {

  public memberAddGroup: FormGroup;
  public memberType: Object;
  private editFlag: boolean;
  private editId: number;

  constructor(
    public dialogRef: MatDialogRef<AddMemberDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _formBuilder: FormBuilder) {
    this.memberType = Constants.memberType;
  }

  ngOnInit() {
    this.memberAddGroup = this._formBuilder.group({
      memberName: ['', Validators.required],
      memberType: ['', Validators.required],
      memberReason: ['', Validators.required]
    });
    if (this.data.member != null) {
      const member: Member = this.data.member;
      this.memberAddGroup.controls.memberName.setValue(member.name);
      this.memberAddGroup.controls.memberType.setValue(member.type);
      this.memberAddGroup.controls.memberReason.setValue(member.reason);
      this.editId = member.id;
      this.editFlag = true;
    } else {
      this.editFlag = false;
    }
  }

  closeDialog(): void {
    if (this.memberAddGroup.valid) {
      if (this.editFlag) {
        const member: Member =
          new Member(this.editId, this.memberAddGroup.value.memberName,
            this.memberAddGroup.value.memberType, this.memberAddGroup.value.memberReason);
        this.dialogRef.close(member);
      } else {
        const member: Member =
          new Member(this.data.length + 1, this.memberAddGroup.value.memberName,
            this.memberAddGroup.value.memberType, this.memberAddGroup.value.memberReason);
        this.dialogRef.close(member);
      }
    }
  }

}
