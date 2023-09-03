package coolguy1842.factions.Events.Entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;

public class PlayerMove implements Listener {
    private static HashMap<Player, UUID> playerCurrentClaimList = new HashMap<>();
    private static Component wilderness = Component.text("Wilderness").color(TextColor.color(0, 255, 0));

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent e) {         
        if(e == null) return;
        Player p = e.getPlayer();
        FactionClaim newChunkClaim = FactionsManager.getInstance().claimManager.getClaim(e.getTo().getChunk());

        if(FactionsManager.getInstance().claimManager.playerHasAutoClaim(p) && newChunkClaim == null) {
            FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);

            if(player.getFaction().getMoney() >= 10) {
                player.getFaction().setMoney(player.getFaction().getMoney() - 10);
                FactionsManager.getInstance().claimManager.createClaim(p.getChunk(), player.getFaction().getID());

                newChunkClaim = FactionsManager.getInstance().claimManager.getClaim(e.getTo().getChunk());          
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("Claimed chunk successfully."));
                    
            }
            else FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("Couldn't autoclaim, not enough money."));
        } 

        UUID newFactionID = null;
        if(newChunkClaim != null) {
            newFactionID = newChunkClaim.getFaction().getID();
        }

        if(newFactionID == playerCurrentClaimList.get(p)) return;

        Component factionName = wilderness;
        if(newChunkClaim != null) {
            factionName = newChunkClaim.getFaction().getFormattedDisplayName();
        }

        playerCurrentClaimList.put(p, newFactionID);
        p.showTitle(Title.title(Component.text("Now Entering"), factionName, Times.times(Duration.ofMillis(500), Duration.ofMillis(750), Duration.ofMillis(500))));
    } 
}
