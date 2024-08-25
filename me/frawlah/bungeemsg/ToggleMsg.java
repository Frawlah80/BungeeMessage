package me.frawlah.bungeemsg;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class ToggleMsg extends Command {
   public static List<ProxiedPlayer> tmsg = new ArrayList<>();

   public ToggleMsg() {
      super("togglemsg", "", "tmsg");
   }

   public void execute(CommandSender sender, String[] args) {
      String prefix = Main.cg.getString("Prefix").replace("&", "§");
      if (sender instanceof ProxiedPlayer) {
         if (sender.hasPermission("bmsg.command.togglemsg")) {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            if (tmsg.contains(player)) {
               tmsg.remove(player);
               player.sendMessage(prefix + Main.cg.getString("ReceiveMessages_Enabled").replace("&", "§"));
               return;
            }

            tmsg.add(player);
            player.sendMessage(prefix + Main.cg.getString("ReceiveMessages_Disabled").replace("&", "§"));
         } else {
            sender.sendMessage(prefix + Main.cg.getString("No_Permission").replace("&", "§"));
         }
      } else {
         sender.sendMessage(prefix + "command only for players!");
      }

   }
}
