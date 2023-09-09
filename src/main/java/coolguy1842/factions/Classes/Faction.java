package coolguy1842.factions.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

public class Faction {
    private UUID id;
    private String displayName;

    private UUID leader;
    private FactionRank defaultRank;
    
    private Long money;
    private TextColor color;

    private HashMap<String, String> options;

    public HashMap<UUID, FactionPlayer> players;

    public HashMap<UUID, FactionRank> ranks;
    public HashMap<String, FactionRank> ranksByName;
    
    public HashMap<UUID, FactionVault> vaults;
    public HashMap<String, FactionVault> vaultsByName;
    
    public HashMap<UUID, FactionHome> homes;
    public HashMap<String, FactionHome> homesByName;
    
    public ArrayList<Faction> allies;
    public ArrayList<Faction> allyInvites;

    public ArrayList<UUID> invites;
    public ArrayList<FactionClaim> claims;

    
    public Faction(UUID id, String displayName, UUID leader, Long money) {
        this.id = id;
        
        this.displayName = displayName;
        this.leader = leader;

        this.money = money;

        this.options = FactionsManager.getInstance().factionManager.getFactionOptions(this.id);
        
        this.players = new HashMap<>();

        this.ranks = new HashMap<>();
        this.ranksByName = new HashMap<>();
        
        this.vaults = new HashMap<>();
        this.vaultsByName = new HashMap<>();
        
        this.homes = new HashMap<>();
        this.homesByName = new HashMap<>();
        
        this.allies = new ArrayList<>();
        this.allyInvites = new ArrayList<>();

        this.invites = new ArrayList<>();
        this.claims = new ArrayList<>();

        this.loadColor();
    }
    
    
    public UUID getID() { return this.id; }
    public Long getMoney() { return this.money; }
    public UUID getLeader() { return this.leader; }
    public String getDisplayName() { return this.displayName; }
    public FactionRank getDefaultRank() { return this.defaultRank; }
    
    public void formatPlayerName(FactionPlayer player) {
        Player p = player.getPlayer();
        if(p == null) return;

        p.displayName(PlayerUtil.getFormattedDisplayName(p));
        p.playerListName(p.displayName());
    }

    public void formatPlayersNames() {
        for(FactionPlayer player : this.players.values()) {
            this.formatPlayerName(player);
        }
    }

    public TextColor getColor() { return this.color; }
    public void loadColor() {
        if(!this.options.containsKey("color")) {
            this.color = TextColor.color(255, 255, 255);
            return;
        }

        String[] colorStr = this.options.get("color").split(",");
        
        this.color = TextColor.color(Integer.parseInt(colorStr[0]), Integer.parseInt(colorStr[1]), Integer.parseInt(colorStr[2])); 
        this.formatPlayersNames();
    }
    
    public Component getFormattedDisplayName() {
        Component displayName = Component.text(this.displayName).color(this.getColor());
        
        return displayName;
    }

    public Boolean hasOnlinePlayer() {
        for(FactionPlayer player : this.players.values()) {
            if(player.getPlayer() != null) return true;
        }

        return false;
    }

    
    public void setMoney(Long money) {
        this.money = money;
        FactionsManager.getInstance().factionManager.setFactionMoney(this.id, money);
    }
    
    public void setLeader(UUID leader) {
        this.leader = leader;
        FactionsManager.getInstance().factionManager.setFactionLeader(this.id, leader);
    }
    

    public void setDefaultRank(UUID rank) {
        if(this.defaultRank != null) this.defaultRank.setIsDefault(false);

        this.defaultRank = FactionsManager.getInstance().rankManager.getRank(rank);
        if(this.defaultRank != null) this.defaultRank.setIsDefault(true);
    }
    
    public void setDefaultRank(FactionRank rank) {
        if(this.defaultRank != null) this.defaultRank.setIsDefault(false);

        this.defaultRank = rank;
        if(rank != null) rank.setIsDefault(true);
    }
    

    public void setDisplayName(String displayName) {
        FactionsManager.getInstance().factionManager.factionsNameLookup.remove(this.displayName);

        this.displayName = displayName;

        for(FactionVault vault : this.vaults.values()) {
            vault.saveInventory();
            vault.loadInventory();
        }

        this.formatPlayersNames();

        FactionsManager.getInstance().factionManager.factionsNameLookup.put(this.displayName, this.id);
        FactionsManager.getInstance().factionManager.setFactionDisplayName(this.id, displayName);
    }


    public FactionRank getRank(UUID id) {
        if(this.ranks.containsKey(id)) return this.ranks.get(id);
        return null;
    }

    public FactionRank getRank(String displayName) {
        if(this.ranksByName.containsKey(displayName)) return this.ranksByName.get(displayName);
        return null;
    }


    public FactionVault getVault(String displayName) { 
        if(!this.hasVault(displayName)) return null;
        return this.vaultsByName.get(displayName);
    }
    
    public FactionVault getVault(UUID id) { 
        if(!this.hasVault(id)) return null;
        return this.vaults.get(id);
    }

    
    public FactionHome getHome(String displayName) { 
        if(!this.hasHome(displayName)) return null;
        return this.homesByName.get(displayName);
    }
    
    public FactionHome getHome(UUID id) { 
        if(!this.hasHome(id)) return null;
        return this.homes.get(id);
    }


    public String getOption(String option) {
        if(!this.options.containsKey(option)) return null;

        return this.options.get(option);
    }

    public void setOption(String option, String value) {
        this.options.put(option, value);
        FactionsManager.getInstance().factionManager.setFactionOption(this.id, option, value);
    }

    public void removeOption(String option) {
        if(!this.options.containsKey(option)) return;
        
        this.options.remove(option);
        FactionsManager.getInstance().factionManager.deleteFactionOption(this.id, option);
    }


