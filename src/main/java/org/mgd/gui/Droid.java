package org.mgd.gui;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Droid extends JPanel {
    private static final int refreshMillis = 20;
    final String name;
    double x;//mm
    double y;//mm
    double targetX;//mm
    double targetY;//mm
    double speed;//mm/s
    Image image;
    ScheduledExecutorService scheduled;


    public Droid(String name, int x, int y, int speed, ScheduledExecutorService scheduled) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.image = new ImageIcon("src\\main\\java\\org\\mgd\\gui\\img\\droid.png").getImage();
        if (scheduled == null) {
            scheduled = Executors.newScheduledThreadPool(1);
        }
        this.scheduled = scheduled;
        start();
    }

    public void moveTo(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, (int)x, (int)y, null);
        }
    }

    private synchronized void start() {
        refreshLocation();
        scheduled.schedule(() -> {
            start();
        }, refreshMillis, TimeUnit.MILLISECONDS);
    }


    private void refreshLocation() {
        if (x != targetX) {
            double xAdd = (refreshMillis * speed) / (double) 1000;
            if (Math.abs(targetX - x) < xAdd) {
                x = targetX;
            } else {
                if (targetX > x) {
                    x+= xAdd;
                }else {
                    x -= xAdd;
                }
            }
        }
        if (y != targetY) {
            double yAdd = (refreshMillis * speed) / (double) 1000;
            if (Math.abs(targetY - y) < yAdd) {
                y = targetY;
            } else {
                if(targetY>y){
                    y += yAdd;
                }else {
                    y -= yAdd;
                }
            }
        }
        repaint();
    }
}
