package fun.felipe.scheduler;

import fun.felipe.Client;
import fun.felipe.actions.GetAllLocateStatus;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GetAllStatusTask implements Runnable {
    @Override
    public void run() {
        Map<String, String> locateStatus = new GetAllLocateStatus().getAllLocateStatus();
        Map<String, JPanel> devicesComponents = Client.getView().getDevicesComponents();

        for (Map.Entry<String, JPanel> device : devicesComponents.entrySet()) {
            String status = locateStatus.get(device.getKey());
            if (status == null) throw new RuntimeException("Status " + device.getKey() + " não foi Encontrado!");

            JPanel panel = device.getValue();
            for (Component component : panel.getComponents()) {
                if (component instanceof JLabel label) {
                    label.setText("Status: " + status);
                }
            }
        }
    }
}
