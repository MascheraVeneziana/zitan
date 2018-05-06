import { Member } from './member';
import { Resource } from './resource';

export class Meeting {
  private id: number;
  private name: string;
  private room: string;
  private description: string;
  private goal: string;
  private date: string;
  private startTime: string;
  private endTime: string;
  private members: Member[];
  private resources: Resource[];
  private notify: boolean;


  constructor($id: number, $name: string, $room: string, $description: string, $goal: string, $date: string, $startTime: string,
    $endTime: string, $members: Member[], $resources: Resource[], $notify: boolean) {
    this.id = $id;
    this.name = $name;
    this.room = $room;
    this.description = $description;
    this.goal = $goal;
    this.date = $date;
    this.startTime = $startTime;
    this.endTime = $endTime;
    this.members = $members;
    this.resources = $resources;
    this.notify = $notify;
  }


  /**
   * Getter $id
   * @return {number}
   */
  public get $id(): number {
    return this.id;
  }

  /**
   * Setter $id
   * @param {number} value
   */
  public set $id(value: number) {
    this.id = value;
  }

  /**
   * Getter $name
   * @return {string}
   */
  public get $name(): string {
    return this.name;
  }

  /**
   * Setter $name
   * @param {string} value
   */
  public set $name(value: string) {
    this.name = value;
  }

  /**
   * Getter $room
   * @return {string}
   */
  public get $room(): string {
    return this.room;
  }

  /**
   * Setter $room
   * @param {string} value
   */
  public set $room(value: string) {
    this.room = value;
  }

  /**
   * Getter $description
   * @return {string}
   */
  public get $description(): string {
    return this.description;
  }

  /**
   * Setter $description
   * @param {string} value
   */
  public set $description(value: string) {
    this.description = value;
  }

  /**
   * Getter $goal
   * @return {string}
   */
  public get $goal(): string {
    return this.goal;
  }

  /**
   * Setter $goal
   * @param {string} value
   */
  public set $goal(value: string) {
    this.goal = value;
  }

  /**
   * Getter $date
   * @return {string}
   */
  public get $date(): string {
    return this.date;
  }

  /**
   * Setter $date
   * @param {string} value
   */
  public set $date(value: string) {
    this.date = value;
  }

  /**
   * Getter $startTime
   * @return {string}
   */
  public get $startTime(): string {
    return this.startTime;
  }

  /**
   * Setter $startTime
   * @param {string} value
   */
  public set $startTime(value: string) {
    this.startTime = value;
  }

  /**
   * Getter $endTime
   * @return {string}
   */
  public get $endTime(): string {
    return this.endTime;
  }

  /**
   * Setter $endTime
   * @param {string} value
   */
  public set $endTime(value: string) {
    this.endTime = value;
  }

  /**
   * Getter $members
   * @return {Member[]}
   */
  public get $members(): Member[] {
    return this.members;
  }

  /**
   * Setter $members
   * @param {Member[]} value
   */
  public set $members(value: Member[]) {
    this.members = value;
  }

  /**
   * Getter $resources
   * @return {Resource[]}
   */
  public get $resources(): Resource[] {
    return this.resources;
  }

  /**
   * Setter $resources
   * @param {Resource[]} value
   */
  public set $resources(value: Resource[]) {
    this.resources = value;
  }

  /**
   * Getter $notify
   * @return {boolean}
   */
  public get $notify(): boolean {
    return this.notify;
  }

  /**
   * Setter $notify
   * @param {boolean} value
   */
  public set $notify(value: boolean) {
    this.notify = value;
  }

}
