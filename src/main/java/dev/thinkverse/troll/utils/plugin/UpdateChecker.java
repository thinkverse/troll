package dev.thinkverse.troll.utils.plugin;

import dev.thinkverse.troll.TrollPlugin;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

public final class UpdateChecker {
  private TrollPlugin plugin;
  private int resource;

  public UpdateChecker(TrollPlugin plugin, int resource) {
    this.plugin = plugin;
    this.resource = resource;
  }

  public void getVersion(final Consumer<SemanticVersion> consumer) {
    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
      try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resource).openStream(); Scanner scanner = new Scanner(inputStream)) {
        if (scanner.hasNext()) consumer.accept(new SemanticVersion(scanner.next()));
      } catch (IOException | ParseException exception) {
        if (exception instanceof IOException) {
          this.plugin.getLogger().log(Level.INFO, "Cannot search for updates: " + exception.getMessage());
        } else {
          this.plugin.getLogger().log(Level.INFO, "Issue parsing plugin version: " + exception.getMessage());
        }
      }
    });
  }
}
