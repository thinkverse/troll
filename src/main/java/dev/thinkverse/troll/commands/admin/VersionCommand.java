package dev.thinkverse.troll.commands.admin;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.plugin.UpdateChecker;
import dev.thinkverse.troll.utils.Chat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VersionCommand extends SubCommand {
  @NotNull
  @Override
  public String getName() { return "version"; }

  @Override
  public String getDescription() { return "Checks current version"; }

  @Override
  public String getPermission() { return "troll.version"; }

  @Override
  public String[] getPermissions() { return new String[]{"troll.version", "troll.admin", "troll.*"}; }

  @NotNull
  @Override
  public String getUsage() { return "/troll version"; }

  @Override
  public void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args) {
    if (!checkPermissions(player)) {
      Chat.message(player, plugin.getConfig().getString("prefix") + plugin.getConfig().getString("no-permission"));
    } else if (args.length >= 2) {
      Chat.message(player, this.getUsage());
    } else {
      checkUpdates(plugin, player);
    }
  }

  private void checkUpdates(TrollPlugin plugin, Player player) {
    Chat.message(player, String.format(plugin.getConfig().getString("prefix") + "&eCurrent version: &f%s", plugin.getDescription().getVersion()));

    new UpdateChecker(plugin, 74111).getVersion(remote -> {
      if (remote.isUpdateFor(plugin.getSemanticVersion())) {
        Chat.message(player, String.format("&aNew version available: &f%s", remote.toString()));
      }
    });
  }

}
