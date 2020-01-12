package dev.thinkverse.troll.utils.config;

import java.util.Objects;

public final class ConfigObj {

  public static String ifNull(Object obj, String value) {
    return Objects.isNull(obj) ? value : (String) obj;
  }

  public static double ifNull(Object obj, double value) { return Objects.isNull(obj) ? value : (double) obj; }

  public static boolean ifNull(Object obj, boolean value) {
    return Objects.isNull(obj) ? value : (boolean) obj;
  }

  public static int ifNull(Object obj, int value) {
    return Objects.isNull(obj) ? value : (int) obj;
  }
}
