package dev.thinkverse.troll.commands.abstraction;

import dev.thinkverse.troll.TrollPlugin;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class SubCommand {
  public abstract String getName();
  public abstract String getDescription();
  public abstract String getPermission();
  public abstract String[] getPermissions();
  public abstract String getUsage();
  public abstract void onCommand(TrollPlugin plugin, Player player, String args[]);

  protected boolean checkPermission(Player player) {
    return player.hasPermission(getPermission());
  }

  protected boolean checkPermissions(Player player) {
    return Arrays.stream(getPermissions()).anyMatch(player::hasPermission);
  }
}
