package me.frawlah.bungeemsg;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class QuitEvent implements Listener {

   @EventHandler
   public void pDCListener(PlayerDisconnectEvent e) {
      ProxiedPlayer player = e.getPlayer();
      if (ReplyCommand.replyhash.containsKey(player) || ReplyCommand.replyhash.containsValue(player.getName())) {
         ProxiedPlayer player2 = ReplyCommand.replyhash.get(player);
         ReplyCommand.replyhash.remove(player);
         ReplyCommand.replyhash.remove(player2);
      }

   }
}
