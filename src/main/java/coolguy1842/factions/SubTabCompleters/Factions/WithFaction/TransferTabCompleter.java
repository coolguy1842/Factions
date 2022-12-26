package coolguy1842.factions.SubTabCompleters.Factions.WithFaction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;

public class TransferTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        if(!player.isLeader()) return out; 
        for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
            if(factionPlayer.isLeader()) continue;

            out.add(factionPlayer.getUsername());
        }

        return out;
    }
}
