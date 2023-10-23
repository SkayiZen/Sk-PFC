package ro.sk.game.commands.pfc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static ro.sk.game.commands.pfc.PfcCmd.pfcListGameByPlayer;

public class PfcEvent implements Listener {


    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (pfcListGameByPlayer.containsKey(player)) {
            if(pfcListGameByPlayer.containsKey(pfcListGameByPlayer.get(player))){
                pfcListGameByPlayer.remove(pfcListGameByPlayer.get(player));
            }
            pfcListGameByPlayer.remove(player);
        }

    }

}
