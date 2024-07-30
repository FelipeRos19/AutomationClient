package fun.felipe.components;

import fun.felipe.actions.GetLocateStatus;
import fun.felipe.actions.SetLocateStatus;

import javax.swing.*;
import java.awt.*;

public class LocateControlPanelComponent extends JPanel {
    private final String locateID;
    private JLabel statusLabel;

    public LocateControlPanelComponent(String locateID, String locateDisplay) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        this.setVisible(true);

        this.locateID = locateID;

        JLabel titleLabel = new JLabel(locateDisplay, SwingConstants.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);

        this.statusLabel = new JLabel("Status: Desligado", SwingConstants.CENTER);
        this.add(statusLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton onButton = this.createOnButton();
        buttonPanel.add(onButton);

        JButton offButton = this.createOffButton();
        buttonPanel.add(offButton);

        JButton updateButton = this.createUpdateButton();
        buttonPanel.add(updateButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createOnButton() {
        JButton onButton = new JButton("Ligar");
        onButton.addActionListener(action -> {
            String statusResult = new SetLocateStatus(this.locateID, "on").setLocateStatus();
            if (statusResult == null) {
                throw new RuntimeException("Status não pode ser nulo!");
            }

            String formattedStatus = (statusResult.equals("on")) ? "Ligado" : "Desligado";
            statusLabel.setText("Status: " + formattedStatus);
        });

        return onButton;
    }

    private JButton createOffButton() {
        JButton offButton = new JButton("Desligar");
        offButton.addActionListener(action -> {
            String statusResult = new SetLocateStatus(this.locateID, "off").setLocateStatus();
            if (statusResult == null) {
                throw new RuntimeException("Status não pode ser nulo!");
            }

            String formattedStatus = (statusResult.equals("on")) ? "Ligado" : "Desligado";
            statusLabel.setText("Status: " + formattedStatus);
        });

        return offButton;
    }

    private JButton createUpdateButton() {
        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(action -> {
            String statusResult = new GetLocateStatus(this.locateID).fetchLocateStatus();
            if (statusResult == null) {
                throw new RuntimeException("Status não pode ser nulo!");
            }

            String formattedStatus = (statusResult.equals("on")) ? "Ligado" : "Desligado";
            statusLabel.setText("Status: " + formattedStatus);
        });

        return updateButton;
    }
}
