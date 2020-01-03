package dev.thinkverse.troll.utils;

import org.bukkit.Bukkit;

public final class Logger {
  public static void log(LogLevel level, String message) {
    if (message == null) return;

    switch (level) {
      case ERROR:
        Bukkit.getConsoleSender().sendMessage(Util.Chat("&8[&c&lERROR&r&8] &f" + message));
        break;
      case WARNING:
        Bukkit.getConsoleSender().sendMessage(Util.Chat("&8[&6&lWARNING&r&8] &f" + message));
        break;
      case INFO:
        Bukkit.getConsoleSender().sendMessage(Util.Chat("&8[&e&lINFO&r&8] &f" + message));
        break;
      case SUCCESS:
        Bukkit.getConsoleSender().sendMessage(Util.Chat("&8[&a&lSUCCESS&r&8] &f" + message));
        break;
      case OUTLINE:
        Bukkit.getConsoleSender().sendMessage(Util.Chat("&7" + message));
        break;
    }
  }

  public enum LogLevel { ERROR, WARNING, INFO, SUCCESS, OUTLINE }
}
