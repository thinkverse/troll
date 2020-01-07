package dev.thinkverse.troll;

import dev.thinkverse.troll.commands.TrollCommand;
import dev.thinkverse.troll.utils.UpdateChecker;
import dev.thinkverse.troll.utils.config.DefaultConfig;
import dev.thinkverse.troll.utils.enums.LogLevel;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import dev.thinkverse.troll.utils.Logger;

import java.util.Objects;

public final class TrollPlugin extends JavaPlugin {
  private final Logger logger = new Logger();

  private DefaultConfig defaultConfig;

  @Override
  public void onEnable() {
    this.getLogger().log(LogLevel.INFO, "Troll Plugin enabled.");

    this.setVariables();
    this.checkUpdates();
    this.loadConfig();

    this.registerCommand("troll", new TrollCommand(this), true);
  }

  @Override
  public void onDisable() {
    this.getLogger().log(LogLevel.INFO, "Troll Plugin disabled.");
  }

  private void setVariables() {
    this.defaultConfig = new DefaultConfig(this);
  }

  public DefaultConfig getDefaultConfig() {
    return this.defaultConfig;
  }

  private void loadConfig() {
    this.defaultConfig.getConfig().options().copyDefaults(true);
    this.defaultConfig.saveDefaultConfig();
    this.defaultConfig.saveConfig();
  }

  private void checkUpdates() {
    new UpdateChecker(this, 74111).getVersion(version -> {
      if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
        getLogger().log(LogLevel.INFO, "There is not a new update available.");
      } else {
        getLogger().log(LogLevel.INFO, "There is a new update available for " + getDescription().getName());
      }
    });
  }

  @Override
  public Logger getLogger() {  return this.logger; }
  protected final void registerEvents(Listener listener) {
    this.getServer().getPluginManager().registerEvents(listener, this);
  }

  protected final void registerCommand(String command, CommandExecutor executor, boolean tabCompleter) {
    Objects.requireNonNull(this.getCommand(command)).setExecutor(executor);
    if (tabCompleter) registerTabCompleter(command, (TabCompleter) executor);
  }

  protected final void registerTabCompleter(String command, TabCompleter completer) {
    Objects.requireNonNull(this.getCommand(command)).setTabCompleter(completer);
  }
}
