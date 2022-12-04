package tk.wesleyramos.mosquittoclient.service;

import tk.wesleyramos.mosquittoclient.Mosquitto;
import tk.wesleyramos.mosquittoclient.MosquittoColor;
import tk.wesleyramos.mosquittoclient.service.threads.SocketKeepAlive;
import tk.wesleyramos.mosquittoclient.service.threads.SocketReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketService {

    private final SocketKeepAlive keepAlive;

    private SocketReader reader;
    private Socket socket;
    private DataOutputStream writer;

    private boolean connected;
    private long lastKeepAlive;

    public SocketService() {
        this.keepAlive = new SocketKeepAlive(this);
        this.keepAlive.start();
    }

    public void connect() {
        if (this.reader != null && !this.reader.isInterrupted()) {
            this.reader.interrupt();
        }

        try {
            this.connected = true;

            this.socket = new Socket(Mosquitto.getHost(), Mosquitto.getPort());
            this.reader = new SocketReader(this);
            this.reader.start();
            this.writer = new DataOutputStream(socket.getOutputStream());

            this.write(
                    new SocketPacket(SocketPacketType.AUTH)
                            .target("mosquittoserver")
                            .set("name", Mosquitto.getName())
                            .set("credentials", Mosquitto.getCredentials())
            );
            this.lastKeepAlive = System.currentTimeMillis();

            System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoClient] [Service]: " + MosquittoColor.WHITE_BRIGHT + "conectado ao servidor com sucesso" + MosquittoColor.RESET);
        } catch (IOException e) {
            e.printStackTrace();
            this.socket = null;
        }
    }

    public void disconnect() {
        try {
            this.connected = false;
            this.reader.interrupt();
            this.socket.close();

            System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoClient] [Service]: " + MosquittoColor.WHITE_BRIGHT + "desconectado do servidor com sucesso" + MosquittoColor.RESET);
        } catch (IOException | NullPointerException ignored) {
        }
    }

    public void write(SocketPacket packet) throws NullPointerException {
        try {
            this.writer.writeUTF(packet.toString());
            this.writer.flush();
        } catch (IOException ignored) {
        }
    }

    public SocketKeepAlive getKeepAlive() {
        return keepAlive;
    }

    public SocketReader getReader() {
        return reader;
    }

    public void setReader(SocketReader reader) {
        this.reader = reader;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getWriter() {
        return writer;
    }

    public void setWriter(DataOutputStream writer) {
        this.writer = writer;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public long getLastKeepAlive() {
        return lastKeepAlive;
    }

    public void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }
}
