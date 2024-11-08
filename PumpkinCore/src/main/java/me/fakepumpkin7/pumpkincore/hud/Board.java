package me.fakepumpkin7.pumpkincore.hud;

import me.fakepumpkin7.pumpkincore.PumpkinCore;
import me.fakepumpkin7.pumpkinframework.economy.EconomyManager;
import me.fakepumpkin7.pumpkinframework.factions.FactionAPI;
import me.fakepumpkin7.pumpkinframework.factions.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;


public class Board implements Runnable {

    private static int count = 0;

    FactionAPI factionAPI = FactionManager.getFactionAPI();

    // "PRIORITY" refers to how high up the scoreboard the item will be placed
    //no two lines can be the same, so for empty lines we have priority no. of spaces
    //priority = 4, message = "    "

    private final String balanceTeamKey = ChatColor.GOLD.toString();


    @Override
    public void run(){
        //this set up with count allows us to fully
        //reset the scoreboard every 5 runs,
        //smaller details are updated every time

        //UPDATING LINES WILL UPDATE EVERY SINGLE RUN
        //STATIC LINES WILL ONLY BE UPDATED EVERY 5 RUNS

        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getScoreboard() != null && player.getScoreboard().getObjective(PumpkinCore.getInstance().getName()) != null){
                if(count % 5 == 0){
                    count = 0;
                    createNewScoreboard(player);
                } else {
                    updateScoreboard(player);
                }
            } else {
                createNewScoreboard(player);
            }
        }
        count++;
    }

    private void createNewScoreboard(Player player){
        ArrayList<Line> lines = new ArrayList<>();

        //get a new scoreboard and objective object.
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(PumpkinCore.getInstance().getName(), "dummy");
        //move to the right, configure the heading
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(PumpkinCore.serverName);

        createEmptyLine(lines, 10);
        //display balance
        String balancePrefix = "Balance: ";
        String balSuffix = getBalanceStr(player);
        createUpdatingLine(lines, 9 ,balanceTeamKey, balancePrefix, balSuffix);

        //line break
        createEmptyLine(lines, 8);

        //display faction name and then list of members
        String facName = factionAPI.getFactionName(player.getUniqueId());


        if(facName.equalsIgnoreCase("Wilderness")){
            //no faction
        } else {
            String facNameString = "Faction: " + facName;
            createStaticLine(lines, 7, facNameString);




            String onlineMembersString = "Online Members: (" + getOnlineMembersCount(player) + "/" + getTotalMembersCount(player) +")";

            createEmptyLine(lines, 6);

            createStaticLine(lines, 5 ,onlineMembersString);


            UUID playerUUID = player.getUniqueId();


            HashMap<UUID,String> factionMembersAndPrefix = factionAPI.getMembersAndRankPrefix(playerUUID);

            String playerString = factionMembersAndPrefix.get(playerUUID) + player.getName();
            createStaticLine(lines, 4 ,playerString);

            for(UUID uuid : factionMembersAndPrefix.keySet()) {
                if (uuid.equals(playerUUID)) continue;
                if (!Bukkit.getOfflinePlayer(uuid).isOnline()) continue;
                createStaticLine(lines, 3, factionMembersAndPrefix.get(uuid) + Bukkit.getOfflinePlayer(uuid).getName());
            }
        }


        //addAllLines adds the lines to the board in the correct order
        addAllLines(scoreboard,objective,lines);
        player.setScoreboard(scoreboard);
    }
    private void updateScoreboard(Player player){
        Scoreboard scoreboard = player.getScoreboard();

        Set<Team> teams = scoreboard.getTeams();
        for(Team team : teams){
            if(team.getName().equalsIgnoreCase(balanceTeamKey)){
                Double balance = EconomyManager.getEconomyAPI().getBalance(player.getUniqueId());
                String balSuffix = EconomyManager.getEconomyAPI().getCurrencyString() + (Math.floor(balance * 100) / 100);
                team.setSuffix(balSuffix);
            }
        }





    }

    private void addAllLines(Scoreboard scoreboard, Objective objective, ArrayList<Line> lines){
        for(Line line : lines) {
            if (line.isUpdating()) {
                String teamKey = line.getTeamKey();

                Team t = scoreboard.registerNewTeam(teamKey);
                t.addEntry(teamKey);

                t.setPrefix(line.getPrefix());
                t.setSuffix(line.getSuffix());

                objective.getScore(teamKey).setScore(line.getPriority());
            } else {
                objective.getScore(line.getMessage()).setScore(line.getPriority());
            }
        }
    }
    private void createEmptyLine(ArrayList<Line> lines, int priority){
        createLine(lines,new Line(priority," ".repeat(priority)));
    }
    private void createStaticLine(ArrayList<Line> lines, int priority, String message){
        createLine(lines, new Line(priority,message));
    }
    private void createUpdatingLine(ArrayList<Line> lines, int priority, String teamKey, String prefix, String suffix){
        createLine(lines, new Line(priority,teamKey,prefix,suffix));
    }
    private void createLine(ArrayList<Line> lines, Line line){
        lines.add(line);
    }

    private String getBalanceStr(Player player){
        Double balance = EconomyManager.getEconomyAPI().getBalance(player.getUniqueId());
        return EconomyManager.getEconomyAPI().getCurrencyString() + (Math.floor(balance * 100) / 100);
    }
    private String getOnlineMembersCount(Player player){
        HashMap<UUID,String> allMembers = factionAPI.getMembersAndRankPrefix(player.getUniqueId());
        int count = 0;
        for(UUID id : allMembers.keySet()){
            if(Bukkit.getOfflinePlayer(id).isOnline()){
                count ++;
            }
        }
        return String.valueOf(count);

    }
    private String getTotalMembersCount(Player player){
        return String.valueOf(factionAPI.getMembersAndRankPrefix(player.getUniqueId()).size());



    }
}
