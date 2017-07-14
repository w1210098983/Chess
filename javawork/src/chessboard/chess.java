package chessboard;

import chessboard.thread.UpdateThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by 吴和伟 on 2017/7/9.
 */
public class chess {
    private int value;
    private int falg = 0;
    private boolean notalive = true;
    private Path path = new Path();
    private JFrame frame;
    private Container c;
    private JPanel chessboard;
    private GridLayout grid = new GridLayout(8, 8);
    private JLabel label[][] = new JLabel[8][8];
    private Dimension preferredSize = new Dimension(300, 50);
    private Box box = Box.createHorizontalBox();
    private File file;
    //声明线程
    private UpdateThread updateThread;

    //构造函数
    public chess() {
        file = new File("C:\\Users\\w1210\\IdeaProjects\\javawork\\src\\chessboard\\horse.png");
        frame = new JFrame("国际象棋遍历");
        frame.setSize(800, 850);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        c = frame.getContentPane();
        c.setLayout(new BorderLayout());
        chessboard = new JPanel();
        chessboard.setLayout(grid);
        create();
        worker();
        c.add(chessboard, BorderLayout.CENTER);
        c.add(box, BorderLayout.SOUTH);
        frame.setVisible(true);
        //刷新界面线程
        updateThread = new UpdateThread();
        updateThread.setFrame(frame);
        updateThread.setLabel(label);
        updateThread.setPath(path);
        updateThread.setFile(file);
    }
    //棋盘区
    void create() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                label[i][j] = new JLabel();
                label[i][j].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                if ((i + j) % 2 == 0) label[i][j].setBackground(Color.black);
                else label[i][j].setBackground(Color.white);
                int finalI = i;
                int finalJ = j;
                label[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (notalive) {
                            if (e.getClickCount() >= 1) {
                                if (falg == 1) {
                                    resetColor();
                                }
                                value = finalI * 8 + finalJ;
                                label[finalI][finalJ].setBackground(Color.orange);
                                label[finalI][finalJ].setIcon(new ImageIcon(file.toString()));
                                falg = 1;
                            }
                        }
                    }
                });
                label[i][j].setOpaque(true);
                chessboard.add(label[i][j]);
            }
        }
    }
    //功能按钮区
    void worker() {
        JButton button1 = new JButton("开始");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notalive = false;
                trave();
            }
        });
        button1.setPreferredSize(preferredSize);
        button1.setBackground(Color.green);
        JButton button2 = new JButton("暂停");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateThread.setFlagTrue();
            }
        });
        button2.setPreferredSize(preferredSize);
        button2.setBackground(Color.green);
        JButton button4 = new JButton("继续");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateThread.setFlagFalse();
            }
        });
        button4.setPreferredSize(preferredSize);
        button4.setBackground(Color.green);
        JButton button3 = new JButton("重置");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetColor();
                notalive = true;
                updateThread.setAlive();
                updateThread = new UpdateThread();
                path.queue.clear();
                updateThread.setFrame(frame);

                updateThread.setLabel(label);

                updateThread.setPath(path);
                updateThread.setFile(file);
            }
        });
        button3.setPreferredSize(preferredSize);
        button3.setBackground(Color.green);
        box.add(Box.createHorizontalStrut(40));
        box.add(button1);
        box.add(Box.createHorizontalStrut(40));
        box.add(button2);
        box.add(Box.createHorizontalStrut(40));
        box.add(button4);
        box.add(Box.createHorizontalStrut(40));
        box.add(button3);
        box.add(Box.createHorizontalStrut(40));
    }

    public void trave() {
        label[value/ 8][value%8].setIcon(null);
        path.setValue(value);
        path.start(value);
        updateThread.start();
    }
    //重置颜色与图片
    public void resetColor() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) label[i][j].setBackground(Color.black);
                else label[i][j].setBackground(Color.white);
                label[i][j].setIcon(null);
            }
        }
    }

    public static void main(String[] args) {

        new chess();
    }

}

