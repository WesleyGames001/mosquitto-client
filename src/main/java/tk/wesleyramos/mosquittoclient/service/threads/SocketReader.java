package tk.wesleyramos.mosquittoclient.service.threads;

import org.json.JSONException;
import tk.wesleyramos.mosquittoclient.service.SocketPacket;
import tk.wesleyramos.mosquittoclient.service.SocketService;
import tk.wesleyramos.mosquittoclient.transcriber.Transcriber;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class SocketReader extends Thread {

    private final SocketService service;

    public SocketReader(SocketService service) {
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            if (!this.service.isConnected() || this.service.getSocket() == null) {
                continue;
            }

            try {
                DataInputStream reader = new DataInputStream(new BufferedInputStream(this.service.getSocket().getInputStream()));

                while (this.service.getSocket().getInputStream() != null) {
                    Transcriber.read(new SocketPacket(reader.readUTF()));
                }
            } catch (IOException | JSONException ignored) {
            }
        }
    }
}
