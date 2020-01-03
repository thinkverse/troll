package dev.thinkverse.troll.utils;

import org.bukkit.ChatColor;

public final class Util {
  public static String Chat(String message) {
    return ChatColor.translateAlternateColorCodes((char) '&', message);
  }
}
