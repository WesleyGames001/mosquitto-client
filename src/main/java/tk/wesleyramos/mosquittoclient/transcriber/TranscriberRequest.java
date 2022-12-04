package tk.wesleyramos.mosquittoclient.transcriber;

import tk.wesleyramos.mosquittoclient.service.SocketPacket;

public abstract class TranscriberRequest {

    private final SocketPacket packet;

    public TranscriberRequest(SocketPacket request) {
        this.packet = request;
    }

    public abstract void callback(SocketPacket response);

    public SocketPacket getPacket() {
        return packet;
    }
}
