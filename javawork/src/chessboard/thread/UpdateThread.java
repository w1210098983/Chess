package chessboard.thread;

import chessboard.Path;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by 吴和伟 on 2017/7/10.
 */
public class UpdateThread extends Thread {

    private Path path;
    private JFrame frame;
    private JLabel label[][];
    private File file;
    private int val,vax,vay;

    boolean flag = false;
    boolean alive = true;

    public void setAlive() {
        this.alive = false;
    }

    public void setFlagTrue() {
        this.flag = true;
    }

    public void setFlagFalse() {
        this.flag = false;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setLabel(JLabel[][] label) {
        this.label = label;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        //frame.setVisible(true);
        for (int j = 0; j < 64; j++) {
            if (!alive) {
                return;
            }
            if (flag) {
                j--;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if(j > 0) label[vax][vay].setIcon(null);
            val = (int) path.queue.getQueue();
            path.queue.deQueue();
            vax = val / 8;
            vay = val % 8;
            try {
                label[vax][vay].setBackground(Color.cyan);
                label[vax][vay].setIcon(new ImageIcon(file.toString()));
                Thread.sleep(500);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
