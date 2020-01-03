package dev.thinkverse.troll.command;

import dev.thinkverse.troll.Troll;
import dev.thinkverse.troll.command.trolls.BlindCommand;
import dev.thinkverse.troll.command.trolls.FlingCommand;
import dev.thinkverse.troll.command.trolls.SlapCommand;
import dev.thinkverse.troll.utils.Logger;
import dev.thinkverse.troll.utils.Util;
import dev.thinkverse.troll.utils.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrollCommand implements CommandExecutor, TabCompleter {
  private Plugin plugin = Troll.getPlugin(Troll.class);

  private ArrayList<SubCommand> trolls = new ArrayList<>();

  private final String[] COMMANDS = {"slap", "fling", "blind"};

  public TrollCommand() {
    trolls.add(new SlapCommand());
    trolls.add(new BlindCommand());
    trolls.add(new FlingCommand());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!(sender instanceof Player) || !sender.hasPermission("troll.use")) {
      sender.sendMessage(Util.Chat(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("no-permission")));
      return true;
    } else {
      final Player player = (Player) sender;

      if (args.length == 0) {
        player.sendMessage(command.getUsage());
      } else {
        boolean isValid = false;

        for (SubCommand troll : getTrolls()) {
          if (args[0].equalsIgnoreCase(troll.getName())) {
            isValid = true;

            try {
              troll.onCommand(player, args);
            } catch (Exception exception) {
              Logger.log(Logger.LogLevel.ERROR, exception.getMessage());
            }

            break;
          }
        }

        if (!isValid) {
          player.sendMessage(Util.Chat("&cThis is not a valid command."));
          player.sendMessage(Util.Chat("&7Do &e/help troll &7for more info."));
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
