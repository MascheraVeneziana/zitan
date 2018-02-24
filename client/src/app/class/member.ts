export class Member {
  private _id: number;
  private _name: string;
  private _type: number;
  private _reason: string;


  constructor(id: number, name: string, type: number, reason: string) {
    this._id = id;
    this._name = name;
    this._type = type;
    this._reason = reason;
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

  public get type(): number {
    return this._type;
  }

  public set type(value: number) {
    this._type = value;
  }

  public get reason(): string {
    return this._reason;
  }

  public set reason(value: string) {
    this._reason = value;
  }

}
