package coolguy1842.factions.Events.Entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;

public class PlayerMove implements Listener {
    private static HashMap<Player, UUID> playerCurrentClaim = new HashMap<>();
    private static Component wilderness = Component.text("Wilderness").color(TextColor.color(0, 255, 0));

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent e) {         
        if(e == null) return;
        Player p = e.getPlayer();
        Faction newChunkFaction = FactionsManager.getInstance().claimManager.getClaim(e.getTo().getChunk());

        if(FactionsManager.getInstance().claimManager.playerHasAutoClaim(p) && newChunkFaction == null) {
            FactionsManager.getInstance().claimManager.createClaim(p.getChunk(), FactionsManager.getInstance().playerManager.getPlayer(p).getFaction().getID());

            newChunkFaction = FactionsManager.getInstance().claimManager.getClaim(e.getTo().getChunk());          
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("Claimed chunk successfully."));
        } 

        UUID newFactionID = (newChunkFaction == null ? null : newChunkFaction.getID());

        if(newFactionID == playerCurrentClaim.get(p)) return;

        Component factionName = (newChunkFaction == null ? wilderness : newChunkFaction.getFormattedDisplayName());
        playerCurrentClaim.put(p, newFactionID);

        p.showTitle(Title.title(Component.text("Now Entering"), factionName, Times.times(Duration.ofMillis(500), Duration.ofMillis(750), Duration.ofMillis(500))));
    } 
}
