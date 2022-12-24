package coolguy1842.factions.SubTabCompleters.Factions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Classes.FactionPlayer;
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

            if(player.hasPermission("viewvault") ||  player.hasPermission("createvault") || 
                player.hasPermission("renamevault") || player.hasPermission("removevault")) {    
                out.add("vault");
            }

            out.add("home");
            if(player.hasPermission("sethome")) out.add("sethome");
            if(player.hasPermission("delhome")) out.add("delhome");
            
            break;
        case 2:
            if(args[0].equals("invite")) {
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(FactionsManager.getInstance().playerManager.getPlayer(onlinePlayer).inFaction()) continue;
                    if(FactionsManager.getInstance().inviteManager.playerHasInvite(onlinePlayer, player.getFaction().getID())) continue;

                    out.add(onlinePlayer.getName());
                }

                break;
            }
            else if(args[0].equals("transfer")) {
                if(!player.isLeader()) break;

                for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
                    if(factionPlayer.getID().equals(player.getID())) continue;

                    out.add(Bukkit.getOfflinePlayer(factionPlayer.getID()).getName());
                }

                break;
            }
            else if(args[0].equals("kick")) {
                if(!player.hasPermission("kick")) break;

                for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
                    if(factionPlayer.getID().equals(player.getID())) continue;
                    else if(!player.isLeader() && factionPlayer.hasPermission("kick")) continue; 

                    out.add(Bukkit.getOfflinePlayer(factionPlayer.getID()).getName());
                }

                break;
            }
            else if(args[0].equals("vault")) {
                if(player.hasPermission("viewvault")) out.add("open");
                if(player.hasPermission("createvault")) out.add("create");
                if(player.hasPermission("renamevault")) out.add("rename");
                if(player.hasPermission("removevault")) out.add("remove");
            }
            else if(args[0].equals("home")) {
                for(FactionHome home : player.getFaction().homes.values()) {
                    out.add(home.getDisplayName());
                }
            }
            else if(args[0].equals("sethome")) {
                if(!player.hasPermission("sethome")) break;

                for(FactionHome home : player.getFaction().homes.values()) {
                    out.add(home.getDisplayName());
                }
            }
            else if(args[0].equals("delhome")) {
                if(!player.hasPermission("delhome")) break;

                for(FactionHome home : player.getFaction().homes.values()) {
                    out.add(home.getDisplayName());
                }
            }

            break;
        case 3:
            if(args[0].equals("vault")) {
                if(args[1].equals("open") && player.hasPermission("viewvault")) {
                    for(String displayName : player.getFaction().vaultsByName.keySet()) {
                        out.add(displayName);
                    }   
                }
                else if(args[1].equals("rename") && player.hasPermission("renamevault")) {
                    for(String displayName : player.getFaction().vaultsByName.keySet()) {
                        out.add(displayName);
                    }   
                }
                else if(args[1].equals("remove") && player.hasPermission("removevault")) {
                    for(String displayName : player.getFaction().vaultsByName.keySet()) {
                        out.add(displayName);
                    }   
                }
            }

        default: break;
        }

        return out;
    }
}
