package dev.thinkverse.troll.utils;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Random;

public final class Teleport {
  private static Random random = new Random();

  private static boolean isAir(Location location) {
    return location.getBlock().getType() == Material.AIR;
  }

  public static Location generateSafeSpawn(final Location location, final int radius) {
    location.setY(location.getWorld().getHighestBlockYAt(location));

    final Location spawn = location.clone().add(Teleport.getRandom().nextInt(radius * 2) - radius, 0, Teleport.getRandom().nextInt(radius * 2) - radius);

    return isSafeSpawnLocation(spawn) ? spawn : generateSafeSpawn(location, radius);
  }

  public static boolean isSafeSpawnLocation(Location location) {
    return isAir(location.clone().add(0, 1, 0)) && isAir(location) && location.clone().subtract(0,1,0).getBlock().getType().isSolid();
  }

  private static Location getBlockBelow(Location location) {
    Location below = location.subtract(0, 1, 0);
    return isAir(below) ? getBlockBelow(below) : below;
  }

  private static boolean betweenRange(int safeY, double y) {
    return (Math.max((int) y - 2, safeY) == Math.min(safeY, (int) y + 2));
  }

  public static Random getRandom() { return random; }
}
