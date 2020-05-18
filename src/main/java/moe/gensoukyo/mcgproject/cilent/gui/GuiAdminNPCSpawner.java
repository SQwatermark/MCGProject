package moe.gensoukyo.mcgproject.cilent.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.swing.*;

@SideOnly(Side.CLIENT)
public class GuiAdminNPCSpawner extends JFrame {

    private JTextArea area;

    public GuiAdminNPCSpawner() {
        super("NPC刷怪程序控制台");
        area = new JTextArea(3, 10);

        JLabel label = new JLabel("构造文本域:");
        label.setBounds(10, 10, 120, 20);
        area.setBounds(130, 10, 150, 100);
        this.setLayout(null);
        this.add(label);
        this.add(area);
        this.setSize(300, 150);
        this.setLocation(300, 200);
        this.setVisible(true);
    }

}
