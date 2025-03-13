package core.GUI.MazeModeIndexJFrame.JPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 生成CenterPanel1面板，即：选择新游戏和历史记录的面板
 */
public class CenterPanel1 extends JPanel {
    public JPanel mazeModeIndexCenterPanel;
    private JButton newGame;
    private JButton history;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JFrame frame;

    public CenterPanel1(JFrame frame){
        this.frame = frame;
        this.setLayout(new GridBagLayout());
        newGame = new JButton("new game");
        history = new JButton("history");
        // 设置布局并默认添加newGame和history两个按钮
        setGBC();
        addListenerToNewGame();
        addListenerToHistory();
        // 将CenterPanel1添加到frame的中心
        frame.add(this, BorderLayout.CENTER);
    }

    private void addListenerToHistory() {
        // 监听 history 按钮
    }

    private void addListenerToNewGame() {
        // 监听 newGame 按钮
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到CenterPanel2面板
                changeToCenterPanel2(frame);
            }
        });
    }

    private void changeToCenterPanel2(JFrame frame) {
        CenterPanel2 centerPanel2 = new CenterPanel2(frame, this);
        centerPanel2.setVisible(true);
    }

    /**
     * 设置布局为GridBagConstraints布局并添加组件
     */
    private void setGBC() {

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // 设置行间距

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(newGame, gbc);

        gbc.gridy = 1;
        this.add(history, gbc);
    }
}
