package fun.felipe.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.felipe.Client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class GetAllLocateStatus {

    public Map<String, String> getAllLocateStatus() {
        Map<String, String> locateStatus = new HashMap<>();

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");

            JsonObject request = new JsonObject();
            request.addProperty("command", "get_all");

            byte[] payload = request.toString().getBytes();
            DatagramPacket payloadPacket = new DatagramPacket(payload, payload.length, address, Client.getInstance().getPort());
            socket.send(payloadPacket);

            byte[] receivedResponse = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(receivedResponse, receivedResponse.length);
            socket.receive(receivedPacket);

            String response = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            JsonArray locates = jsonResponse.get("locates").getAsJsonArray();

            for (JsonElement locate : locates) {
                JsonObject locateJson = locate.getAsJsonObject();
                locateStatus.put(locateJson.get("locate").getAsString(), locateJson.get("status").getAsString());
            }
            return locateStatus;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
