package com.github.huda0209.lobbypluginadevdojo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyPluginADevDojo extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockBreakEventListener(),this);
        getServer().getPluginManager().registerEvents(new EntityDamageEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(),this);

        String[] EnableMessage = {"=============================","Plugin Name : "+this.getDescription().getName() ,"Author : "+ this.getDescription().getAuthors(),"============================="};
        for (String s : EnableMessage) {
            getLogger().info(s);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info(this.getDescription().getName()+" was disable.");
    }

    private class BlockBreakEventListener implements Listener {
        @EventHandler
        public void BlockBreakEvent(BlockBreakEvent event){
            if(event.getPlayer().hasPermission("LobbyPluginADevDojo.breakBlock")) return;
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou don't permit to break block in this server!"));
            event.setCancelled(true);
        }
    }

    private class EntityDamageEventListener implements Listener {
        @EventHandler
        public void EntityDamageEvent(EntityDamageEvent event){
            if(!(event.getEntity() instanceof Player)) return;
            event.setCancelled(true);
        }
    }

    private class PlayerJoinEventListener implements Listener {
        @EventHandler
        public void PlayerJoinEvent(PlayerJoinEvent event){
            Location joinLocate = new Location(event.getPlayer().getWorld(),0.5,64,0.5,0,0);
            event.getPlayer().teleport(joinLocate);
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&',event.getPlayer().getDisplayName()+" joined Lobby server."));
        }
    }
}
