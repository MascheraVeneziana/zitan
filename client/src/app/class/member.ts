export class Member {
  private id: string;
  private name: string;
  private email: string;
  private required: boolean;


  constructor($id: string, $name: string, $email: string, $required: boolean) {
    this.id = $id;
    this.name = $name;
    this.email = $email;
    this.required = $required;
  }

  /**
   * Getter $id
   * @return {string}
   */
  public get $id(): string {
    return this.id;
  }

  /**
   * Setter $id
   * @param {string} value
   */
  public set $id(value: string) {
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
   * Getter $email
   * @return {string}
   */
  public get $email(): string {
    return this.email;
  }

  /**
   * Setter $email
   * @param {string} value
   */
  public set $email(value: string) {
    this.email = value;
  }

  /**
   * Getter $required
   * @return {boolean}
   */
  public get $required(): boolean {
    return this.required;
  }

  /**
   * Setter $required
   * @param {boolean} value
   */
  public set $required(value: boolean) {
    this.required = value;
  }



}
