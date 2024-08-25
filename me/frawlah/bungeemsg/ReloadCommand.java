package me.frawlah.bungeemsg;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {
   public ReloadCommand() {
      super("bmsgreload");
   }

   public void execute(CommandSender sender, String[] args) {
      if (sender instanceof ProxiedPlayer) {
         ProxiedPlayer player = (ProxiedPlayer)sender;
         if (player.hasPermission("bmsg.command.reload")) {
            Main.registerConfig();
            player.sendMessage("§aConfig has been reloaded!");
         } else {
            player.sendMessage("§cYou dont have permissions to use this command!");
         }
      } else {
         Main.registerConfig();
         sender.sendMessage("§aConfig has been reloaded!");
      }

   }
}
