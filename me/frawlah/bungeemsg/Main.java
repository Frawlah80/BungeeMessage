package me.frawlah.bungeemsg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {
   public static Main instance;
   public static Configuration cg;

   public void onEnable() {
      instance = this;
      this.createFiles();
      registerConfig();
      this.getProxy().getPluginManager().registerCommand(this, new ReplyCommand());
      this.getProxy().getPluginManager().registerCommand(this, new MsgCommand());
      this.getProxy().getPluginManager().registerListener(this, new QuitEvent());
      this.getProxy().getPluginManager().registerCommand(this, new SocialSpy());
      this.getProxy().getPluginManager().registerCommand(this, new ToggleMsg());
      this.getProxy().getPluginManager().registerCommand(this, new ReloadCommand());

   }

   private void createFiles() {
      if (!this.getDataFolder().exists()) {
         this.getDataFolder().mkdir();
      }

      File file = new File(this.getDataFolder(), "config.yml");
      if (!file.exists()) {
         try {
            InputStream in = this.getResourceAsStream("config.yml");
            Files.copy(in, file.toPath());
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

   }

   public static void registerConfig() {
      try {
         cg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), "config.yml"));
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }

   public static Main getInstance() {
      return instance;
   }
}
