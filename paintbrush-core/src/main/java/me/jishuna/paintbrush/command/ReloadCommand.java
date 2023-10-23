package me.jishuna.paintbrush.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.commands.SimpleCommandHandler;
import me.jishuna.paintbrush.Paintbrush;

public class ReloadCommand extends SimpleCommandHandler {
    private final Paintbrush plugin;

    protected ReloadCommand(Paintbrush plugin) {
        super("paintbrush.command.reload");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfigs();
        sender.sendMessage(MessageHandler.get("command.reload.complete"));
        return true;
    }
}