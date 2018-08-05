package com.goodgaming.applicanttest.listener;

import com.goodgaming.applicanttest.ApplicantTest;
import com.goodgaming.applicanttest.data.EcoManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by patrickzhong on 8/5/18.
 */
public class EventListener implements Listener {


    @EventHandler
    public void onQuit(PlayerQuitEvent ev){
        Bukkit.getScheduler().runTaskAsynchronously(ApplicantTest.getInstance(), () -> {
            EcoManager.getInstance().save(ev.getPlayer().getUniqueId(), true);
            EcoManager.getInstance().getReadCache().remove(ev.getPlayer().getUniqueId());
        });
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onSpawn(CreatureSpawnEvent ev){
        if(ev.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM && ev.getEntity() instanceof Monster) {
            ApplicantTest.log("Prevented hostile mob " + ev.getEntityType() + " from spawning.");
            ev.setCancelled(true);
        }
    }


}
