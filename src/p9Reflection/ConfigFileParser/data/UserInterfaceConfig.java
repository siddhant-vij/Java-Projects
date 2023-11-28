package p9Reflection.ConfigFileParser.data;

import java.util.Arrays;

public class UserInterfaceConfig {
  private String titleText;
  private String[] titleFonts;
  private String[] titleTextSizes;

  public String getTitleText() {
    return titleText;
  }

  public String[] getTitleFonts() {
    return titleFonts;
  }

  public String[] getTitleTextSizes() {
    return titleTextSizes;
  }

  @Override
  public String toString() {
    return "UserInterfaceConfig [titleText=" + titleText + ", titleFonts=" + Arrays.toString(titleFonts)
        + ", titleTextSizes=" + Arrays.toString(titleTextSizes) + "]";
  }
}
