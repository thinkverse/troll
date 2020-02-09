package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SmiteCommand extends SubCommand {
  @NotNull
  @Override
  public String getName() { return "smite"; }

  @Override
  public String getDescription() { return "Strike a user with lightning."; }

  @Override
  public String getPermission() { return null; }

  @Override
  public String[] getPermissions() { return new String[]{"troll.smite.damage", "troll.admin", "troll.*"}; }

  @NotNull
  @Override
  public String getUsage() { return "/troll smite <player>"; }

  @Override
  public void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args) {
    final boolean damage = plugin.getConfig().getBoolean("troll.smite.damage", true);

    if (args.length == 1) {
      Chat.message(player, this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.smite")) {
          Chat.message(player, String.format("Ooh, seems Zeus likes %s.", target.getName()));
        } else {
          strikeTarget(target, damage);

          Chat.message(target, String.format("&c%s called upon Zeus.", player.getName()));
          Chat.message(player, String.format("&aYou struck %s with lightning.", target.getName()));
        }
      }
    } else if (args.length == 3) {
      final Player target = Bukkit.getPlayer(args[1]);
      final boolean player_damage = Boolean.parseBoolean(args[2]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.smite")) {
          Chat.message(player, String.format("Ooh, seems Zeus likes %s.", target.getName()));
        } else {

          if (!checkPermissions(player)) {
            Chat.message(player, plugin.getConfig().getString("prefix") + plugin.getConfig().getString("no-permission"));
          } else {
            strikeTarget(target, player_damage);

            Chat.message(target, String.format("&c%s called upon Zeus.", player.getName()));
            Chat.message(player, String.format("&aYou struck %s with lightning.", target.getName()));
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
