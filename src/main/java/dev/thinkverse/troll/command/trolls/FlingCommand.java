package dev.thinkverse.troll.command.trolls;

import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.utils.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
  public String getUsage() {
    return "/troll fling <player>";
  }

  @Override
  public void onCommand(Player player, String[] args) {
    if (args.length == 1) {
      player.sendMessage(this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.fling")) {
          player.sendMessage(String.format(Util.Chat("Ooh, seems %s is stuck to the ground."), target.getName()));
        } else {
          target.setVelocity(Vector.getRandom());

          target.sendMessage(String.format(Util.Chat("&c%s flinged you away."), player.getName()));
          player.sendMessage(String.format(Util.Chat("&aYou flinged %s away."), target.getName()));
        }
      }

    }
  }
}
