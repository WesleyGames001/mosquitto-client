package tk.wesleyramos.mosquittoclient.plugins;

import org.bukkit.plugin.java.JavaPlugin;
import tk.wesleyramos.mosquittoclient.Mosquitto;

public class MosquittoBukkit extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveResource("config.yml", false);

        Mosquitto.configure(
                getConfig().getString("server.host"),
                getConfig().getInt("server.port"),
                getConfig().getString("auth.name"),
                getConfig().getString("auth.credentials")
        );
        Mosquitto.start();

        getCommand("mosquitto").setExecutor(new MosquittoBukkitCommand());
    }

    @Override
    public void onDisable() {
        Mosquitto.stop();
    }
}
