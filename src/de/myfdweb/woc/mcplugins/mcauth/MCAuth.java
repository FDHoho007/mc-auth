package de.myfdweb.woc.mcplugins.mcauth;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MCAuth extends JavaPlugin implements Listener {

    private RESTServer server;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Token::cleanup, 0, 10*60*20);
        server = new RESTServer(25580);
    }

    @Override
    public void onDisable() {
        server.interrupt();
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        e.setMaxPlayers(0);
        e.setMotd("§a§lBestätige deine Identität durch Betreten des Servers.");
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§7Hallo, §a" + e.getPlayer().getName() + "§7!\n\n§7Verwende folgenden Code um dich anzumelden:\n§b§l" + new Token(e.getPlayer().getUniqueId().toString()).getToken() + "\n\n§8Du kannst diesen Code\n§8für §c3 Minuten §8verwenden,\n§8um dich bei Webseiten, Apps, etc.\n§8als §a" + e.getPlayer().getName() + " §8anzumelden.\n\n§7Minecraft Auth by FDHoho007\n§7github.com/FDHoho007/mc-auth");
    }

}
