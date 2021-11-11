package com.github.huda0209.lobbypluginadevdojo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

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
            if(!event.getPlayer().getWorld().getName().equalsIgnoreCase("lobby")) return;
            if(event.getPlayer().hasPermission("LobbyPluginADevDojo.breakBlock")) return;
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou don't permit to break block in this world!"));
            event.setCancelled(true);
        }
    }

    private class EntityDamageEventListener implements Listener {
        @EventHandler
        public void EntityDamageEvent(EntityDamageEvent event){
            if(!(event.getEntity() instanceof Player)) return;
            if(!event.getEntity().getWorld().getName().equalsIgnoreCase("lobby")) return;
            event.setCancelled(true);
        }
    }

    private class PlayerJoinEventListener implements Listener {
        @EventHandler
        public void PlayerJoinEvent(PlayerJoinEvent event){
            if(!ExistWorld("lobby",event.getPlayer().getServer().getWorlds())){
                getLogger().warning("Cannot find the world \"lobby\". Please a world named \"lobby\".");
                return;
            }

            Location joinLocate = new Location(event.getPlayer().getServer().getWorld("lobby"),0.5,64,0.5,0,0);
            event.getPlayer().teleport(joinLocate);
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&',event.getPlayer().getDisplayName()+" joined Lobby Server."));
        }
    }

    public boolean ExistWorld(String worldName, List<World> worlds){
        for(World world : worlds){
            if(world.getName().equalsIgnoreCase(worldName)) return true;
        }
        return false;
    }
}


