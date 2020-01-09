package dev.thinkverse.troll;

import dev.thinkverse.troll.utils.plugin.SemanticVersion;
import dev.thinkverse.troll.utils.plugin.UpdateChecker;
import dev.thinkverse.troll.utils.config.DefaultConfig;
import dev.thinkverse.troll.utils.metrics.MetricsLite;
import dev.thinkverse.troll.commands.TrollCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TrollPlugin extends JavaPlugin {
  private MetricsLite metrics;
  private SemanticVersion semanticVersion;
  private DefaultConfig defaultConfig;
  private String version;

  @Override
  public void onEnable() {
    this.setVariables();

    try {
      this.setSemanticVersion(this.getDescription().getVersion());
      this.checkUpdates();
    } catch (ParseException exception) {
      this.getLogger().log(Level.INFO, "Issue parsing plugin version: " + exception.getMessage());
    }

    this.loadConfig();

    this.setMetrics();

    this.registerCommand("troll", new TrollCommand(this), true);
  }

  private void setSemanticVersion(@NotNull String version) throws ParseException { this.semanticVersion = new SemanticVersion(version); }

  public SemanticVersion getSemanticVersion() { return semanticVersion; }

  private void setMetrics() { this.metrics = new MetricsLite(this); }

  public String getServerVersion() { return version; }

  private void setVariables() {
    this.version = getMinecraftVersion();
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
    new UpdateChecker(this, 74111).getVersion(remote -> {
      if (remote.isUpdateFor(getSemanticVersion())) {
        this.getLogger().log(Level.INFO, String.format("New version available: %s", remote.toString()));
      } else {
        this.getLogger().log(Level.INFO, "No new version available.");
      }
    });
  }

  protected final String getMinecraftVersion() {
    Matcher matcher = Pattern.compile("(\\(MC: )([\\d\\.]+)(\\))").matcher(Bukkit.getVersion());
    if (matcher.find()) { return matcher.group(2); }
    return null;
  }

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
