package moe.gensoukyo.mcgproject.cilent.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.swing.*;

@SideOnly(Side.CLIENT)
public class ShouterThread extends Thread {

    private String message;

    public ShouterThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        JOptionPane.showMessageDialog(null, message, "Java version issue", JOptionPane.WARNING_MESSAGE);
    }
}
