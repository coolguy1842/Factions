package coolguy1842.factions.SubTabCompleters.Factions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class PlayerWithFactionTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        switch(args.length) {
        case 1:
            out.add(player.isLeader() ? "disband" : "leave");
            if(player.hasPermission("invite")) out.add("invite");
            if(player.hasPermission("rename")) out.add("rename");
            

            out.add("bal");
            out.add("balance");

            break;
        case 2:
            if(args[0].equals("invite")) {
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(FactionsManager.getInstance().playerManager.getPlayer(onlinePlayer).inFaction()) continue;

                    out.add(onlinePlayer.getName());
                }

                break;
            }
            
            break;
        default: break;
        }

        return out;
    }
}
