package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
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
  public String getPermission() { return null; }

  @Override
  public String[] getPermissions() { return new String[0]; }

  @Override
  public String getUsage() {
    return "/troll speak <player>";
  }

  @Override
  public void onCommand(TrollPlugin plugin, Player player, String[] args) {
    if (args.length <= 2) {
      Util.message(player, this.getUsage());
    } else {
      final Player target = Bukkit.getPlayer(args[1]);

      StringBuilder buffer = new StringBuilder();

      for (int i = 2; i < args.length; i++) {
        buffer.append(args[i]).append(" ");
      }

      String message = buffer.toString().trim();

      if (target != null) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.speak")) {
          Util.message(player, String.format("Too bad, %s can't be slapped", target.getName()));

        } else {
          target.chat(message);
          Util.message(player, String.format("&aYou spoke as %s.", target.getName()));
        }
      } else {
        Util.message(player, "&aPlayer doesn't exist.");
      }
    }
  }
}
