package me.fakepumpkin7.pumpkincore.patches;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisableNaturalMobSpawn implements Listener {

    @EventHandler
    public void onNaturalSpawn(CreatureSpawnEvent event){
        SpawnReason reason = event.getSpawnReason();
        if(enabledReasons.contains(reason)) return;
        event.setCancelled(true);
    }

    private List<SpawnReason> enabledReasons = Arrays.asList(
            SpawnReason.CHUNK_GEN,
            SpawnReason.SPAWNER,
            SpawnReason.EGG,
            SpawnReason.SPAWNER_EGG,
            SpawnReason.LIGHTNING,
            SpawnReason.BREEDING,
            SpawnReason.SLIME_SPLIT,
            SpawnReason.CUSTOM,
            SpawnReason.DEFAULT
    );




}
