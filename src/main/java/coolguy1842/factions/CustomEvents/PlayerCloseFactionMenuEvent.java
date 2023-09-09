package coolguy1842.factions.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import coolguy1842.factions.Classes.FactionPlayer;

public class PlayerCloseFactionMenuEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private FactionPlayer player;


    public PlayerCloseFactionMenuEvent(FactionPlayer player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }


    public FactionPlayer getPlayer() {
        return player;
    }
}
