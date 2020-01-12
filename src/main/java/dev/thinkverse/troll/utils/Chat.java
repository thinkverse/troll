package dev.thinkverse.troll.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class Chat {
  public static void message(@NotNull CommandSender sender, @NotNull String... message) {
    Arrays.stream(message).map(Chat::translate).forEach(sender::sendMessage);
  }

  public static void broadcast(@NotNull String... message) {
    Arrays.stream(message).map(Chat::translate).forEach(Bukkit::broadcastMessage);
  }

  public static void notify(@NotNull CommandSender sender, @NotNull String permission, @NotNull String... message) {
    Bukkit.getOnlinePlayers().forEach(player -> {
      if (player.hasPermission(permission) && !(player.equals(sender))) Chat.message(player, message);
    });
  }

  public static void notify(@NotNull CommandSender sender, @NotNull String[] permissions, @NotNull String... message) {
    Bukkit.getOnlinePlayers().stream().filter(player -> {
      for (String node : permissions) {
        if (player.hasPermission(node) && !(player.equals(sender))) return true;
      }
      return false;
    }).forEach(player -> Chat.message(player, message));
  }

  @NotNull
  public static String translate(String message) { return ChatColor.translateAlternateColorCodes((char) '&', message); }
}