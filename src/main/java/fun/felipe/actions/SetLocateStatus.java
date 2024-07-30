package fun.felipe.actions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.felipe.Client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SetLocateStatus {
    private final String locateID;
    private final String status;

    public SetLocateStatus(String locateID, String status) {
        this.locateID = locateID;
        this.status = status;
    }

    public String setLocateStatus() {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(Client.getInstance().getAddress());

            JsonObject request = new JsonObject();
            request.addProperty("command", "set");
            request.addProperty("locate", locateID);
            request.addProperty("value", status);

            byte[] payload = request.toString().getBytes();
            DatagramPacket payloadPacket = new DatagramPacket(payload, payload.length, address, Client.getInstance().getPort());
            socket.send(payloadPacket);

            byte[] receivedResponse = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(receivedResponse, receivedResponse.length);
            socket.receive(receivedPacket);

            String response = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            return jsonResponse.get("status").getAsString();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
