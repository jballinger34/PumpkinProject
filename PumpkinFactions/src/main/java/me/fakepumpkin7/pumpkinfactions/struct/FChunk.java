package me.fakepumpkin7.pumpkinfactions.struct;

import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.event.Cancellable;

public class FChunk {

    @Getter
    private String worldName;
    @Getter
    private int x;

    @Getter
    private int y;

    public FChunk(Chunk chunk){
        this.worldName = chunk.getWorld().getName();
        this.x = chunk.getX();
        this.y = chunk.getZ();
    }
    public FChunk(String worldName, int x, int y){
        this.worldName = worldName;
        this.x = x;
        this.y = y;
    }

    public boolean equals(FChunk chunk) {
        return this.x == chunk.x && this.y == chunk.y && this.worldName.equals(chunk.worldName);
    }
}

