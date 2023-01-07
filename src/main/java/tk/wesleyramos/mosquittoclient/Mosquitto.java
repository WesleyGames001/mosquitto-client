package tk.wesleyramos.mosquittoclient;

import tk.wesleyramos.mosquittoclient.service.SocketService;

// TODO: adicionar criptografia pont-a-ponta
public class Mosquitto {

    private static final SocketService service = new SocketService();

    private static String host, name, credentials;
    private static int port;

    public static void start() {
        service.connect();
    }

    public static void stop() {
        service.disconnect();
    }

    public static void configure(String host, int port, String name, String credentials) {
        Mosquitto.host = host;
        Mosquitto.port = port;
        Mosquitto.name = name;
        Mosquitto.credentials = credentials;
    }

    public static String getHost() {
        return host;
    }

    public static String getName() {
        return name;
    }

    public static String getCredentials() {
        return credentials;
    }

    public static int getPort() {
        return port;
    }

    public static SocketService getService() {
        return service;
    }
}
