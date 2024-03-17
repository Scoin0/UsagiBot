package usagibot.osu.memreaders;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

@Slf4j
@Getter
public class WebSocketConnector extends WebSocketClient {

    public static String jsonData;

    public WebSocketConnector(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake data) {
    }

    @Override
    public void onMessage(String message) {
        jsonData = message;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public String getJsonData() {
        return jsonData;
    }
}