package tk.wesleyramos.mosquittoclient.transcriber;

import tk.wesleyramos.mosquittoclient.Mosquitto;
import tk.wesleyramos.mosquittoclient.MosquittoColor;
import tk.wesleyramos.mosquittoclient.service.SocketPacket;
import tk.wesleyramos.mosquittoclient.service.SocketPacketType;
import tk.wesleyramos.mosquittoclient.transcriber.events.Event;
import tk.wesleyramos.mosquittoclient.transcriber.events.EventHandler;
import tk.wesleyramos.mosquittoclient.transcriber.events.EventObserver;
import tk.wesleyramos.mosquittoclient.transcriber.types.AuthType;
import tk.wesleyramos.mosquittoclient.transcriber.types.KeepAliveType;
import tk.wesleyramos.mosquittoclient.transcriber.types.MessengerType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transcriber {

    private static final Map<SocketPacketType, TranscriberType> types = new HashMap<>();
    private static final List<EventObserver> observers = new ArrayList<>();
    private static final Map<String, TranscriberRequest> requests = new HashMap<>();

    static {
        types.put(SocketPacketType.AUTH, new AuthType());
        types.put(SocketPacketType.KEEP_ALIVE, new KeepAliveType());
        types.put(SocketPacketType.MESSENGER, new MessengerType());
    }

    public static void registerEvent(EventObserver observer) {
        observers.add(observer);
    }

    public static void unregisterEvent(EventObserver observer) {
        observers.remove(observer);
    }

    public static void read(SocketPacket packet) {
        if (!packet.isValid()) {
            System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoClient] [Transcriber]: " + MosquittoColor.RED_BRIGHT + "uma nova mensagem desconhecida foi recebida... packet=" + packet + MosquittoColor.RESET);
            return;
        }

        if (packet.getType() != null) {
            types.get(packet.getType()).execute(packet);
            return;
        }

        System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoClient] [Transcriber]: " + MosquittoColor.RED_BRIGHT + "uma nova mensagem desconhecida foi recebida... type=" + packet.getType().name() + "; body=" + packet.getBody() + MosquittoColor.RESET);
    }

    public static void write(TranscriberRequest request) {
        Mosquitto.getService().write(request.getPacket());
        requests.put(request.getPacket().getId(), request);
    }

    public static void callEvent(Event event) {
        for (EventObserver observer : observers) {
            for (Method declaredMethod : observer.getClass().getDeclaredMethods()) {
                EventHandler eventHandler = declaredMethod.getAnnotation(EventHandler.class);

                if (eventHandler == null || declaredMethod.getParameterCount() != 1) continue;

                Parameter parameter = declaredMethod.getParameters()[0];

                if (parameter.getType() != event.getClass()) continue;

                try {
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(observer, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Map<SocketPacketType, TranscriberType> getTypes() {
        return types;
    }

    public static List<EventObserver> getObservers() {
        return observers;
    }

    public static Map<String, TranscriberRequest> getRequests() {
        return requests;
    }
}
