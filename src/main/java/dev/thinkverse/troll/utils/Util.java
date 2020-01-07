package dev.thinkverse.troll.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public final class Util {
  public static void message(CommandSender sender, String... message) {
    Arrays.stream(message).map(Util::translate).forEach(sender::sendMessage);
  }

  public static void broadcast(String... message) {
    Arrays.stream(message).map(Util::translate).forEach(Bukkit::broadcastMessage);
  }

  public static String translate(String message) {
    return ChatColor.translateAlternateColorCodes((char) '&', message);
  }
}
