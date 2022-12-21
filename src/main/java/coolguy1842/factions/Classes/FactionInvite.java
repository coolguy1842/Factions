package coolguy1842.factions.Classes;

import java.util.UUID;

public class FactionInvite {
    private UUID invited;
    private Faction faction;

    public FactionInvite(UUID invited, Faction faction) {
        this.invited = invited;
        this.faction = faction;
    }

    public UUID getInvited() { return this.invited; }
    public Faction getFaction() { return this.faction; }
}
