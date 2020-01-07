package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.enums.LogLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class BlindCommand extends SubCommand {

  @Override
  public String getName() {
    return "blind";
  }

  @Override
  public String getDescription() {
    return "Blind a player";
  }

  @Override
  public String getPermission() { return null; }

  @Override
  public String getUsage() {
    return "/troll blind <player> [duration]";
  }

  @Override
  public void onCommand(TrollPlugin plugin, Player player, String[] args) {
    final int duration = plugin.getDefaultConfig().getConfig().getInt("troll.blindness.duration");
    final int amplifier = plugin.getDefaultConfig().getConfig().getInt("troll.blindness.amplifier");

    if (args.length == 1) {
      player.sendMessage(this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.blind")) {
          Util.message(player, String.format("Too bad, %s has really good vision..", target.getName()));
        } else {
          target.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(duration, amplifier));

          Util.message(target, String.format("&c%s blinded you.", player.getName()));
          Util.message(player, String.format("&aYou blinded %s.", target.getName()));
        }
      }
    } else if (args.length > 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.blind")) {
          Util.message(player, String.format("Too bad, %s has really good vision..", target.getName()));
        } else {
          int player_duration = 0;

          try {
            player_duration = Integer.parseInt(args[2]);
          } catch (NumberFormatException exception) {
            plugin.getLogger().log(LogLevel.WARNING, exception.getMessage());
          }

          target.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(player_duration * 20, amplifier));

          Util.message(target, String.format("&c%s blinded you.", player.getName()));
          Util.message(player, String.format("&aYou blinded %s, for %d seconds.", target.getName(), player_duration));
        }
      }
    }
  }
}