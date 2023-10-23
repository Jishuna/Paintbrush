package me.jishuna.paintbrush;

import java.io.File;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.SimpleSemVersion;
import me.jishuna.jishlib.UpdateChecker;
import me.jishuna.jishlib.config.ConfigReloadable;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.util.ServerUtils;
import me.jishuna.paintbrush.command.PaintbrushCommandHandler;
import me.jishuna.paintbrush.listener.InventoryListener;
import me.jishuna.paintbrush.listener.PaintbrushListener;
import me.jishuna.paintbrush.recipe.RecipeAdapter;
import me.jishuna.paintbrush.recipe.RecipeData;
import net.md_5.bungee.api.ChatColor;

public class Paintbrush extends JavaPlugin {
    private static final int BSTATS_ID = 20122;
    private static final int PLUGIN_ID = -1;

    private ConfigReloadable<Settings> settings;

    @Override
    public void onEnable() {
        initializeConfigs();

        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PaintbrushListener(), this);

        getCommand("paintbrush").setExecutor(new PaintbrushCommandHandler(this));

        initializeMetrics();
        initializeUpdateChecker();
    }

    private void initializeConfigs() {
        ConfigurationManager manager = new ConfigurationManager(this);
        manager.registerTypeAdapter(RecipeData.class, new RecipeAdapter());
        MessageHandler.initalize(manager, new File(getDataFolder(), "messages.yml"), getResource("messages.yml"));
        settings = manager.createStaticReloadable(new File(getDataFolder(), "config.yml"), Settings.class).saveDefaults().load();
    }

    public void reloadConfigs() {
        MessageHandler.getInstance().reload();
        settings.load();
    }

    private void initializeMetrics() {
        if (!Settings.bStats) {
            return;
        }

        Metrics metrics = new Metrics(this, BSTATS_ID);
        metrics.addCustomChart(new SimplePie("online_status", ServerUtils::getOnlineMode));
    }

    private void initializeUpdateChecker() {
        if (!Settings.updateChecker) {
            return;
        }

        UpdateChecker checker = new UpdateChecker(this, PLUGIN_ID);
        SimpleSemVersion current = SimpleSemVersion.fromString(this.getDescription().getVersion());

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> checker.getVersion(version -> {
            if (SimpleSemVersion.fromString(version).isNewerThan(current)) {
                ConsoleCommandSender sender = Bukkit.getConsoleSender();
                sender.sendMessage(ChatColor.GOLD + "=".repeat(70));
                sender.sendMessage(ChatColor.GOLD + "A new version of Paintbrush is available: " + ChatColor.DARK_AQUA + version);
                sender.sendMessage(ChatColor.GOLD + "Download it at <url>");
                sender.sendMessage(ChatColor.GOLD + "=".repeat(70));
            }
        }), 0, 20l * 60 * 60);
    }
}
