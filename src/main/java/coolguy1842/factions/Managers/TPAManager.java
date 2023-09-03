package coolguy1842.factions.Managers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.TPARequest;
import coolguy1842.factions.Enums.TPARequestType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

public class TPAManager {
    private static ArrayList<TPARequest> tpaRequests;

    private static TPAManager instance = null;

    private static Component executorMessage;
    static {
        executorMessage = Component.text("[ACCEPT]")
                          .color(TextColor.color(0, 255, 0))
                          .clickEvent(ClickEvent.runCommand("/tpaccept"))
                          .hoverEvent(HoverEvent.showText(Component.text("Accept TPA request?")));
                        
        executorMessage = executorMessage.append(Component.text(" "));
        
        executorMessage = executorMessage.append(Component.text("[DENY]")
                          .color(TextColor.color(255, 0, 0))
                          .clickEvent(ClickEvent.runCommand("/tpdeny"))
                          .hoverEvent(HoverEvent.showText(Component.text("Deny TPA request?"))));
    }

    private TPAManager() {
        instance = this;
        tpaRequests = new ArrayList<>();
    }

    public static TPAManager getInstance() {
        if(instance == null) instance = new TPAManager();
        return instance; 
    }

    public void close() {
        tpaRequests = null;
        instance = null;
    }

    public TPARequest getPlayerRequest(Player player) {
        for(TPARequest request : tpaRequests) {
            if(request.requester == player || request.requested == player) {
                return request;
            }
        }
        
        return null;
    }
    
    public TPARequest getPlayerRequested(Player player) {
        for(TPARequest request : tpaRequests) {
            if(request.requested == player) {
                return request;
            }
        }
        
        return null;
    }
    
    public TPARequest getPlayerRequester(Player player) {
        for(TPARequest request : tpaRequests) {
            if(request.requester == player) {
                return request;
            }
        }
        
        return null;
    }

    public TPARequest createPlayerRequest(Player requester, Player requested, TPARequestType type, Long timeOutDelay) {
        TPARequest request = new TPARequest(requester, requested, type);
        tpaRequests.add(request);
        
        Component requesterMSG;
        Component requestedMSG;
        switch(request.type) {
        case TPA:
            requesterMSG = Component.text("Sent TPA");
            requestedMSG = Component.text("TPA");
            break;
        case TPAHERE:
            requesterMSG = Component.text("Sent TPAHere");
            requestedMSG = Component.text("TPAHere");
            break;
        default:
            requesterMSG = Component.text("");
            requestedMSG = Component.text("");
            break;
        }
        
        requesterMSG = requesterMSG.append(Component.text(" request to ")
                    .append(request.requested.displayName())
                    .append(Component.text(".")));

        requestedMSG = requestedMSG.append(Component.text(" request from ")
                    .append(request.requester.displayName())
                    .append(Component.text(" received.")));

        requester.sendMessage(requesterMSG);

        requested.sendMessage(requestedMSG);
        requested.sendMessage(executorMessage);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Globals.plugin, new Runnable() {
            @Override
            public void run() {
                instance.removePlayerRequest(request);
            }
        }, timeOutDelay);

        return request;
    }

    public void removePlayerRequest(TPARequest request) {
        if(!tpaRequests.contains(request)) return;

        Component requesterMSG;
        Component requestedMSG;
        switch(request.type) {
        case TPA:
            requesterMSG = Component.text("TPA");
            requestedMSG = Component.text("TPA");
            break;
        case TPAHERE:
            requesterMSG = Component.text("TPAHere");
            requestedMSG = Component.text("TPAHere");
            break;
        default:
            requesterMSG = Component.text("");
            requestedMSG = Component.text("");
            break;
        }
        
        requesterMSG = requesterMSG.append(Component.text(" request to ")
                       .append(request.requested.displayName())
                       .append(Component.text(" cancelled.")));

        requestedMSG = requestedMSG.append(Component.text(" request from ")
                       .append(request.requester.displayName())
                       .append(Component.text(" cancelled.")));
        
        if(request.requester != null && request.requester.isOnline()) {
            request.requester.sendMessage(requesterMSG);
        }
        
        if(request.requested != null && request.requested.isOnline()) {
            request.requested.sendMessage(requestedMSG);
        }

        tpaRequests.remove(request);
    }

    public void acceptPlayerRequest(TPARequest request) {
        Component requesterMSG;
        Component requestedMSG;
        switch(request.type) {
        case TPA:
            requesterMSG = Component.text("TPA");
            requestedMSG = Component.text("TPA");
            break;
        case TPAHERE:
            requesterMSG = Component.text("TPAHere");
            requestedMSG = Component.text("TPAHere");
            break;
        default:
            requesterMSG = Component.text("");
            requestedMSG = Component.text("");
            break;
        }
        
        requesterMSG = requesterMSG.append(Component.text(" request to ")
                       .append(request.requested.displayName())
                       .append(Component.text(" accepted.")));

        requestedMSG = requestedMSG.append(Component.text(" request from ")
                       .append(request.requester.displayName())
                       .append(Component.text(" accepted.")));

        request.requester.sendMessage(requesterMSG);
        request.requested.sendMessage(requestedMSG);

        switch(request.type) {
        case TPA:
            request.requester.teleport(request.requested.getLocation());
            break;
        case TPAHERE:
            request.requested.teleport(request.requester.getLocation());
            break;
        default:
            break;
        }

        tpaRequests.remove(request);
    }
}
