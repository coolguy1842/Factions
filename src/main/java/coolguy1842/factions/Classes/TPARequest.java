package coolguy1842.factions.Classes;

import org.bukkit.entity.Player;

import coolguy1842.factions.Enums.TPARequestType;
import net.kyori.adventure.text.Component;

public class TPARequest {
    public Player requester;
    public Player requested;

    public Component requesterDisplayName;
    public Component requestedDisplayName;
    
    public TPARequestType type;

    public TPARequest(Player requester, Player requested, TPARequestType type) {
        this.requester = requester;
        this.requested = requested;

        this.requesterDisplayName = requester.displayName();
        this.requestedDisplayName = requested.displayName();

        this.type = type;
    }
}