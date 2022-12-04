package tk.wesleyramos.mosquittoclient.transcriber.types;

import tk.wesleyramos.mosquittoclient.service.SocketPacket;
import tk.wesleyramos.mosquittoclient.transcriber.Transcriber;
import tk.wesleyramos.mosquittoclient.transcriber.TranscriberRequest;
import tk.wesleyramos.mosquittoclient.transcriber.TranscriberType;

public class MessengerType implements TranscriberType {

    @Override
    public void execute(SocketPacket packet) {
        TranscriberRequest request = Transcriber.getRequests().get(packet.getId());

        if (request != null) {
            request.callback(packet);
        }

        Transcriber.callEvent(new MessengerEvent(packet));
    }
}
