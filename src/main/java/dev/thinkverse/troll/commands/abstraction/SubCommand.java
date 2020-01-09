package dev.thinkverse.troll.commands.abstraction;

import dev.thinkverse.troll.TrollPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public abstract class SubCommand {
  @NotNull
  public abstract String getName();
  @Nullable
  public abstract String getDescription();
  @Nullable
  public abstract String getPermission();
  @Nullable
  public abstract String[] getPermissions();
  @NotNull
  public abstract String getUsage();

  public abstract void onCommand(@NotNull TrollPlugin plugin, @NotNull Player player, @NotNull String[] args);

  protected boolean checkPermission(@NotNull Player player) {
    return player.hasPermission(getPermission());
  }

  protected boolean checkPermissions(@NotNull Player player) { return Arrays.stream(Objects.requireNonNull(getPermissions())).anyMatch(player::hasPermission); }
}
