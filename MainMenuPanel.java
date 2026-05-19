package com.transportgame.ui.panels;

import com.transportgame.ui.components.MenuButton;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends BackgroundPanel {

    private final GameWindow window;

    public MainMenuPanel(GameWindow window) {
        this.window = window;
        setPreferredSize(new Dimension(GameWindow.WIDTH, GameWindow.HEIGHT));
        setLayout(new GridBagLayout());
        buildMenuUI();
    }

    private void buildMenuUI() {
        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        JComponent title = new JComponent() {
            {
                setPreferredSize(new Dimension(500, 72));
                setMaximumSize(new Dimension(500, 72));
                setOpaque(false);
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 52));
                FontMetrics fm = g2.getFontMetrics();
                String text = "City Construct";
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = fm.getAscent();

                g2.setColor(new Color(60, 140, 255, 60));
                g2.drawString(text, x + 4, y + 4);
                g2.setColor(new Color(40, 110, 220, 120));
                g2.drawString(text, x + 2, y + 2);

                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y);
                g2.dispose();
            }
        };
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel accent = new JPanel();
        accent.setOpaque(true);
        accent.setBackground(new Color(70, 130, 230));
        accent.setMaximumSize(new Dimension(200, 2));
        accent.setPreferredSize(new Dimension(200, 2));
        accent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Transport management");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(160, 190, 240));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        MenuButton newGameBtn  = new MenuButton("New Game");
        MenuButton loadGameBtn = new MenuButton("Load Game");
        MenuButton settingsBtn = new MenuButton("Settings");
        MenuButton exitBtn     = new MenuButton("Exit");

        for (MenuButton b : new MenuButton[]{newGameBtn, loadGameBtn, settingsBtn, exitBtn}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        newGameBtn.addActionListener(e  -> window.showScreen("NEW_GAME"));
        loadGameBtn.addActionListener(e -> window.showScreen("LOAD_GAME"));
        settingsBtn.addActionListener(e -> window.showScreen("SETTINGS"));
        exitBtn.addActionListener(e     -> System.exit(0));

        menu.add(title);
        menu.add(Box.createVerticalStrut(8));
        menu.add(accent);
        menu.add(Box.createVerticalStrut(8));
        menu.add(subtitle);
        menu.add(Box.createVerticalStrut(40));
        menu.add(newGameBtn);
        menu.add(Box.createVerticalStrut(12));
        menu.add(loadGameBtn);
        menu.add(Box.createVerticalStrut(12));
        menu.add(settingsBtn);
        menu.add(Box.createVerticalStrut(12));
        menu.add(exitBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(menu, gbc);
    }

}
