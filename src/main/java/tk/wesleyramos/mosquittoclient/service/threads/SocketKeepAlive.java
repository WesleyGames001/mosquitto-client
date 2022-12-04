package tk.wesleyramos.mosquittoclient.service.threads;

import tk.wesleyramos.mosquittoclient.service.SocketPacket;
import tk.wesleyramos.mosquittoclient.service.SocketPacketType;
import tk.wesleyramos.mosquittoclient.service.SocketService;

public class SocketKeepAlive extends Thread {

    private final SocketService service;

    public SocketKeepAlive(SocketService service) {
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException ignored) {
                continue;
            }

            if (!this.service.isConnected()) continue;

            if ((this.service.getLastKeepAlive() + 15000) < System.currentTimeMillis()) {
                this.service.connect();
                continue;
            }

            try {
                this.service.write(new SocketPacket(SocketPacketType.KEEP_ALIVE).target("mosquittoserver").set("currentTimeMillis", System.currentTimeMillis()));
            } catch (NullPointerException ignored) {
            }
        }
    }
}
