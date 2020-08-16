package moe.gensoukyo.mcgproject.cilent.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.swing.*;

@SideOnly(Side.CLIENT)
public class ShouterThread extends Thread {

    private String message;
    private String title;

    public ShouterThread(String message, String title) {
        this.message = message;
        this.title = title;
    }

    @Override
    public void run() {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
