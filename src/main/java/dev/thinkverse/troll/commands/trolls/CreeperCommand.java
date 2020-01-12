package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.Chat;
import org.bukkit.Bukkit;
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
        } else {
          Creeper creeper = (Creeper) target.getWorld().spawnEntity(target.getLocation(), EntityType.CREEPER);
          creeper.setTarget(target);

          Chat.message(target, String.format("&c%s sent you a surprise.", player.getName()));
          Chat.message(player, String.format("&aYou sent %s a friendly creeper.", target.getName()));
        }
      }
    }
  }
}
