package fun.felipe.view;

import fun.felipe.Client;
import fun.felipe.components.LocateControlPanelComponent;
import fun.felipe.entities.Locate;
import fun.felipe.scheduler.GetAllStatusTask;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ClientView extends JFrame {
    private final Map<String, JPanel> devicesComponents;

    public ClientView() throws HeadlessException {
        super("Controle Remoto");
        this.devicesComponents = new HashMap<>();
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(new JLabel("Tempo para Atualização (em Segundos): "));
        JTextField updateTimeField = new JTextField(5);
        updateTimeField.setText("10");
        topPanel.add(updateTimeField);
        JButton updateTimeButton = new JButton("Atualizar");
        updateTimeButton.addActionListener(action -> {
            Client.getGetAllStatusTask().cancel(true);
            int time;
            try {
                time = Integer.parseInt(updateTimeField.getText());
            } catch (Exception exception) {
                System.out.println("Erro ao tentar converter String -> int");
                return;
            }

            Client.setGetAllStatusTask(Client.getScheduledExecutorService().scheduleAtFixedRate(new GetAllStatusTask(), 0, time, TimeUnit.SECONDS));
        });
        topPanel.add(updateTimeButton);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel locateComponentPanel = new JPanel();
        locateComponentPanel.setLayout(new GridLayout(3, 3, 10, 10));
        for (Locate locate : Client.getInstance().getLocates()) {
            JPanel locatePanel = (new LocateControlPanelComponent(locate.id(), locate.displayName()));
            this.devicesComponents.put(locate.id(), locatePanel);
            locateComponentPanel.add(locatePanel);
        }
        this.add(locateComponentPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public Map<String, JPanel> getDevicesComponents() {
        return devicesComponents;
    }
}
