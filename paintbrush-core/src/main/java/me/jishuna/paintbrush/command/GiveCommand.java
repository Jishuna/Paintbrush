package me.jishuna.paintbrush.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.commands.SimpleCommandHandler;
import me.jishuna.paintbrush.Settings;

public class GiveCommand extends SimpleCommandHandler {

    protected GiveCommand() {
        super("paintbrush.command.give");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (args.length >= 1) {
            player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(MessageHandler.get("command.invalid-player", args[0]));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MessageHandler.get("command.requires-player"));
                return true;
            }
            player = (Player) sender;
        }

        player.getInventory().addItem(Settings.getBrush());
        sender.sendMessage(MessageHandler.get("command.give.success", player.getName()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Bukkit.getOnlinePlayers().stream().map(Player::getName).toList(), new ArrayList<>());
        }
        return Collections.emptyList();
    }
}