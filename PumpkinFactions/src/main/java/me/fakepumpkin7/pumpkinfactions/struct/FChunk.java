package me.fakepumpkin7.pumpkinfactions.struct;

import org.bukkit.Chunk;

public class FChunk {

    private String worldName;

    private int x;

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

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof FChunk)) return false;
        FChunk chunk = (FChunk) object;
        return this.x == chunk.x && this.y == chunk.y && this.worldName.equals(chunk.worldName);
    }

    public String getWorldName() {
        return worldName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
