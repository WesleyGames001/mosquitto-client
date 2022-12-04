package tk.wesleyramos.mosquittoclient.plugins;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.bukkit.Color;
import tk.wesleyramos.mosquittoclient.Mosquitto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MosquittoBungee extends Plugin {

    private Configuration configuration;

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

        getProxy().getPluginManager().registerCommand(this, new MosquittoBungeeCommand("mosquitto", null, "mosquittoclient"));
    }

    @Override
    public void onDisable() {
        Mosquitto.stop();
    }

    public void saveResource(String resourcePath, boolean replace) {
        try {
            File file = new File(getDataFolder(), resourcePath);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() || (replace && file.delete())) {
                Files.copy(getResourceAsStream("config.yml"), file.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            getProxy().getConsole().sendMessage(new TextComponent(Color.RED + "[MosquittoClient]: " + Color.RED + "falha ao tentar salvar o arquivo " + resourcePath));
        }
    }

    public Configuration getConfig() {
        try {
            if (this.configuration == null) {
                this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            getProxy().getConsole().sendMessage(new TextComponent(Color.RED + "[MosquittoClient]: " + Color.RED + "falha ao tentar carregar o arquivo config.yml"));
        }

        return configuration;
    }
}
