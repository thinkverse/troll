package dev.thinkverse.troll.utils.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {
  public abstract String getName();
  public abstract String getDescription();
  public abstract String getUsage();
  public abstract void onCommand(Player player, String args[]);
}
