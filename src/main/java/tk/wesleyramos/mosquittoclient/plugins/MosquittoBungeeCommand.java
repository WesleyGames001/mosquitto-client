package tk.wesleyramos.mosquittoclient.plugins;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import tk.wesleyramos.mosquittoclient.Mosquitto;

public class MosquittoBungeeCommand extends Command {

    public MosquittoBungeeCommand(String name) {
        super(name);
    }

    public MosquittoBungeeCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        sender.sendMessage(new TextComponent("§9Mosquitto ➭ §fO cliente está " +
                (Mosquitto.getService().getSocket() != null && Mosquitto.getService().getSocket().isConnected() ? "§2conectado" : "§csem conexão") +
                " §fao servidor."));
    }
}
