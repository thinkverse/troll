package dev.thinkverse.troll.command.trolls;

import dev.thinkverse.troll.Troll;
import dev.thinkverse.troll.utils.Logger;
import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.utils.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class BlindCommand extends SubCommand {
  private Plugin plugin = Troll.getPlugin(Troll.class);

  @Override
  public String getName() {
    return "blind";
  }

  @Override
  public String getDescription() {
    return "Blind a player";
  }

  @Override
  public String getUsage() {
    return "/troll blind <player> [duration]";
  }

  @Override
  public void onCommand(Player player, String[] args) throws NumberFormatException {
    final int duration = plugin.getConfig().getInt("troll.blindness.duration");
    final int amplifier = plugin.getConfig().getInt("troll.blindness.amplifier");

    if (args.length == 1) {
      player.sendMessage(this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.blind")) {
          player.sendMessage(String.format(Util.Chat("Too bad, %s has really good vision.."), target.getName()));
        } else {
          target.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(duration, amplifier));

          target.sendMessage(String.format(Util.Chat("&c%s blinded you."), player.getName()));
          player.sendMessage(String.format(Util.Chat("&aYou blinded %s."), target.getName()));
        }
      }
    } else if (args.length > 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.blind")) {
          player.sendMessage(String.format(Util.Chat("Too bad, %s has really good vision.."), target.getName()));
        } else {
          int player_duration = 0;

          try {
            player_duration = Integer.parseInt(args[2]);
          } catch (NumberFormatException exception) {
            Logger.log(Logger.LogLevel.WARNING, exception.getMessage());
          }

          target.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(player_duration * 20, amplifier));

          target.sendMessage(String.format(Util.Chat("&c%s blinded you."), player.getName()));
          player.sendMessage(String.format(Util.Chat("&aYou blinded %s for %d seconds."), target.getName(), player_duration));
        }
      }
    }
  }
}
