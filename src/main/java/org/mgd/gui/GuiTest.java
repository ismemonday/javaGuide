package org.mgd.gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;

public class GuiTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("机器人移动");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Droid robot1 = new Droid("robot1", 10, 10, 200, Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));
        frame.add(robot1);
        frame.pack();
        addCompunentTest(robot1);
        robot1.moveTo(600, 600);
        // 创建一个面板作为示例组件
        robot1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // 获取鼠标点击的位置（相对于contentPane组件）
                int mouseX = e.getX();
                int mouseY = e.getY();
                robot1.moveTo(mouseX, mouseY);
            }
        });
    }

    private static void addCompunentTest(Droid robot1) {
        // 创建根节点
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("根节点");
        // 创建一级子节点
        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode("一级子节点1");
        DefaultMutableTreeNode root2 = new DefaultMutableTreeNode("一级子节点2");
        DefaultMutableTreeNode root3 = new DefaultMutableTreeNode("一级子节点3");
        // 创建二级子节点
        DefaultMutableTreeNode root11 = new DefaultMutableTreeNode("二级子节点1");
        DefaultMutableTreeNode root12 = new DefaultMutableTreeNode("二级子节点2");
        DefaultMutableTreeNode root21 = new DefaultMutableTreeNode("二级子节点3");
        // 根节点添加一级子节点
        root.add(root1);
        root.add(root2);
        root.add(root3);
        // 一级子节点添加二级子节点
        root1.add(root11);
        root1.add(root12);
        root2.add(root21);

        JTree tree = new JTree(root);
        robot1.add(tree);

    }

    class MouseClientListener extends JFrame {

    }
}
