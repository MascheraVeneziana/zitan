export class Resource {
  private email: string;


  constructor($email: string) {
    this.email = $email;
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

}
