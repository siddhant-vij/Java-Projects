package p9Reflection.IDE_Info_Plugin;

/**
 * Represents a Type when the user hovers the mouse over
 */

public class PopupTypeInfo {
  private boolean isPrimitive;
  private boolean isInterface;
  private boolean isEnum;
  private String name;
  private boolean isJdk;

  public boolean isPrimitive() {
    return isPrimitive;
  }

  public PopupTypeInfo setPrimitive(boolean isPrimitive) {
    this.isPrimitive = isPrimitive;
    return this;
  }

  public boolean isInterface() {
    return isInterface;
  }

  public PopupTypeInfo setInterface(boolean isInterface) {
    this.isInterface = isInterface;
    return this;
  }

  public boolean isEnum() {
    return isEnum;
  }

  public PopupTypeInfo setEnum(boolean isEnum) {
    this.isEnum = isEnum;
    return this;
  }

  public String getName() {
    return name;
  }

  public PopupTypeInfo setName(String name) {
    this.name = name;
    return this;
  }

  public boolean isJdk() {
    return isJdk;
  }

  public PopupTypeInfo setJdk(boolean isJdk) {
    this.isJdk = isJdk;
    return this;
  }

  @Override
  public String toString() {
    return "PopupTypeInfo{" +
        "isPrimitive=" + isPrimitive +
        ", isInterface=" + isInterface +
        ", isEnum=" + isEnum +
        ", name='" + name + '\'' +
        ", isJdk=" + isJdk +
        '}';
  }
}
