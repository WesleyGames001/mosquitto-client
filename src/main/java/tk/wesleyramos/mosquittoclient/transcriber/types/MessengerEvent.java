package tk.wesleyramos.mosquittoclient.transcriber.types;

import tk.wesleyramos.mosquittoclient.service.SocketPacket;
import tk.wesleyramos.mosquittoclient.transcriber.events.Event;

public class MessengerEvent implements Event {

    private final SocketPacket packet;

    public MessengerEvent(SocketPacket packet) {
        this.packet = packet;
    }

    public SocketPacket getPacket() {
        return packet;
    }
}