    public Boolean hasPlayer(UUID playerID) {
        return this.players.containsKey(playerID);
    }


    public Boolean hasDefaultRank() {
        return this.defaultRank != null;
    }

    public Boolean hasRank(UUID id) {
        return this.ranks.containsKey(id);
    }

    public Boolean hasRank(String displayName) {
        return this.ranksByName.containsKey(displayName);
    }

    
    public Boolean hasVault(UUID vaultID) {
        return this.vaults.containsKey(vaultID);
    }
    
    public Boolean hasVault(String displayName) {
        return this.vaultsByName.containsKey(displayName);
    }
    
    
    public Boolean hasHome(UUID vaultID) {
        return this.homes.containsKey(vaultID);
    }
    
    public Boolean hasHome(String displayName) {
        return this.homesByName.containsKey(displayName);
    }
    

    public Boolean isAllied(Faction faction) {
        return this.allies.contains(faction);
    }

    public Boolean hasAllyInvite(Faction inviter) {
        return this.allyInvites.contains(inviter);
    }

    public void addAllyInvite(Faction inviter) {
        Component[] message = {
            Globals.factionsPrefix, 
            Bukkit.getPlayer(inviter.getLeader()).displayName(), 
            Component.text(" has invited \""), 
            this.getFormattedDisplayName(),
            Component.text("\" to be allies.")
        };
                                
        Component[] commandMessage = {
            Component.text("[Accept] ")
                .color(TextColor.color(0, 255, 0))
                .clickEvent(ClickEvent.runCommand("/f ally " + inviter.getDisplayName()))
                .hoverEvent(
                    HoverEvent.showText(Component.text("Accept \"")
                        .append(inviter.getFormattedDisplayName())
                        .append(Component.text("\" to be your allies?"))
                    )
                ),
            Component.text("[Decline]")
                .color(TextColor.color(255, 0, 0))
                .clickEvent(ClickEvent.runCommand("/f declineally " + inviter.getDisplayName()))
                .hoverEvent(
                    HoverEvent.showText(Component.text("Decline \"")
                                        .append(inviter.getFormattedDisplayName())
                                        .append(Component.text("\" from being your allies?"))
                    )
                )
        };

        FactionsManager.getInstance().factionManager.createFactionAllyInvite(inviter, this);

        inviter.broadcastMessage(message);
        this.broadcastMessage(message);

        FactionsMessaging.sendMessage(this.getLeader(), commandMessage);
    }

    public void removeAllyInvite(Faction invited) {
        Component[] message = {
            Globals.factionsPrefix, 
            Bukkit.getPlayer(this.getLeader()).displayName(), 
            Component.text(" has Rescinded their invite to \""), 
            invited.getFormattedDisplayName(),
            Component.text("\" to be allies.")
        };
                                
        FactionsManager.getInstance().factionManager.deleteFactionAllyInvite(this, invited);

        invited.broadcastMessage(message);
        this.broadcastMessage(message);
    }

    public void acceptAllyInvite(Faction inviter) {
        Component[] message = {
            Globals.factionsPrefix, 
            Component.text("\""),
            this.getFormattedDisplayName(), 
            Component.text("\" is now allied with \""),
            inviter.getFormattedDisplayName(),
            Component.text("\".")
        };
        
        
        FactionsMessaging.broadcastMessage(null, message);
        FactionsMessaging.sendToDiscord("[Factions]", null, message);

        FactionsManager.getInstance().factionManager.deleteFactionAllyInvite(inviter, this);
        FactionsManager.getInstance().factionManager.createFactionAlly(inviter, this);
    }


    public void unally(Faction ally) {
        Component[] message = {
            Component.text(""),
            this.getFormattedDisplayName(),
            Component.text(" is no longer allied with "),
            ally.getFormattedDisplayName(),
            Component.text("."),
        };

        FactionsManager.getInstance().factionManager.deleteFactionAlly(this, ally);

        FactionsMessaging.broadcastMessage(null, message);
        FactionsMessaging.sendToDiscord(null, null, message);
    }


    public Boolean hasInvite(UUID player) {
        return this.invites.contains(player);
    }
    
    public Boolean hasInvite(Player player) {
        return this.invites.contains(player.getUniqueId());
    }


    public void addInvite(Player player) {
        if(this.hasInvite(player)) return;

        this.invites.add(player.getUniqueId());
        FactionsManager.getInstance().inviteManager.createFactionInvite(player, this.id);
    }
    
    public void addInvite(UUID player) {
        if(this.hasInvite(player)) return;
        
        this.invites.add(player);
        FactionsManager.getInstance().inviteManager.createFactionInvite(player, this.id);
    }
    

    public void removeInvite(Player player) {
        if(!this.hasInvite(player)) return;

        this.invites.remove(player.getUniqueId());
        FactionsManager.getInstance().inviteManager.deletePlayerInvite(player, this.id);
    }
    
    public void removeInvite(UUID player) {
        if(!this.hasInvite(player)) return;
        
        this.invites.remove(player);
        FactionsManager.getInstance().inviteManager.deletePlayerInvite(player, this.id);
    }

    public void removeAllInvites() {
        for(UUID invite : this.invites) {
            FactionsManager.getInstance().inviteManager.deletePlayerInvite(invite, this.id);
        }

        this.invites.clear();
    }


    public void broadcastMessage(Component... components) {
        Component message = Component.empty();

        for(Component component : components) {
            message = message.append(component);
        }

        for(FactionPlayer factionPlayer : this.players.values()) {
            FactionsMessaging.sendMessage(factionPlayer.getID(), message);
        }
    }
}
