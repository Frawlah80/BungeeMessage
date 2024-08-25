package me.frawlah.bungeemsg;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class MsgCommand extends Command {

   public MsgCommand() {
      super("msg", "", "tell", "say");
   }

   public void execute(CommandSender sender, String[] args) {
      String prefix = Main.cg.getString("Prefix").replace("&", "§");
      if (sender instanceof ProxiedPlayer) {
         if (sender.hasPermission("bmsg.command.msg")) {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            if (args.length >= 2) {
               ProxiedPlayer player2 = Main.getInstance().getProxy().getPlayer(args[0]);
               if (player2 == null) {
                  player.sendMessage(prefix + Main.cg.getString("Player_Offline").replace("&", "§"));
                  return;
               }

               if (player.getName().equals(player2.getName())) {
                  player.sendMessage(prefix + Main.cg.getString("No_Message_Yourself").replace("&", "§"));
                  return;
               }

               if (ToggleMsg.tmsg.contains(player2)) {
                  player.sendMessage(prefix + Main.cg.getString("Messages_Disabled").replace("&", "§").replace("%player%", player2.getName()));
                  return;
               }

               StringBuilder mensaje = new StringBuilder();

               for(int i = 1; i < args.length; ++i) {
                  mensaje.append(args[i]).append(" ");
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

               if (ReplyCommand.replyhash.containsKey(player) || ReplyCommand.replyhash.containsKey(player2)) {
                  ReplyCommand.replyhash.remove(player);
                  ReplyCommand.replyhash.remove(player2);
                  ReplyCommand.replyhash.put(player, player2);
                  ReplyCommand.replyhash.put(player2, player);
               }

               ReplyCommand.replyhash.put(player, player2);
               ReplyCommand.replyhash.put(player2, player);
            } else {
               player.sendMessage(prefix + Main.cg.getString("Usage").replace("&", "§"));
            }
         } else {
            sender.sendMessage(prefix + Main.cg.getString("No_Permission").replace("&", "§"));
         }
      } else {
         sender.sendMessage(prefix + "command only for players!");
      }

   }
}
