package dev.thinkverse.troll.commands.trolls;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import dev.thinkverse.troll.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FakeOpCommand extends SubCommand {
  @Override
  public @NotNull String getName() {
    return "op";
  }

  @Override
  public @Nullable String getDescription() {
    return "Give player a fake OP status";
  }

  @Override
  public @Nullable String getPermission() {
    return null;
  }

  @Nullable
  @Override
  public String[] getPermissions() {
    return new String[0];
  }

  @Override
  public @NotNull String getUsage() {
    return "/troll op <player>";
  }

  @Override
  public void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args) {
    final String message = plugin.getDefaultConfig().getConfig().getString("troll.messages.op");

    if (args.length == 1) {
      player.sendMessage(this.getUsage());
    } else if (args.length == 2) {
      final Player target = Bukkit.getPlayer(args[1]);

      if (!Objects.isNull(target)) {
        if (target.hasPermission("troll.bypass.op")) {
          Chat.message(player, String.format("Sorry, but the IQ of %s is to high to be fooled.", target.getName()));
        } else {
          Chat.message(target, message.replace("{player}", target.getName()));
          Chat.message(player, String.format("&aYou fooled %s into thinking they're OP.", target.getName()));
        }
      }
    }

  }
}
