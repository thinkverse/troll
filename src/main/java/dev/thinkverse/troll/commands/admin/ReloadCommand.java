package dev.thinkverse.troll.commands.admin;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.Util;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends SubCommand {
  @Override
  public String getName() {
    return "reload";
  }

  @Override
  public String getDescription() {
    return "Reload troll config";
  }

  @Override
  public String getPermission() { return "troll.admin"; }

  @Override
  public String[] getPermissions() { return new String[0]; }

  @Override
  public String getUsage() {
    return "/troll reload";
  }

  @Override
  public void onCommand(TrollPlugin plugin, Player player, @NotNull String[] args) {
    if (!checkPermission(player)) {
      Util.message(player, plugin.getDefaultConfig().getConfig().getString("prefix") + plugin.getDefaultConfig().getConfig().getString("no-permission"));
    } else if (args.length >= 2) {
      Util.message(player, this.getUsage());
    } else {
      plugin.getDefaultConfig().reloadConfig();
      Util.message(player, plugin.getDefaultConfig().getConfig().getString("prefix") + "&aConfig reloaded.");
    }
  }
}
