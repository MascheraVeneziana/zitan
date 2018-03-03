import { Member } from './member';

export class Meeting {
  private _id: number;
  private _name: string;
  private _place: string;
  private _date: string;
  private _startTime: string;
  private _endTime: string;
  private _memberList: Member[];


  constructor(id: number, name: string, place: string, date: string, startTime: string, endTime: string, memberList: Member[]) {
    this._id = id;
    this._name = name;
    this._place = place;
    this._date = date;
    this._startTime = startTime;
    this._endTime = endTime;
    this._memberList = memberList;
  }


  public get id(): number {
    return this._id;
  }

  public set id(value: number) {
    this._id = value;
  }

  public get name(): string {
    return this._name;
  }

  public set name(value: string) {
    this._name = value;
  }

  public get place(): string {
    return this._place;
  }

  public set place(value: string) {
    this._place = value;
  }

  public get date(): string {
    return this._date;
  }

  public set date(value: string) {
    this._date = value;
  }

  public get startTime(): string {
    return this._startTime;
  }

  public set startTime(value: string) {
    this._startTime = value;
  }

  public get endTime(): string {
    return this._endTime;
  }

  public set endTime(value: string) {
    this._endTime = value;
  }

  public get memberList(): Member[] {
    return this._memberList;
  }

  public set memberList(value: Member[]) {
    this._memberList = value;
  }

}
