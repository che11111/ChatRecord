package com.che1sBukkit.chatrecord;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        JDBCUtil.insertChat(e.getPlayer().getName(), e.getMessage());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPrivateMessage(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        for (String prefix : Main.getMsgCommands()) {
            if (cmd.startsWith("/" + prefix + " ")) {
                Bukkit.getScheduler().runTaskAsynchronously(
                        Main.getInstance(),
                        () -> JDBCUtil.insertChat(e.getPlayer().getName(), e.getMessage())
                );
                return;
            }
        }
    }
}
