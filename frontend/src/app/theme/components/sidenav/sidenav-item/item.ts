export class Item {
  private _text: String;
  private _icon: String;
  private _isVisible: boolean;

  constructor(text: String, icon: String, isVisible: boolean) {
    this._text = text;
    this._icon = icon;
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

  get isVisible(): boolean {
    return this._isVisible;
  }

  set isVisible(value: boolean) {
    this._isVisible = value;
  }
}
