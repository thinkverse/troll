package dev.thinkverse.troll.utils;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.utils.enums.LogLevel;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public final class UpdateChecker {
  private TrollPlugin plugin;
  private int resource;

  public UpdateChecker(TrollPlugin plugin, int resource) {
    this.plugin = plugin;
    this.resource = resource;
  }

  public void getVersion(final Consumer<String> consumer) {
    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
      try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resource).openStream(); Scanner scanner = new Scanner(inputStream)) {
        if (scanner.hasNext()) consumer.accept(scanner.next());
      } catch (IOException exception) {
        this.plugin.getLogger().log(LogLevel.INFO, "Cannot search for updates:" + exception.getMessage());
      }
    });
  }
}
