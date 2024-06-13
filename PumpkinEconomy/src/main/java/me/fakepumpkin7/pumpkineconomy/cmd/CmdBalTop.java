package me.fakepumpkin7.pumpkineconomy.cmd;

import me.fakepumpkin7.pumpkineconomy.PumpkinEconomy;
import me.fakepumpkin7.pumpkineconomy.config.EconomyConfigHandler;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CmdBalTop implements CommandExecutor {

    protected HashMap<String, Double> uuidToBalSorted = new HashMap<>();

    public CmdBalTop(){

        BalTopTask task = new BalTopTask(this);
        Bukkit.getScheduler().runTaskTimer(PumpkinEconomy.getInstance(), task, 0, 15*60*20);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;

        ChatUtils.sendDivider(player, ChatColor.GREEN.toString());
        player.sendMessage("Balance Top");

        int i = 1;
        for(String id : uuidToBalSorted.keySet()){

            String name = Bukkit.getOfflinePlayer(UUID.fromString(id)).getName();
            double balance = uuidToBalSorted.get(id);

            player.sendMessage(i + ") " + name + ": " + balance);
            if(i == 10){
                break;
            }
            i++;

        }
        ChatUtils.sendDivider(player, ChatColor.GREEN.toString());

        return true;
    }
}

class BalTopTask implements Runnable{

    CmdBalTop cmd;

    BalTopTask(CmdBalTop cmd){
        this.cmd = cmd;
    }
    @Override
    public void run() {
        System.out.println("Updating Baltop");
        HashMap<String, Double> unsorted = EconomyConfigHandler.getAllBalancesFromConfig();
        if(unsorted == null){
            return;
        }
        cmd.uuidToBalSorted = sortHashMap(unsorted);
    }

    public HashMap<String,Double> sortHashMap(HashMap<String,Double> hashMap) {
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<>();
        list.addAll(hashMap.entrySet());
        Collections.sort( list , new balTopComparator() );
        LinkedHashMap<String,Double> map = new LinkedHashMap<>();
        for (int i = 0; i < list.size();i++){
            map.put(list.get(i).getKey(), list.get(i).getValue() );
        }
        return map;
    }


}
class balTopComparator implements Comparator<Map.Entry<String,Double> > {

    @Override
    public int compare( Map.Entry<String,Double> entry1,  Map.Entry<String,Double> entry2)
    {
        Double bal1 = entry1.getValue();
        Double bal2 = entry2.getValue();

        if(bal1 > bal2){
            return -1;
        } else if (bal1 < bal2){
            return 1;
        } else{
            return 0;
        }

    }

}
