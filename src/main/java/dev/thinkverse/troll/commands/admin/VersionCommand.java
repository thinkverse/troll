package dev.thinkverse.troll.commands.admin;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.UpdateChecker;
import dev.thinkverse.troll.utils.Util;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VersionCommand extends SubCommand {
  @Override
  public String getName() { return "version"; }

  @Override
  public String getDescription() { return "Checks current version"; }

  @Override
  public String getPermission() { return "troll.version"; }

  @Override
  public String[] getPermissions() { return new String[]{"troll.version", "troll.admin", "troll.*"}; }

  @Override
  public String getUsage() { return "/troll version"; }

  @Override
  public void onCommand(TrollPlugin plugin, Player player, @NotNull String[] args) {
    if (!checkPermissions(player)) {
      Util.message(player, plugin.getDefaultConfig().getConfig().getString("prefix") + plugin.getDefaultConfig().getConfig().getString("no-permission"));
    } else if (args.length >= 2) {
      Util.message(player, this.getUsage());
    } else {
      checkUpdates(plugin, player);
    }
  }

  private void checkUpdates(TrollPlugin plugin, Player player) {
    String current = plugin.getDescription().getVersion();
    new UpdateChecker(plugin, 74111).getVersion(version -> {
      Util.message(player, String.format(plugin.getDefaultConfig().getConfig().getString("prefix") + "Current version: &f%s", current));

      if (!current.equalsIgnoreCase(version)) {
        Util.message(player, String.format("&aUpdate available: &f%s", version));
        Util.notify(player, "troll.notify", String.format(plugin.getDefaultConfig().getConfig().getString("prefix") + "&aUpdate available: &f%s", version));
      }
    });
  }

}
