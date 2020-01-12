package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.Chat;
import dev.thinkverse.troll.utils.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CreeperCommand extends SubCommand {
  @NotNull
  @Override
  public String getName() {
    return "creeper";
  }

  @Override
  public String getDescription() {
    return "Spawn creeper on target";
  }

  @Override
  public String getPermission() {
    return null;
  }

  @Override
  public String[] getPermissions() {
    return new String[0];
  }

  @NotNull
  @Override
  public String getUsage() {
    return "/troll creeper <player>";
  }

  @Override
  public void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args) {
    if (args.length == 1) {
      Chat.message(player, this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.*") || target.hasPermission("troll.bypass.spawn")) {
          Chat.message(player, String.format("Ooh, seems %s is protected by Herobrine.", target.getName()));
        } else if (target.isFlying()) {
          Chat.message(player, String.format("%s cannot be flying.", target.getName()));
        } else if (target.getLocation().getY() <= 60.0) {
          Chat.message(player, String.format("%s must be above ground.", target.getName()));
        } else {
          final int amount = plugin.getDefaultConfig().getConfig().getInt("troll.creeper.amount", 4);
          final boolean charged = plugin.getDefaultConfig().getConfig().getBoolean("troll.creeper.charged", false);

          for (int i = 0; i < amount; i++) {
            try {
              Location location = Teleport.generateSafeSpawn(target.getLocation(), 5);

              Creeper creeper = (Creeper) target.getWorld().spawnEntity(location, EntityType.CREEPER);
              creeper.setTarget(target);
              creeper.setPowered(charged);
            } catch (StackOverflowError exception) {
              plugin.getLogger().info(exception.getMessage());
            }
          }

          Chat.message(target, String.format("&c%s sent you a surprise.", player.getName()));
          Chat.message(player, String.format("&aYou sent %s a friendly creeper.", target.getName()));
        }
      }
    }
  }
}
