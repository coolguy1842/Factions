package coolguy1842.factions.Managers.FactionsManagers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Managers.FactionsManagerMethods.Booleans.HasHome;
import coolguy1842.factions.Managers.FactionsManagerMethods.Creators.CreateHome;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteHome;
import coolguy1842.factions.Managers.FactionsManagerMethods.Getters.GetHome;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadHomes;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionHome.SetHomeDisplayName;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionHome.SetHomeLocation;

public class HomeManager {
    public HashMap<UUID, FactionHome> homes;

    public HomeManager() {
        this.homes = new HashMap<>();
    }

    public void close() {
        this.homes.clear();

        this.homes = null;
    }

    
    public void loadHomes() { LoadHomes.load(FactionsManager.getInstance()); }
    public FactionHome loadHome(UUID id) { return LoadHomes.loadHome(FactionsManager.getInstance(), id); }


    public FactionHome createHome(UUID id, String displayName, Location location, Faction faction) { 
        return CreateHome.create(FactionsManager.getInstance(), id, displayName, location, faction);
    }
    

    public void deleteHome(UUID id) { DeleteHome.delete(FactionsManager.getInstance(), id); }
    public void deleteHome(String displayName, Faction faction) { DeleteHome.delete(FactionsManager.getInstance(), displayName, faction); }


    public FactionHome getHome(UUID id) { return GetHome.get(FactionsManager.getInstance(), id); }
    public FactionHome getHome(String displayName, Faction faction) { return GetHome.get(FactionsManager.getInstance(), displayName, faction); }

    public Boolean hasHome(UUID id) { return HasHome.has(FactionsManager.getInstance(), id); }
    public Boolean hasHome(String displayName, Faction faction) { return HasHome.has(FactionsManager.getInstance(), displayName, faction); }


    public void setHomeDisplayName(UUID id, String displayName) { SetHomeDisplayName.set(FactionsManager.getInstance(), id, displayName); }
    
    public void setHomeLocation(UUID id, Location location) { SetHomeLocation.set(FactionsManager.getInstance(), id, location); }
}
