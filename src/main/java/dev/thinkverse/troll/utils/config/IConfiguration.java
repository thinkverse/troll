package dev.thinkverse.troll.utils.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfiguration {
  public FileConfiguration getConfig();
  public void saveDefaultConfig();
  public void saveConfig();
  public void reloadConfig();
}
