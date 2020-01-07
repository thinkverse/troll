package dev.thinkverse.troll.utils;

import dev.thinkverse.troll.utils.enums.LogLevel;
import org.bukkit.Bukkit;

public final class Logger extends java.util.logging.Logger {

  public Logger() {
    super("TrollPlugin", null);
  }

  public void log(LogLevel level, String message) {
    if (message == null) return;

    switch (level) {
      case ERROR:
        Util.message(Bukkit.getConsoleSender(),"&8[&c&lERROR&r&8] &f" + message);
        break;
      case WARNING:
        Util.message(Bukkit.getConsoleSender(),"&8[&6&lWARNING&r&8] &f" + message);
        break;
      case INFO:
        Util.message(Bukkit.getConsoleSender(),"&8[&e&lINFO&r&8] &f" + message);
        break;
      case SUCCESS:
        Util.message(Bukkit.getConsoleSender(),"&8[&a&lSUCCESS&r&8] &f" + message);
        break;
      case OUTLINE:
        Util.message(Bukkit.getConsoleSender(),"&7" + message);
        break;
    }
  }
}
