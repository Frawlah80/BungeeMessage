package me.frawlah.bungeemsg;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.query.QueryOptions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LuckPermsUtil {
    public static String getLuckPermsPrefix(ProxiedPlayer player) {
        String luckPermsPrefix = getActualLuckPermsPrefix(player);
        return ChatColor.translateAlternateColorCodes('&', luckPermsPrefix);
    }

    private static String getActualLuckPermsPrefix(ProxiedPlayer player) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());
        if (user == null) {
            return "";
        }
        CachedMetaData metaData = user.getCachedData().getMetaData(QueryOptions.defaultContextualOptions());
        return metaData.getPrefix() != null ? metaData.getPrefix() : "";
    }
}
