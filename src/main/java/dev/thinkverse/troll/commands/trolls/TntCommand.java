package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TntCommand extends SubCommand {
  @Override
  public @NotNull String getName() { return "tnt"; }

  @Override
  public @Nullable String getDescription() {
    return "Summons TNT rain";
  }

  @Override
  public @Nullable String getPermission() {
    return "troll.use.tnt";
  }

  @Override
  public @Nullable String[] getPermissions() {
    return new String[0];
  }

  @Override
  public @NotNull String getUsage() {
    return "/troll tnt <player>";
  }

  @Override
  public void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args) {
    if (args.length == 1) {
      Chat.message(player, this.getUsage());
    } else if (args.length == 2 && checkPermission(player)) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.tnt")) {
          Chat.message(player, String.format("Ooh, seems %s has an umbrella.", target.getName()));
        } else {
          final int steps = 3;

          protectTarget(plugin, target);
          createSafeTNTSpawn(target, steps);

          final Location location = target.getLocation().clone().add(0, steps -1, 0);

          for (int i = 0; i < 10; i++) {
            TNTPrimed tnt = target.getWorld().spawn(location, TNTPrimed.class);
            tnt.setIsIncendiary(plugin.getDefaultConfig().getConfig().getBoolean("troll.tnt.fire", false));
            tnt.setFuseTicks(20);
          }

          Chat.message(target, String.format("&cHey %s, watch out below!", target.getName()));
          Chat.message(player, String.format("&aYou sent %s a volley of TNT", target.getName()));
        }
      }
    }
  }

  private void createSafeTNTSpawn(@NotNull Player player, int steps) {
    final World world = player.getWorld();
    final Location playerLoc = player.getLocation().clone().add(0, 2, 0);

    for (int i = 0; i < steps; i++) {
      final Location east = playerLoc.clone().add(1, i, 0);
      player.getWorld().getBlockAt(east).setType(Material.AIR);

      final Location west = playerLoc.clone().add(-1, i, 0);

      player.getWorld().getBlockAt(west).setType(Material.AIR);
      final Location middle = playerLoc.clone().add(0, i, 0);
      player.getWorld().getBlockAt(middle).setType(Material.AIR);

      final Location south = playerLoc.clone().add(0, i, 1);
      player.getWorld().getBlockAt(south).setType(Material.AIR);

      final Location north = playerLoc.clone().add(0, i, -1);
      player.getWorld().getBlockAt(north).setType(Material.AIR);
    }
  }

  private void protectTarget(@NotNull JavaPlugin plugin, @NotNull Player player) {
    player.setInvulnerable(true);
    Bukkit.getScheduler().runTaskLater(plugin, () -> player.setInvulnerable(false), 20 * 3);
  }
}