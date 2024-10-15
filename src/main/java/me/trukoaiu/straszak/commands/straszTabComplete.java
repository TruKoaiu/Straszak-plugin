package me.trukoaiu.straszak.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class straszTabComplete implements TabCompleter {
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i ++) {
                playerNames.add(players[i].getName());
            }
            return playerNames;
        } else if (strings.length == 2) {
            List<String> completions = new ArrayList<>();
            String[] strings1 = new strasz().strings1;
            for (int i = 0; i < strings1.length; i++) {
                completions.add(strings1[i]);
            }
            return completions;
        }

        return null;
    }
}
