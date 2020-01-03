package dev.thinkverse.troll.command.trolls;

import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.utils.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpeakCommand extends SubCommand {
  @Override
  public String getName() {
    return "speak";
  }

  @Override
  public String getDescription() {
    return "Write messages as another player";
  }

  @Override
  public String getUsage() {
    return "/troll speak <player>";
  }

  @Override
  public void onCommand(Player player, String[] args) {
    if (args.length <= 2) {
      player.sendMessage(this.getUsage());
    } else {
      final Player target = Bukkit.getPlayer(args[1]);

      StringBuilder buffer = new StringBuilder();

      for (int i = 2; i < args.length; i++) {
        buffer.append(args[i]).append(" ");
      }

      String message = buffer.toString().trim();

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.speak")) {
          player.sendMessage(String.format(Util.Chat("Too bad, %s can't be slapped"), target.getName()));
        } else {

          target.chat(message);
          player.sendMessage(String.format(Util.Chat("&aYou spoke as %s."), target.getName()));
        }
      } else {
        player.sendMessage(Util.Chat("&aPlayer doesn't exist."));
      }
    }
  }
}
