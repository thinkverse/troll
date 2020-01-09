package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Level;

public class BlindCommand extends SubCommand {

  @NotNull
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
  public String[] getPermissions() { return new String[0]; }

  @NotNull
  @Override
  public String getUsage() {
    return "/troll blind <player> [duration]";
  }

  @Override
  public void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args) {
    int duration = plugin.getDefaultConfig().getConfig().getInt("troll.blindness.duration");
    final int amplifier = plugin.getDefaultConfig().getConfig().getInt("troll.blindness.amplifier");

    if (args.length == 1) {
      player.sendMessage(this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.blind")) {
          Util.message(player, String.format("Too bad, %s has really good vision..", target.getName()));
        } else {
          if (plugin.getServerVersion().startsWith("1.13")) { duration = (int) (duration * 4); }
          target.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(duration, amplifier));

          Util.message(target, String.format("&c%s blinded you.", player.getName()));
          Util.message(player, String.format("&aYou blinded %s.", target.getName()));
        }
      }
    } else if (args.length > 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.blind")) {
          Util.message(player, String.format("Too bad, %s has really good vision..", target.getName()));
        } else {
          int player_duration = 0;

          try {
            player_duration = Integer.parseInt(args[2]);
          } catch (NumberFormatException exception) {
            plugin.getLogger().log(Level.WARNING, exception.getMessage());
          }

          if (plugin.getServerVersion().startsWith("1.13")) { player_duration = (int) (player_duration * 4); }
          target.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(player_duration * 20, amplifier));

          Util.message(target, String.format("&c%s blinded you.", player.getName()));
          Util.message(player, String.format("&aYou blinded %s, for %d seconds.", target.getName(), player_duration));
        }
      }
    }
  }
}
