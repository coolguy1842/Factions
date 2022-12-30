package coolguy1842.factions.SubTabCompleters.Factions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.BankTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.InviteTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.KickTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.RankTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.SetTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.TransferTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.VaultTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.Ally.AllyTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.Ally.DeclineAllyTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.Ally.UnAllyTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.Homes.DelHomeTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.Homes.HomeTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.WithFaction.Homes.SetHomeTabCompleter;

public class PlayerWithFactionTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        switch(args.length) {
        case 1:
            if(player.isLeader()) {
                out.add("disband");
                out.add("transfer");
                
                out.add("ally");
                out.add("unally");
                out.add("declineally");
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

            out.add("set");

            out.add("rank");
            out.add("ranks");
            
            out.add("leaderboard");

            if(player.hasPermission("openvault") ||  player.hasPermission("createvault") || 
                player.hasPermission("renamevault") || player.hasPermission("removevault")) {    
                out.add("vault");
            }

            out.add("bank");

            out.add("home");
            if(player.hasPermission("sethome")) out.add("sethome");
            if(player.hasPermission("delhome")) out.add("delhome");
            
            break;
        default: 
            switch(args[0]) {
            case "invite": return InviteTabCompleter.onTabComplete(p, player, command, label, args);
            case "kick": return KickTabCompleter.onTabComplete(p, player, command, label, args);
            case "transfer": return TransferTabCompleter.onTabComplete(p, player, command, label, args);
            case "home": return HomeTabCompleter.onTabComplete(p, player, command, label, args);
            case "sethome": return SetHomeTabCompleter.onTabComplete(p, player, command, label, args);
            case "delhome": return DelHomeTabCompleter.onTabComplete(p, player, command, label, args);
            case "rank": return RankTabCompleter.onTabComplete(p, player, command, label, args);
            case "set": return SetTabCompleter.onTabComplete(p, player, command, label, args);
            case "vault": return VaultTabCompleter.onTabComplete(p, player, command, label, args);
            case "bank": return BankTabCompleter.onTabComplete(p, player, command, label, args);
            case "ally": return AllyTabCompleter.onTabComplete(p, player, command, label, args);
            case "unally": return UnAllyTabCompleter.onTabComplete(p, player, command, label, args);
            case "declineally": return DeclineAllyTabCompleter.onTabComplete(p, player, command, label, args);
            default: break;
            }
            break;
        }

        return out;
    }
}
