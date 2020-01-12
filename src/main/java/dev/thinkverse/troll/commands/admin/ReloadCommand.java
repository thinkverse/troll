package dev.thinkverse.troll.commands.admin;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.Chat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends SubCommand {
  @NotNull
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

  @NotNull
  @Override
  public String getUsage() {
    return "/troll reload";
  }

  @Override
  public void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args) {
    if (!checkPermission(player)) {
      Chat.message(player, plugin.getDefaultConfig().getConfig().getString("prefix") + plugin.getDefaultConfig().getConfig().getString("no-permission"));
    } else if (args.length >= 2) {
      Chat.message(player, this.getUsage());
    } else {
      plugin.getDefaultConfig().reloadConfig();
      Chat.message(player, plugin.getDefaultConfig().getConfig().getString("prefix") + "&aConfig reloaded.");
    }
  }
}
