package tk.wesleyramos.mosquittoclient.transcriber;

import tk.wesleyramos.mosquittoclient.service.SocketPacket;

public interface TranscriberType {
    void execute(SocketPacket packet);
}
