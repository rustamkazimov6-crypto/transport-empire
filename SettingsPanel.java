package com.transportgame.ui.panels;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends BackgroundPanel {

    private final GameWindow window;

    public SettingsPanel(GameWindow window) {
        this.window = window;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel card = new JPanel();
        card.setOpaque(true);
        card.setBackground(new Color(15, 20, 35, 220));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 160, 220), 1),
                BorderFactory.createEmptyBorder(16, 22, 18, 22)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(230, 230, 240));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel("<html>Configure visual and gameplay preferences for your session.</html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        desc.setForeground(new Color(190, 200, 215));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints fg = new GridBagConstraints();
        fg.insets = new Insets(6, 0, 6, 0);
        fg.gridx = 0;
        fg.gridy = 0;
        fg.anchor = GridBagConstraints.WEST;

        JLabel volumeLabel = new JLabel("Master volume:");
        volumeLabel.setForeground(new Color(210, 215, 230));
        JSlider volumeSlider = new JSlider(0, 100, 65);
        volumeSlider.setOpaque(false);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);

        JLabel speedLabel = new JLabel("Simulation speed:");
        speedLabel.setForeground(new Color(210, 215, 230));
        String[] speeds = {"Slow", "Normal", "Fast"};
        JComboBox<String> speedBox = new JComboBox<>(speeds);
        speedBox.setSelectedIndex(1);

        JCheckBox tutorials = new JCheckBox("Show tutorial hints on first runs");
        tutorials.setForeground(new Color(210, 215, 230));
        tutorials.setOpaque(false);
        tutorials.setSelected(true);

        fg.gridx = 0;
        fg.weightx = 0;
        form.add(volumeLabel, fg);
        fg.gridx = 1;
        fg.weightx = 1;
        fg.fill = GridBagConstraints.HORIZONTAL;
        form.add(volumeSlider, fg);

        fg.gridy++;
        fg.gridx = 0;
        fg.weightx = 0;
        fg.fill = GridBagConstraints.NONE;
        form.add(speedLabel, fg);
        fg.gridx = 1;
        fg.weightx = 1;
        fg.fill = GridBagConstraints.HORIZONTAL;
        form.add(speedBox, fg);

        fg.gridy++;
        fg.gridx = 0;
        fg.gridwidth = 2;
        fg.weightx = 1;
        fg.fill = GridBagConstraints.HORIZONTAL;
        form.add(tutorials, fg);

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton back = new JButton("Back");
        JButton apply = new JButton("Apply");

        styleSmallButton(back);
        stylePrimaryButton(apply);

        back.addActionListener(e -> window.showScreen("MAIN_MENU"));

        buttons.add(back);
        buttons.add(apply);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(desc);
        card.add(Box.createVerticalStrut(16));
        card.add(form);
        card.add(Box.createVerticalStrut(18));
        card.add(buttons);

        add(card, gbc);
    }

    private void styleSmallButton(JButton b) {
        b.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setBackground(new Color(35, 45, 70));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 130, 185)),
            BorderFactory.createEmptyBorder(5, 14, 5, 14)
        ));
    }

    private void stylePrimaryButton(JButton b) {
        b.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setBackground(new Color(70, 110, 190));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(140, 180, 240)),
            BorderFactory.createEmptyBorder(5, 14, 5, 14)
        ));
    }
}

