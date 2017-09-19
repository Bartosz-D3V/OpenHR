export class SidenavItem {
  private _text: String;
  private _icon: String;
  private _link: String;
  private _isVisible: boolean;

  constructor(text: String, icon: String, link: String, isVisible: boolean) {
    this._text = text;
    this._icon = icon;
    this._link = link;
    this._isVisible = isVisible;
  }

  get text(): String {
    return this._text;
  }

  set text(value: String) {
    this._text = value;
  }

  get icon(): String {
    return this._icon;
  }

  set icon(value: String) {
    this._icon = value;
  }

  get link(): String {
    return this._link;
  }

  set link(value: String) {
    this._link = value;
  }

  get isVisible(): boolean {
    return this._isVisible;
  }

  set isVisible(value: boolean) {
    this._isVisible = value;
  }
}
