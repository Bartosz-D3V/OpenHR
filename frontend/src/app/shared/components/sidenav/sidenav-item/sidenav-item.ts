export class SidenavItem {
  public text: String;
  public icon: String;
  public link: String;
  public isVisible: boolean;

  constructor(text: String, icon: String, link: String, isVisible: boolean) {
    this.text = text;
    this.icon = icon;
    this.link = link;
    this.isVisible = isVisible;
  }
}
