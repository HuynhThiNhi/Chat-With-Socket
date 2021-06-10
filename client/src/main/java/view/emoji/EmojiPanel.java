package view.emoji;


import view.ChattingView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EmojiPanel extends JPanel {
    private JPanel rootPanel;
    private JPanel pnlPeople;
    private ChattingView chattingView;

    public EmojiPanel(ChattingView ChattingView) {
        setLayout(null);
        this.chattingView = ChattingView;

        rootPanel = new JPanel();
        rootPanel.setBounds(0, 0, 130, 250);//315 300
        add(rootPanel);
        rootPanel.setLayout(null);

        JTabbedPane tabEmoji = new JTabbedPane(JTabbedPane.TOP);
        tabEmoji.setBounds(0, 0, 130, 250);
        rootPanel.add(tabEmoji);

        List<String[]> emoji = EmojiContance.getAllEmojisSortedByCategory();

        for (int i = 0; i <emoji.size() ; i++) {
            pnlPeople = new JPanel();
            pnlPeople.setPreferredSize(new Dimension(100, (emoji.get(i).length/8)*37));//200->150
            tabEmoji.setFont(new Font("", Font.BOLD, 15));
            pnlPeople.setLayout(null);
            initPeople(emoji.get(i));
            JScrollPane scrollPane = new JScrollPane(pnlPeople, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            tabEmoji.addTab(emoji.get(i)[1], null, scrollPane, null);
        }

    }

    public void initPeople(String []emoji) {
        int k = 0;
        for (int i = 0; i < emoji.length / 8; i++) {
            for (int j = 0; j < 8; j++) {
                JPanel panel = new JPanel();
                panel.setLayout(null);
                final JLabel icon = new JLabel(emoji[k++]);
                icon.setHorizontalAlignment(SwingConstants.CENTER);
                icon.setFont(new Font("", Font.PLAIN, 25));
                icon.setBounds(0,0,25,25);
                panel.add(icon);
                panel.setBounds(10 + 35 * j, 11 + 36 * i, 25, 25);

                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        String msg = chattingView.getInputField().getText();
                        chattingView.setInputField(msg + " " + icon.getText());
                    }
                });
                pnlPeople.add(panel);
            }
        }
    }
}
