package coolguy1842.factions.SubTabCompleters.Factions.WithFaction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;

public class RankTabCompleter {
    private static List<String> Len2Complete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();
        
        out.add("info");
        out.add("list");
        if(!player.isLeader()) return out;
        
        out.add("create");
        out.add("rename");
        out.add("remove");
        out.add("set");
        out.add("get");
        out.add("assign");
        out.add("unassign");

        return out;
    }

    private static List<String> Len3Complete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();
        if(!player.isLeader()) return out;
        
        switch(args[1]) {
        case "set": case "get":
            out.add("permission");
            out.add("default");
            break;
        case "assign": case "remove": case "rename":
            for(FactionRank rank : player.getFaction().ranks.values()) {
                out.add(rank.getDisplayName());
            }
            break;
        case "unassign":
            for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
                if(!factionPlayer.hasRank()) continue;

                out.add(factionPlayer.getUsername());
            }
            break;
        default: break;
        }

        return out;
    }

    private static List<String> Len4Complete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();
        if(!player.isLeader()) return out;    
        
        switch(args[1]) {
        case "set":
            switch(args[2]) {
            case "default":
                for(FactionRank rank : player.getFaction().ranks.values()) {
                    if(rank.getIsDefault()) continue;

                    out.add(rank.getDisplayName());
                }    

                break;
            case "permission":
                for(FactionRank rank : player.getFaction().ranks.values()) {
                    out.add(rank.getDisplayName());
                }
                
                break;
            default: break;
            }

            break;
        case "get":
            switch(args[2]) {
            case "default":
                for(FactionRank rank : player.getFaction().ranks.values()) {
                    out.add(rank.getDisplayName());
                }    

                break;
            case "permission":
                for(FactionRank rank : player.getFaction().ranks.values()) {
                    out.add(rank.getDisplayName());
                }
                
                break;
            default: break;
            }

            break;
        case "assign":
            for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
                if(factionPlayer.hasRank()) {
                    if(factionPlayer.getRank().getDisplayName().equals(args[2])) continue;
                }
                out.add(factionPlayer.getUsername());
            }    

            break;
        default: break;
        }

        return out;
    }

    private static List<String> Len5Complete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();
        if(!player.isLeader()) return out;    
        
        switch(args[1]) {
        case "set":
            switch(args[2]) {
            case "permission":
                for(String permission : Globals.rankPermissions) {
                    out.add(permission);
                }
                
                break;
            default: break;
            }

            break;
        case "get":
            switch(args[2]) {
            case "permission":
                for(String permission : Globals.rankPermissions) {
                    out.add(permission);
                }
                
                break;
            default: break;
            }

        default: break;
        }

        return out;
    }

    private static List<String> Len6Complete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();
        if(!player.isLeader()) return out;    
        
        switch(args[1]) {
        case "set":
            switch(args[2]) {
            case "permission":
                if(!Globals.rankPermissions.contains(args[4])) return out;
                
                out.add("true");
                out.add("false");
                
                break;
            default: break;
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
        case 4: return Len4Complete(p, player, command, label, args);
        case 5: return Len5Complete(p, player, command, label, args);
        case 6: return Len6Complete(p, player, command, label, args);
        default: return new ArrayList<String>();
        }
    }
}
