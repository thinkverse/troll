package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FlingCommand extends SubCommand {
  @Override
  public String getName() {
    return "fling";
  }

  @Override
  public String getDescription() {
    return "Fling player into the sky.";
  }

  @Override
  public String getPermission() { return null; }

  @Override
  public String[] getPermissions() { return new String[0]; }

  @Override
  public String getUsage() {
    return "/troll fling <player>";
  }

  @Override
  public void onCommand(TrollPlugin plugin, Player player, @NotNull String[] args) {
    if (args.length == 1) {
      Util.message(player, this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.fling")) {
          Util.message(player, String.format("Ooh, seems %s is stuck to the ground.", target.getName()));
        } else {
          target.setVelocity(Vector.getRandom());

          Util.message(target, String.format("&c%s flinged you away.", player.getName()));
          Util.message(player, String.format("&aYou flinged %s away.", target.getName()));
        }
      }

    }
  }
}
