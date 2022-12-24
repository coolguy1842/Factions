package coolguy1842.factions.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

public class Faction {
    private UUID id;
    private String displayName;

    private UUID leader;
    private FactionRank defaultRank;
    
    private Long money;

    private HashMap<String, Object> options;

    public HashMap<UUID, FactionPlayer> players;

    public HashMap<UUID, FactionRank> ranks;
    public HashMap<String, FactionRank> ranksByName;
    
    public HashMap<UUID, FactionVault> vaults;
    public HashMap<String, FactionVault> vaultsByName;
    
    public HashMap<UUID, FactionHome> homes;
    public HashMap<String, FactionHome> homesByName;

    public ArrayList<UUID> invites;
    public ArrayList<Chunk> claims;

    
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
        
        this.invites = new ArrayList<>();
        this.claims = new ArrayList<>();
    }

    
    public UUID getID() { return this.id; }
    public Long getMoney() { return this.money; }
    public UUID getLeader() { return this.leader; }
    public String getDisplayName() { return this.displayName; }
    public FactionRank getDefaultRank() { return this.defaultRank; }
    
    public Component getFormattedDisplayName() {
        return Component.text(this.displayName); 
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


    public Object getOption(String option) {
        return this.options.get(option);
    }

    private String getOptionsString() {
        String out = "";

        for(Map.Entry<String, Object> entry : this.options.entrySet()) {
            out += entry.getKey() + ":" + entry.getValue().toString();
        }

        return out;
    }


    public void setOption(String option, Object value) {
        if(this.options.containsKey(option)) return;

        this.options.put(option, value);
        FactionsManager.getInstance().factionManager.setFactionOptions(this.id, this.getOptionsString());
    }

    public void removeOption(String option) {
        if(!this.options.containsKey(option)) return;
        
        this.options.remove(option);
        FactionsManager.getInstance().factionManager.setFactionOptions(this.id, this.getOptionsString());
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
