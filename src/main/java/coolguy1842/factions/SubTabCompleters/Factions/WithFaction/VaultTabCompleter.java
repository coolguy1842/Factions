package coolguy1842.factions.SubTabCompleters.Factions.WithFaction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;

public class VaultTabCompleter {
    private static List<String> Len2Complete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        if(player.hasPermission("openvault")) out.add("open");
        if(player.hasPermission("createvault")) out.add("create");
        if(player.hasPermission("renamevault")) out.add("rename");
        if(player.hasPermission("removevault")) out.add("remove");
        
        return out;
    }

    private static List<String> Len3Complete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();
        
        switch(args[1]) {
        case "open":
            if(!player.hasPermission("openvault")) break;
            
            for(String displayName : player.getFaction().vaultsByName.keySet()) {
                out.add(displayName);
            }   

            break;
        case "rename":
            if(!player.hasPermission("renamevault")) break;
            
            for(String displayName : player.getFaction().vaultsByName.keySet()) {
                out.add(displayName);
            }   

            break;
        case "remove":
            if(!player.hasPermission("removevault")) break;
            
            for(String displayName : player.getFaction().vaultsByName.keySet()) {
                out.add(displayName);
            }   

            break;
        default: break;
        }

        return out;
    }

    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        switch(args.length) {
        case 2: return Len2Complete(p, player, command, label, args);
        case 3: return Len3Complete(p, player, command, label, args);
        default: return new ArrayList<String>();
        }
    }
}
