package dev.thinkverse.troll.commands;

import dev.thinkverse.troll.TrollPlugin;
import dev.thinkverse.troll.commands.admin.ReloadCommand;
import dev.thinkverse.troll.commands.admin.VersionCommand;
import dev.thinkverse.troll.commands.trolls.*;
import dev.thinkverse.troll.utils.Chat;
import dev.thinkverse.troll.commands.abstraction.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class TrollCommand implements CommandExecutor, TabCompleter {
  private TrollPlugin plugin;

  private ArrayList<SubCommand> trolls = new ArrayList<>();

  private final String[] COMMANDS = {"slap", "fling", "blind", "speak", "smite", "reload", "version", "creeper", "zombie", "skeleton", "tnt", "op", "deop"};

  public TrollCommand(TrollPlugin plugin) {
    this.plugin = plugin;
    addSubCommands();
  }

  private void addSubCommands() {
    trolls.add(new TntCommand());
    trolls.add(new SlapCommand());
    trolls.add(new BlindCommand());
    trolls.add(new FlingCommand());
    trolls.add(new SpeakCommand());
    trolls.add(new SmiteCommand());
    trolls.add(new ReloadCommand());
    trolls.add(new ZombieCommand());
    trolls.add(new FakeOpCommand());
    trolls.add(new VersionCommand());
    trolls.add(new CreeperCommand());
    trolls.add(new SkeletonCommand());
    trolls.add(new FakeDeopCommand());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!(sender instanceof Player) || !sender.hasPermission("troll.use")) {
      Chat.message(sender, plugin.getConfig().getString("prefix") + plugin.getConfig().getString("no-permission"));
      return true;
    } else {
      final Player player = (Player) sender;

      if (args.length == 0) {
        Chat.message(player, command.getUsage());
      } else {
        boolean isValid = false;

        for (SubCommand troll : getTrolls()) {
          if (args[0].equalsIgnoreCase(troll.getName())) {
            isValid = true;

            try {
              troll.onCommand(this.plugin, player, args);
            } catch (Exception exception) {
              plugin.getLogger().log(Level.SEVERE, exception.getMessage());
            }

            break;
          }
        }

        if (!isValid) {
          Chat.message(player, "&cThis is not a valid command.");
          Chat.message(player,"&7Do &e/help troll &7for more info.");
        }

        return true;
      }
    }

    return true;
  }

  private ArrayList<SubCommand> getTrolls() {
    return trolls;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    final List<String> completions = new ArrayList<String>();

    if (sender.hasPermission("troll.use")) {
      if (args.length == 1) {
        StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
      } else if (args.length == 2) {
        for (Player player : Bukkit.getOnlinePlayers()) {
          if (StringUtil.startsWithIgnoreCase(player.getName(), args[1])) {
            completions.add(player.getName());
          }
        }
      }
    }

    Collections.sort(completions);

    return completions;
  }
}
