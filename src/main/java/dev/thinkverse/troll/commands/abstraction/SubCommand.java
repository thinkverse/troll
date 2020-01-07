package dev.thinkverse.troll.commands.abstraction;

import dev.thinkverse.troll.TrollPlugin;
import org.bukkit.entity.Player;

public abstract class SubCommand {
  public abstract String getName();
  public abstract String getDescription();
  public abstract String getPermission();
  public abstract String getUsage();
  public abstract void onCommand(TrollPlugin plugin, Player player, String args[]);
}
