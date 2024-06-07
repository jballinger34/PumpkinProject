package me.fakepumpkin7.pumpkinframework.factions;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Faction {

    @Getter
    private String name;
    @Getter
    private HashMap<UUID, FactionRank> membersAndRank = new HashMap<>();
    @Getter
    Faction ally;
    @Getter
    boolean isInviteOnly = true;
    List<UUID> currentlyInvited = new ArrayList<>();
    @Getter
    private List<FChunk> claims = new ArrayList<>();
    @Getter
    private List<FWarp> warps = new ArrayList<>();

    //use FactionHandler to create new factions
    //FactionHandler should probably re-create the factions on startup, and save them to config
    protected Faction(Player leader, String name){
        this.membersAndRank.put(leader.getUniqueId(), FactionRank.LEADER);
        this.name = name;
    }
    protected Faction(String name, HashMap<UUID,FactionRank> map){
        this.name = name;
        this.membersAndRank = map;
    }


    public void addMember(Player player){
        if(FactionHandler.getPlayersFaction(player.getUniqueId()) != null){
            ChatUtils.error(player,"You already have a Faction!");
        } else {
            membersAndRank.put(player.getUniqueId(), FactionRank.MEMBER);
            ChatUtils.success(player,"Successfully joined " + name);
        }
    }

    public void removeMember(Player player){
        if(membersAndRank.get(player.getUniqueId()) == null ){
            return;
        }
        if(membersAndRank.get(player.getUniqueId()) == FactionRank.LEADER ){
            ChatUtils.warn(player, "Promote someone else to leader before you leave!");
            return;
        }
        ChatUtils.notify(player, "Successfully left " + name);
        membersAndRank.remove(player.getUniqueId());
    }
    public void forceKickMember(UUID uuid){
        if(membersAndRank.get(uuid) == null ){
            return;
        }
        membersAndRank.remove(uuid);
    }
    public void demoteMember(UUID memberID){
        if(membersAndRank.get(memberID) == null) return;
        FactionRank current = membersAndRank.get(memberID);
        if(current == FactionRank.LEADER  || current == FactionRank.MEMBER) return;
        FactionRank newRank = FactionRank.values()[current.ordinal() - 1];
        membersAndRank.put(memberID, newRank);
    }
    public void promoteMember(UUID memberID){
        if(membersAndRank.get(memberID) == null) return;
        FactionRank current = membersAndRank.get(memberID);
        if(current == FactionRank.LEADER  || current == FactionRank.COLEADER) return;
        FactionRank newRank = FactionRank.values()[current.ordinal() + 1];
        membersAndRank.put(memberID, newRank);
    }
    public void swapLeaders(UUID currentCoID){
        if(membersAndRank.get(currentCoID) == null) return;
        if(membersAndRank.get(currentCoID) != FactionRank.COLEADER) return;

        UUID leaderUUID = getLeader();
        membersAndRank.put(leaderUUID, FactionRank.COLEADER);
        membersAndRank.put(currentCoID, FactionRank.LEADER);

    }

    //TODO TEST ALLY SHIT
    public void resetAlly(){
        Faction oldAlly = this.ally;
        this.ally = null;
        if(oldAlly != null && oldAlly.getAlly() != null){
            oldAlly.resetAlly();
        }
    }

    public void setAlly(Faction toAlly) {
        if(toAlly.equals(this.getAlly())){
            //facs are already allied
            return;
        }
        this.resetAlly();
        toAlly.resetAlly();

        this.ally = toAlly;
        toAlly.ally = this;
    }
    public void setInviteOnly(boolean inviteOnly){
        this.isInviteOnly = inviteOnly;
    }
    public void invitePlayer(Player player){
        currentlyInvited.add(player.getUniqueId());
        ChatUtils.notify(player, "You received an invite from "+ this.getName() + ", \"/f join "+ this.getName() +"\" to accept.");
        //remove invite after 30 seconds
        Bukkit.getScheduler().runTaskLater(PumpkinFramework.getInstance(),
                () -> {
                    if(currentlyInvited.contains(player.getUniqueId())){
                        deleteInvite(player);
                        ChatUtils.info(player, "Invite from " + this.getName() + " expired.");
                    }
                },30*20);
    }
    public void deleteInvite(Player player){
        currentlyInvited.remove(player.getUniqueId());
    }
    public boolean isInvited(Player player){
        UUID id = player.getUniqueId();
        for (UUID uuid : currentlyInvited){
            if(uuid.equals(id)){
                return true;
            }
        }
        return false;
    }
    public UUID getLeader(){
        for(UUID id : membersAndRank.keySet()){
            if(membersAndRank.get(id).equals(FactionRank.LEADER)){
                return id;
            }
        }
        return null;
    }
    public boolean isLeader(Player player){
        if(membersAndRank.get(player.getUniqueId()) == null) return false;
        if(membersAndRank.get(player.getUniqueId()).equals(FactionRank.LEADER)){
            return true;
        }
        return false;
    }
    public boolean isAtLeast(Player player, FactionRank factionRank){
        if(membersAndRank.get(player.getUniqueId()) == null) return false;
        if(membersAndRank.get(player.getUniqueId()).ordinal() >= factionRank.ordinal()){
            return true;
        }
        return false;
    }
    public List<Player> getOnlineMembers(){
        List<Player> list = new ArrayList<>();
        for(UUID id : membersAndRank.keySet()){
            if(Bukkit.getOfflinePlayer(id).isOnline()){
                list.add(Bukkit.getOfflinePlayer(id).getPlayer());
            }
        }

        return list;
    }
    public List<String> getOfflineMembersNames(){
        List<String> list = new ArrayList<>();
        for(UUID id : membersAndRank.keySet()){
            if(!Bukkit.getOfflinePlayer(id).isOnline()){
                list.add(Bukkit.getOfflinePlayer(id).getName());
            }
        }
        return list;
    }

    public void setHome(Location location){
        removeWarpByName("home");
        FWarp home = new FWarp("home",location, FactionRank.MEMBER);
        addWarp(home);
    }
    public void addWarp(FWarp warp){
        warps.add(warp);
    }
    public void removeWarp(FWarp warp){
        warps.remove(warp);
    }
    public FWarp getWarpByName(String warpName){
        for(FWarp warp : warps){
            if (warp.getName().equalsIgnoreCase(warpName)){
                return warp;
            }
        }
        return null;
    }
    public void removeWarpByName(String name){
        Collection<FWarp> toRemove = new ArrayList<>();

        for(FWarp warp : warps){
            if(warp.getName().equals(name)){
                toRemove.add(warp);
            }
        }
        warps.removeAll(toRemove);
    }
    public void removeWarpsInChunk(FChunk chunk){
        Collection<FWarp> toRemove = new ArrayList<>();
        for(FWarp warp : warps){
            FChunk warpChunk = new FChunk(warp.getLocation().getChunk());
            if(warpChunk.equals(chunk)){
                toRemove.add(warp);
            }
        }
        warps.removeAll(toRemove);
    }
}
