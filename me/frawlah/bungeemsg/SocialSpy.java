package me.frawlah.bungeemsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SocialSpy extends Command {
   public static HashMap<ProxiedPlayer, ProxiedPlayer> replyhash = new HashMap<>();
   public static List<ProxiedPlayer> sp = new ArrayList<>();

   public SocialSpy() {
      super("socialspy", "", "sp");
   }

   public void execute(CommandSender sender, String[] args) {
      String prefix = Main.cg.getString("Prefix").replace("&", "§");
      if (sender instanceof ProxiedPlayer) {
         ProxiedPlayer player = (ProxiedPlayer)sender;
         if (player.hasPermission("bmsg.command.socialspy")) {
            if (sp.contains(player)) {
               sp.remove(player);
               player.sendMessage(prefix + Main.cg.getString("SocialSpy_Off").replace("&", "§"));
               return;
            }

            sp.add(player);
            player.sendMessage(prefix + Main.cg.getString("SocialSpy_On").replace("&", "§"));
         } else {
            player.sendMessage(prefix + Main.cg.getString("No_Permission").replace("&", "§"));
         }
      }

   }
}
