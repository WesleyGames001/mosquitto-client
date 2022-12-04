package tk.wesleyramos.mosquittoclient.transcriber.types;

import tk.wesleyramos.mosquittoclient.Mosquitto;
import tk.wesleyramos.mosquittoclient.service.SocketPacket;
import tk.wesleyramos.mosquittoclient.transcriber.TranscriberType;

public class KeepAliveType implements TranscriberType {

    @Override
    public void execute(SocketPacket packet) {
        Mosquitto.getService().setLastKeepAlive(packet.getLong("currentTimeMillis"));
    }
}
