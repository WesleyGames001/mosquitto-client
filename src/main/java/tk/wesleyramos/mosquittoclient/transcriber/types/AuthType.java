package tk.wesleyramos.mosquittoclient.transcriber.types;

import tk.wesleyramos.mosquittoclient.Mosquitto;
import tk.wesleyramos.mosquittoclient.MosquittoColor;
import tk.wesleyramos.mosquittoclient.service.SocketPacket;
import tk.wesleyramos.mosquittoclient.transcriber.Transcriber;
import tk.wesleyramos.mosquittoclient.transcriber.TranscriberType;

public class AuthType implements TranscriberType {

    @Override
    public void execute(SocketPacket packet) {
        int status = packet.getInt("status");

        if (status == 200) {
            System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoClient] [Transcriber] [AUTH]: " + MosquittoColor.WHITE_BRIGHT + "a autenticação foi feita com sucesso!" + MosquittoColor.RESET);
        } else if (status == 400 || status == 401) {
            System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoClient] [Transcriber] [AUTH]: " + MosquittoColor.RED_BRIGHT + "a autenticação não pôde ser validada! message=" + packet.getString("message") + MosquittoColor.RESET);
            Mosquitto.stop();
        }

        Transcriber.callEvent(new AuthEvent(status, packet.getString("message")));
    }
}
