package dev.thinkverse.troll;

import dev.thinkverse.troll.command.TrollCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import dev.thinkverse.troll.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Troll extends JavaPlugin {
  private FileConfiguration config;

  @Override
  public void onEnable() {
    Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------------");
    Logger.log(Logger.LogLevel.INFO, "Troll Plugin loaded");
    Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------------");

    createConfig();

    registerCommand("troll", new TrollCommand());
    registerTabCompleter("troll", new TrollCommand());
  }

  @Override
  public void onDisable() {
    Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------------");
    Logger.log(Logger.LogLevel.INFO, "Troll Plugin disabled");
    Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------------");
  }

  protected final void registerEvents(Listener listener) {
    getServer().getPluginManager().registerEvents(listener, this);
  }

  protected final void registerCommand(String command, CommandExecutor executor) {
    Objects.requireNonNull(this.getCommand(command)).setExecutor(executor);
  }

  protected final void registerTabCompleter(String command, TabCompleter completer) {
    Objects.requireNonNull(this.getCommand(command)).setTabCompleter(completer);
  }

  private void createConfig() {
    File file = new File(getDataFolder(), "config.yml");

    if (!file.exists()) {
      file.getParentFile().mkdirs();
      saveResource("config.yml", false);
    }

    config = new YamlConfiguration();

    try {
      config.load(file);
    } catch (InvalidConfigurationException | IOException exception) {
      exception.printStackTrace();
    }
  }

}
