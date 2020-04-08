package moe.gensoukyo.mcgproject.common.util;

import javax.swing.*;

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
