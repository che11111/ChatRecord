package com.che1sBukkit.chatrecord;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Main extends JavaPlugin {
    private static Main instance;
    private static List<String> msgCommands;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig();
        msgCommands = getConfig().getStringList("msg-command-list");
        JDBCUtil.init();
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getLogger().info("Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        JDBCUtil.disconnect();
        getLogger().info("Done.");
    }

    public static List<String> getMsgCommands() {
        return msgCommands;
    }

    public static Main getInstance() {
        return instance;
    }
}
