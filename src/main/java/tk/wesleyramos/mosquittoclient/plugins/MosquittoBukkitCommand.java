package tk.wesleyramos.mosquittoclient.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tk.wesleyramos.mosquittoclient.Mosquitto;

public class MosquittoBukkitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§9Mosquitto ➭ §fO cliente está " +
                (Mosquitto.getService().getSocket() != null && Mosquitto.getService().getSocket().isConnected() ? "§2conectado" : "§csem conexão") +
                " §fao servidor.");
        return false;
    }
}
