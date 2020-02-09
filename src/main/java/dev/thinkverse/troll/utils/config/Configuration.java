package dev.thinkverse.troll.utils.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class Configuration implements IConfiguration {
  private FileConfiguration config = null;
  private File configFile = null;
  private String configName;
  private JavaPlugin plugin;

  public Configuration(JavaPlugin plugin, String configName) {
    this.configName = configName;
    this.plugin = plugin;

    this.createConfiguration();
  }

  @Override
  public FileConfiguration getConfig() {
    if (this.config == null) this.reloadConfig();
    return this.config;
  }

  @Override
  public void saveDefaultConfig() {
    if (this.configFile == null) { new File(this.plugin.getDataFolder(), this.configName); }
    if (!this.configFile.exists()) {
     this.configFile.getParentFile().mkdirs();
     this.plugin.saveResource(this.configName, false);
    }
  }

  /**
   * Saving config removes comments, use with caution.
   */
  @Override
  @Deprecated
  public void saveConfig() {
    try {
      this.getConfig().save(this.configFile);
    } catch (IOException exception) {
      this.plugin.getLogger().severe(exception.getMessage());
      exception.getStackTrace();
    }
  }

  @Override
  public void reloadConfig() {
    this.config = YamlConfiguration.loadConfiguration(this.configFile);

    final Reader configReader = new InputStreamReader(this.plugin.getResource(this.configName), StandardCharsets.UTF_8);

    final YamlConfiguration configDefault = YamlConfiguration.loadConfiguration(configReader);

    this.config.setDefaults(configDefault);
    this.config.options().copyDefaults(true);
  }

  private void createConfiguration() {
    this.configFile = new File(this.plugin.getDataFolder(), this.configName);

    this.saveDefaultConfig();

    this.config = new YamlConfiguration();

    try {
      this.config.load(this.configFile);
    } catch (IOException | InvalidConfigurationException exception) {
      this.plugin.getLogger().severe(exception.getMessage());
      exception.getStackTrace();
    }
  }

}
