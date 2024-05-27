package com.lauriethefish.betterportals.bukkit.chunk.chunkloading;

import com.lauriethefish.betterportals.bukkit.chunk.chunkpos.ChunkPosition;
import jakarta.inject.Singleton;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class ModernChunkLoader implements IChunkLoader {
    // Used to only unforceload chunks if they are *loaded by the plugin*
    private final Set<ChunkPosition> loadedChunks = new HashSet<>();

    @Override
    public void setForceLoaded(Chunk chunk) {
        chunk.setForceLoaded(true);
        loadedChunks.add(new ChunkPosition(chunk));
    }

    @Override
    public void setNotForceLoaded(@NotNull ChunkPosition chunk) {
        if(loadedChunks.remove(chunk)) {
            // Do it this way to avoid loading the chunk by calling getChunk
            chunk.getWorld().setChunkForceLoaded(chunk.x, chunk.z, false);
        }
    }

    @Override
    public boolean isForceLoaded(@NotNull ChunkPosition chunk) {
        return loadedChunks.contains(chunk);
    }   
}
