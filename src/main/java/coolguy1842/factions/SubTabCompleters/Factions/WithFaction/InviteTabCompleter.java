package coolguy1842.factions.SubTabCompleters.Factions.WithFaction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class InviteTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        if(!player.hasPermission("invite")) return out; 
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(FactionsManager.getInstance().playerManager.getPlayer(onlinePlayer).inFaction()) continue;
            if(FactionsManager.getInstance().inviteManager.playerHasInvite(onlinePlayer, player.getFaction().getID())) continue;

            out.add(onlinePlayer.getName());
        }

        return out;
    }
}
