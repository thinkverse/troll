package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SmiteCommand extends SubCommand {
  @Override
  public String getName() { return "smite"; }

  @Override
  public String getDescription() { return "Strike a user with lightning."; }

  @Override
  public String getPermission() { return null; }

  @Override
  public String[] getPermissions() { return new String[]{"troll.smite.damage", "troll.admin", "troll.*"}; }

  @Override
  public String getUsage() { return "/troll smite <player>"; }

  @Override
  public void onCommand(TrollPlugin plugin, Player player, String[] args) {
    final boolean damage = plugin.getDefaultConfig().getConfig().getBoolean("troll.smite.damage");

    if (args.length == 1) {
      Util.message(player, this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.smite")) {
          Util.message(player, String.format("Ooh, seems Zeus likes %s.", target.getName()));
        } else {
          strikeTarget(target, damage);

          Util.message(target, String.format("&c%s called upon Zeus.", player.getName()));
          Util.message(player, String.format("&aYou struck %s with lightning.", target.getName()));
        }
      }
    } else if (args.length == 3) {
      final Player target = Bukkit.getPlayer(args[1]);
      final boolean player_damage = Boolean.parseBoolean(args[2]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.smite")) {
          Util.message(player, String.format("Ooh, seems Zeus likes %s.", target.getName()));
        } else {

          if (!checkPermissions(player)) {
            Util.message(player, plugin.getDefaultConfig().getConfig().getString("prefix") + plugin.getDefaultConfig().getConfig().getString("no-permission"));
          } else {
            strikeTarget(target, player_damage);

            Util.message(target, String.format("&c%s called upon Zeus.", player.getName()));
            Util.message(player, String.format("&aYou struck %s with lightning.", target.getName()));
          }
        }
      }
    }
  }

  private void strikeTarget(Player player, Boolean damage) {
    if (damage) {
      player.getWorld().strikeLightning(player.getLocation());
    } else {
      player.getWorld().strikeLightningEffect(player.getLocation());
    }
  }
}
