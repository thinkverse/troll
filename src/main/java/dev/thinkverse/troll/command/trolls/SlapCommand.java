package dev.thinkverse.troll.command.trolls;

import dev.thinkverse.troll.Troll;
import dev.thinkverse.troll.utils.Logger;
import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.utils.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SlapCommand extends SubCommand {
  private Plugin plugin = Troll.getPlugin(Troll.class);

  @Override
  public String getName() {
    return "slap";
  }

  @Override
  public String getDescription() {
    return "Slap a player silly.";
  }

  @Override
  public String getUsage() {
    return "/troll slap <player> [strength]";
  }

  @Override
  public void onCommand(Player player, String[] args) {
    final double strength = plugin.getConfig().getDouble("troll.slap.strength");

    if (strength > 10) {
      plugin.getConfig().set("troll.slap.strength", 10);
    }

    if (args.length == 1) {
      player.sendMessage(this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.slap")) {
          player.sendMessage(String.format(Util.Chat("Too bad, %s can't be slapped"), target.getName()));
        } else {
          target.damage(strength);

          target.sendMessage(String.format(Util.Chat("&c%s slapped you silly."), player.getName()));
          player.sendMessage(String.format(Util.Chat("&aYou slapped %s silly."), target.getName()));
        }
      }
    } else if (args.length > 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.slap")) {
          player.sendMessage(String.format(Util.Chat("Too bad, %s can't be slapped"), target.getName()));
        } else {
          int player_strength = 0;

          try {
            player_strength = Integer.parseInt(args[2]);
          } catch (NumberFormatException exception) {
            Logger.log(Logger.LogLevel.WARNING, exception.getLocalizedMessage());
          }

          target.damage(player_strength);

          target.sendMessage(String.format(Util.Chat("&c%s slapped you silly."), player.getName()));
          player.sendMessage(String.format(Util.Chat("&aYou slapped %s silly, with the strength of %d horses."), target.getName(), player_strength));
        }
      }
    }
  }
}
