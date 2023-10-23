package me.jishuna.paintbrush.command;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.commands.ArgumentCommandHandler;
import me.jishuna.paintbrush.Paintbrush;

public class PaintbrushCommandHandler extends ArgumentCommandHandler {

    public PaintbrushCommandHandler(Paintbrush plugin) {
        super("paintbrush.command", () -> MessageHandler.get("command.no-permission"), () -> MessageHandler.get("command.usage"));
        addArguments(plugin);
    }

    private void addArguments(Paintbrush plugin) {
        addArgumentExecutor("reload", new ReloadCommand(plugin));
        addArgumentExecutor("give", new GiveCommand());
    }
}
