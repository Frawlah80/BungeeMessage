package me.frawlah.bungeemsg;

import java.util.HashMap;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class ReplyCommand extends Command {
   public static HashMap<ProxiedPlayer, ProxiedPlayer> replyhash = new HashMap<>();

   public ReplyCommand() {
      super("r", "", "reply");
   }

   public void execute(CommandSender sender, String[] args) {
      String prefix = Main.cg.getString("Prefix").replace("&", "§");
      if (sender instanceof ProxiedPlayer) {
         ProxiedPlayer player = (ProxiedPlayer)sender;
         if (player.hasPermission("bmsg.command.reply")) {
            if (replyhash.containsKey(player)) {
               ProxiedPlayer player2 = replyhash.get(player);
               if (replyhash.containsValue(player2)) {
                  if (player2 != null) {
                     if (args.length >= 1) {
                        if (ToggleMsg.tmsg.contains(player2)) {
                           player.sendMessage(prefix + Main.cg.getString("Messages_Disabled").replace("&", "§").replace("%player%", player2.getName()));
                           replyhash.remove(player);
                           replyhash.remove(player2);
                           return;
                        }

                        StringBuilder mensaje = new StringBuilder();

                         for (String arg : args) {
                             mensaje.append(arg).append(" ");
                         }

                        String tusv = player.getServer().getInfo().getName();
                        String susv = player2.getServer().getInfo().getName();
                        String senderFormat = Main.cg.getString("Sender_Format")
                                .replace("&", "§")
                                .replace("%sender-name%", player.getName())
                                .replace("%sender-prefix%", LuckPermsUtil.getLuckPermsPrefix(player))
                                .replace("%sender-server%", tusv)
                                .replace("%msg%", mensaje.toString())
                                .replace("%receiver-server%", susv)
                                .replace("%receiver-name%", player2.getName())
                                .replace("%receiver-prefix%", LuckPermsUtil.getLuckPermsPrefix(player2));
                        String receiverFormat = Main.cg.getString("Receiver_Format")
                                .replace("&", "§")
                                .replace("%sender-name%", player.getName())
                                .replace("%sender-prefix%", LuckPermsUtil.getLuckPermsPrefix(player))
                                .replace("%sender-server%", tusv)
                                .replace("%msg%", mensaje.toString())
                                .replace("%receiver-server%", susv)
                                .replace("%receiver-name%", player2.getName())
                                .replace("%receiver-prefix%", LuckPermsUtil.getLuckPermsPrefix(player2));
                        player.sendMessage(senderFormat);
                        player2.sendMessage(receiverFormat);

                         for (ProxiedPlayer staffs : Main.getInstance().getProxy().getPlayers()) {
                             if (SocialSpy.sp.contains(staffs)) {
                                 String format = Main.cg.getString("SocialSpy_Format").replace("&", "§").replace("%sender%", player.getName()).replace("%receiver%", player2.getName()).replace("%msg%", mensaje.toString());
                                 staffs.sendMessage(format);
                             }
                         }

                        if (replyhash.containsKey(player) || replyhash.containsKey(player2)) {
                           replyhash.remove(player);
                           replyhash.remove(player2);
                           replyhash.put(player, player2);
                           replyhash.put(player2, player);
                        }

                        replyhash.put(player, player2);
                        replyhash.put(player2, player);
                     } else {
                        player.sendMessage(prefix + Main.cg.getString("Reply_Usage").replace("&", "§"));
                     }
                  } else {
                     player.sendMessage(prefix + Main.cg.getString("Player_Offline").replace("&", "§"));
                     replyhash.remove(player);
                     replyhash.remove(player2);
                  }
               } else {
                  player.sendMessage(prefix + Main.cg.getString("Player_Offline").replace("&", "§"));
               }
            } else {
               player.sendMessage(prefix + Main.cg.getString("No_Player_Reply").replace("&", "§"));
            }
         } else {
            player.sendMessage(prefix + Main.cg.getString("No_Reply_Permission").replace("&", "§"));
         }
      }

   }
}
