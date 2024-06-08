package me.fakepumpkin7.pumpkincore.scoreboard;

import me.fakepumpkin7.pumpkincore.PumpkinCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Board implements Runnable{

    private final static Board instance = new Board();

    private Board(){

    }

    @Override
    public void run(){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getScoreboard() != null && player.getScoreboard().getObjective(PumpkinCore.getInstance().getName()) != null){
                updateScoreboard(player);
            } else {
                createNewScoreboard(player);
            }
        }


    }

    private void createNewScoreboard(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(PumpkinCore.getInstance().getName(), "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("SCOREBOARD1");

        objective.getScore("").setScore(4);
        objective.getScore("1").setScore(3);
        objective.getScore("2").setScore(2);
        objective.getScore("3").setScore(1);
        //objective.getScore("4").setScore(0);

        Team team1 = scoreboard.registerNewTeam("team1");
        String teamKey = ChatColor.GOLD.toString();
        team1.addEntry(teamKey);
        team1.setPrefix("Walked: ");
        team1.setSuffix("0cm");

        objective.getScore(teamKey).setScore(0);

        player.setScoreboard(scoreboard);
    }
    private void updateScoreboard(Player player){
        Scoreboard scoreboard = player.getScoreboard();

        Team team1 = scoreboard.getTeam("team1");
        team1.setSuffix("" + player.getStatistic(Statistic.WALK_ONE_CM) + "cm");
    }


    public static Board getInstance(){
        return instance;
    }


}
