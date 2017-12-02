export class SidenavItem {
  public text: string;
  public icon: string;
  public link: string;
  public isVisible: boolean;

  constructor(text: string, icon: string, link: string, isVisible: boolean) {
    this.text = text;
    this.icon = icon;
    this.link = link;
    this.isVisible = isVisible;
  }
}
