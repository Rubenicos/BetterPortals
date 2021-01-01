package com.lauriethefish.betterportals.bukkit.chunkloading;

import java.util.HashSet;
import java.util.Set;

import com.lauriethefish.betterportals.bukkit.BetterPortals;
import com.lauriethefish.betterportals.bukkit.multiblockchange.ChunkCoordIntPair;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class LegacyChunkLoader implements ChunkLoader, Listener {
    private Set<ChunkCoordIntPair> loadedChunks = new HashSet<>();

    public LegacyChunkLoader(BetterPortals pl) {
        // Register listeners
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        // Cancel ChunkUnloadEvent if the chunk is force loaded
        ChunkCoordIntPair chunkPos = new ChunkCoordIntPair(event.getChunk());
        if(loadedChunks.contains(chunkPos)) {
            event.setCancelled(true);
        }
    }

    @Override
    public void setForceLoaded(Chunk chunk) {
        loadedChunks.add(new ChunkCoordIntPair(chunk));
        chunk.load();
    }

    @Override
    public void setNotForceLoaded(ChunkCoordIntPair chunk) {
        loadedChunks.remove(chunk);
    }

    @Override
    public boolean isForceLoaded(ChunkCoordIntPair chunk) {
        return loadedChunks.contains(chunk);
    }
}
