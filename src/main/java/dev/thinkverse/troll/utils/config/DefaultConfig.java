package dev.thinkverse.troll.utils.config;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.utils.enums.LogLevel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class DefaultConfig {
  private final String filename = "config.yml";
  private final TrollPlugin plugin;

  private FileConfiguration configuration;
  private File file;

  public DefaultConfig(final TrollPlugin plugin) { this.plugin = plugin; }

  public void reloadConfig() {
    if (this.file == null) this.file = new File(this.plugin.getDataFolder(), this.filename);

    this.configuration = YamlConfiguration.loadConfiguration(this.file);

    Reader configReader = new InputStreamReader(Objects.requireNonNull(this.plugin.getResource(this.filename)), StandardCharsets.UTF_8);

    if (Objects.nonNull(configReader)) {
      YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(configReader);
      this.configuration.setDefaults(defaultConfig);
      this.configuration.options().copyDefaults(true);
    }
  }

  public FileConfiguration getConfig() {
    if (Objects.isNull(this.configuration)) reloadConfig();
    return this.configuration;
  }

  public void saveConfig() {
    if(Objects.isNull(this.configuration) || Objects.isNull(this.file)) return;

    try {
      getConfig().save(this.file);
    } catch (IOException exception) {
      plugin.getLogger().log(LogLevel.WARNING, exception.getMessage());
    }
  }

  public void saveDefaultConfig() {
    if (Objects.isNull(this.file)) this.file = new File(this.plugin.getDataFolder(), this.filename);
    if (!this.file.exists()) this.plugin.saveResource(this.filename, false);
  }

}
