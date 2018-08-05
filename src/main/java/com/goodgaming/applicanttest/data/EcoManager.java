package com.goodgaming.applicanttest.data;

import com.goodgaming.applicanttest.ApplicantTest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by patrickzhong on 8/5/18.
 */
public class EcoManager {

    @Getter private static EcoManager instance;

    private HashMap<UUID, Integer> writeCache = new HashMap<>();
    @Getter private HashMap<UUID, Integer> readCache = new HashMap<>();

    public EcoManager(){

        instance = this;

        int period = 20 * 60 * 5;

        new BukkitRunnable(){
            @Override
            public void run() {
                saveAll();
            }
        }.runTaskTimerAsynchronously(ApplicantTest.getInstance(), period, period);

    }

    public void saveAll(){
        for (UUID uuid : writeCache.keySet())
            save(uuid, false);
        writeCache.clear();
    }

    public void save(UUID uuid, boolean remove){

        ApplicantTest.log("Saving " + uuid + " to database...");

        Integer bal = remove ? writeCache.remove(uuid) : writeCache.get(uuid);
        if(bal != null){
            if(DataManager.getInstance().exists(uuid))
                DataManager.getInstance().setBal(uuid, bal);
            else
                DataManager.getInstance().insert(uuid, bal);
        }
    }

    public void addBal(Player player, int amount){
        int updated = getBal(player) + amount;

        ApplicantTest.log("Updating " + player.getUniqueId() + " cache value to " + updated + ".");

        writeCache.put(player.getUniqueId(), updated);
        readCache.put(player.getUniqueId(), updated);
    }

    public int getBal(Player player){

        Integer bal = readCache.get(player.getUniqueId());
        if(bal != null) return bal;

        bal = DataManager.getInstance().getBal(player.getUniqueId());
        readCache.put(player.getUniqueId(), bal);
        return bal;

    }

}
