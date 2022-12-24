package coolguy1842.factions.SubTabCompleters.Factions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;

public class PlayerWithFactionTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        switch(args.length) {
        case 1:
            if(player.isLeader()) {
                out.add("disband");
                out.add("transfer");
            }
            else {
                out.add("leave");
            }

            if(player.hasPermission("invite")) out.add("invite");
            if(player.hasPermission("kick")) out.add("kick");

            if(player.hasPermission("rename")) out.add("rename");
    

            if(player.hasPermission("claim")) {
                out.add("claim");
                out.add("autoclaim");
            }
            
            if(player.hasPermission("unclaim")) out.add("unclaim");
            

            out.add("help");
            out.add("info");
            
            out.add("bal");
            out.add("balance");

            out.add("rank");

            if(player.hasPermission("viewvault") ||  player.hasPermission("createvault") || 
                player.hasPermission("renamevault") || player.hasPermission("removevault")) {    
                out.add("vault");
            }

            out.add("home");
            if(player.hasPermission("sethome")) out.add("sethome");
            if(player.hasPermission("delhome")) out.add("delhome");
            
            break;
        case 2:
            
            switch(args[0]) {
            case "invite":
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(FactionsManager.getInstance().playerManager.getPlayer(onlinePlayer).inFaction()) continue;
                    if(FactionsManager.getInstance().inviteManager.playerHasInvite(onlinePlayer, player.getFaction().getID())) continue;

                    out.add(onlinePlayer.getName());
                }

                break;
            case "transfer":
                if(!player.isLeader()) break;

                for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
                    if(factionPlayer.getID().equals(player.getID())) continue;

                    out.add(Bukkit.getOfflinePlayer(factionPlayer.getID()).getName());
                }

                break;
            case "kick":
                if(!player.hasPermission("kick")) break;

                for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
                    if(factionPlayer.getID().equals(player.getID())) continue;
                    else if(!player.isLeader() && factionPlayer.hasPermission("kick")) continue; 

                    out.add(Bukkit.getOfflinePlayer(factionPlayer.getID()).getName());
                }

                break;
            case "vault":
                if(player.hasPermission("viewvault")) out.add("open");
                if(player.hasPermission("createvault")) out.add("create");
                if(player.hasPermission("renamevault")) out.add("rename");
                if(player.hasPermission("removevault")) out.add("remove");

                break;
            case "home":
                for(FactionHome home : player.getFaction().homes.values()) {
                    out.add(home.getDisplayName());
                }
                
                break;
            case "sethome":
                if(!player.hasPermission("sethome")) break;

                for(FactionHome home : player.getFaction().homes.values()) {
                    out.add(home.getDisplayName());
                }
             
                break;
            case "delhome":
                if(!player.hasPermission("delhome")) break;

                for(FactionHome home : player.getFaction().homes.values()) {
                    out.add(home.getDisplayName());
                }    
                
                break;
            case "rank":
                out.add("info");
                if(!player.isLeader()) break;

                out.add("create");
                out.add("rename");
                out.add("remove");
                out.add("set");
                out.add("assign");
                out.add("unassign");

                break;
            default: break;
            }    

            break;
        case 3:
            switch(args[0]) {
            case "vault":
                switch(args[1]) {
                    case "open":
                        if(!player.hasPermission("viewvault")) break;
                        
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

                break;
            case "rank":
                if(!player.isLeader()) break;
                
                switch(args[1]) {
                case "set":
                    out.add("setpermission");
                    out.add("default");
                    break;
                case "assign":
                    for(FactionRank rank : player.getFaction().ranks.values()) {
                        out.add(rank.getDisplayName());
                    }
                    break;
                case "unassign":
                    for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
                        if(!factionPlayer.hasRank()) continue;

                        out.add(factionPlayer.getDisplayNameStr());
                    }
                    break;
                default: break;
                }
            default: break;
            }
        case 4:
            switch(args[0]) {
            case "rank":
                if(!player.isLeader()) break;

                switch(args[1]) {
                case "assign":
                    if(!player.getFaction().hasRank(args[2])) break;

                    for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
                        if(factionPlayer.hasRank()) {
                            if(factionPlayer.getRank().getDisplayName().equals(args[2])) continue;
                        }

                        out.add(factionPlayer.getDisplayNameStr());
                    }    

                    break;
                default: break;
                }
                break;
            default: break;
            }

            break;
        default: break;
        }

        return out;
    }
}
