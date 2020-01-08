package dev.thinkverse.troll;

import dev.thinkverse.troll.commands.TrollCommand;
import dev.thinkverse.troll.utils.UpdateChecker;
import dev.thinkverse.troll.utils.config.DefaultConfig;
import dev.thinkverse.troll.utils.enums.LogLevel;
import dev.thinkverse.troll.utils.metrics.MetricsLite;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import dev.thinkverse.troll.utils.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class TrollPlugin extends JavaPlugin {
  private final Logger logger = new Logger();

  private MetricsLite metrics;
  private DefaultConfig defaultConfig;

  @Override
  public void onLoad() { this.logPluginStatus("Loading"); }

  @Override
  public void onEnable() {
    this.logPluginStatus("Enabling");

    this.setVariables();
    this.checkUpdates();
    this.loadConfig();

    this.setMetrics();

    this.registerCommand("troll", new TrollCommand(this), true);
  }

  @Override
  public void onDisable() { this.logPluginStatus("Disabling"); }

  private void setMetrics() { this.metrics = new MetricsLite(this); }

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
        getLogger().log(LogLevel.INFO, "No new update available.");
      } else {
        getLogger().log(LogLevel.INFO, "New update available.");
      }
    });
  }

  private void logPluginStatus(String status) { this.getLogger().log(LogLevel.INFO, status + " " + this.getDescription().getName() + " v" + this.getDescription().getVersion()); }

  @NotNull
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
