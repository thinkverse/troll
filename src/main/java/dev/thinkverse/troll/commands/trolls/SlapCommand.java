package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.utils.Chat;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Level;

public class SlapCommand extends SubCommand {
  private TrollPlugin plugin;
  private double strength;

  @NotNull
  @Override
  public String getName() {
    return "slap";
  }

  @Override
  public String getDescription() {
    return "Slap a player silly.";
  }

  @Override
  public String getPermission() { return null; }

  @Override
  public String[] getPermissions() { return new String[0]; }

  @NotNull
  @Override
  public String getUsage() {
    return "/troll slap <player> [strength]";
  }

  @Override
  public void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args) {
    this.setPlugin(plugin);
    this.setStrength(plugin.getConfig().getDouble("troll.slap.strength", 10));

    if (args.length == 1) {
      Chat.message(player, this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.slap")) {
          Chat.message(player, String.format("&cToo bad, %s can't be slapped", target.getName()));
        } else {
          target.damage(strength);

          Chat.message(target, String.format("&c%s slapped you silly.", player.getName()));
          Chat.message(player, String.format("&aYou slapped %s silly.", target.getName()));
        }
      }
    } else if (args.length > 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.slap")) {
          Chat.message(player, String.format("&cToo bad, %s can't be slapped", target.getName()));
        } else {
          int player_strength = 0;

          try {
            player_strength = Integer.parseInt(args[2]);
          } catch (NumberFormatException exception) {
            plugin.getLogger().log(Level.WARNING, exception.getMessage());
          }

          /* Make sure the strength passed by the player doesn't exceed 10 */
          if (player_strength > 10) player_strength = 10;

          target.damage(player_strength);

          Chat.message(target, String.format("&c%s slapped you silly.", player.getName()));
          Chat.message(player, String.format("&aYou slapped %s silly, with the strength of %d horses.", target.getName(), player_strength));
        }
      }
    }
  }

  private double getStrength() {
    if (this.strength > 10) {
      this.strength = 10;
      this.getPlugin().getConfig().set("troll.slap.strength", 10);
      this.getPlugin().saveConfig();
    } return this.strength;
  }

  private TrollPlugin getPlugin() { return this.plugin; }

  private void setStrength(double strength) { this.strength = strength; }
  private void setPlugin(TrollPlugin plugin) { this.plugin = plugin; }
}
