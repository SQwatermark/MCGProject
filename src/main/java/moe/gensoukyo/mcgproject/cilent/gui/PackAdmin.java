package moe.gensoukyo.mcgproject.cilent.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author drzzm32
 * @date 2020/6/7
 */
public class PackAdmin extends JFrame {

    public static class Data extends LinkedHashMap<String, List<String>> {
        public Data() { super(); }
        public void add(String name, String... type) {
            put(name, Arrays.asList(type.clone()));
        }
    }

    @FunctionalInterface
    public interface Invoke {
        void run(String name, String type);
    }

    private static Invoke INVOKE = (name, type) -> System.out.println(name + " -> " + type);
    public static void setInvoke(Invoke invoke) {
        INVOKE = invoke;
    }

    private JPanel basePanel;
    private JPanel userPanel, typePanel;

    private final Data data;

    public PackAdmin(Data data) {
        super("PackAdmin");
        this.data = data;

        init();
        load();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        basePanel.registerKeyboardAction(e -> close(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(360,640);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);

        this.setAlwaysOnTop(false);
    }

    private void load() {
        ActionListener typeListener = e -> {
            String[] args = e.getActionCommand().split(":");
            if (args.length == 2)
                INVOKE.run(args[0], args[1]);
        };
        ActionListener nameListener = e -> {
            typePanel.removeAll();
            String name = e.getActionCommand();
            int i = 0;
            for (String type : data.get(name)) {
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.BOTH; c.gridy = i;
                c.gridx = 0; c.weightx = 1;
                c.ipadx = 4; c.ipady = 4;
                c.insets = new Insets(2, 2, 2, 2);
                JButton typeBtn = new JButton(type);
                typeBtn.setActionCommand(name + ":" + type);
                typeBtn.addActionListener(typeListener);
                typePanel.add(typeBtn, c);
                i += 1;
            }
            typePanel.revalidate();
        };

        int i = 0;
        for (String name : data.keySet()) {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH; c.gridy = i;
            c.gridx = 0; c.weightx = 1;
            c.ipadx = 4; c.ipady = 4;
            c.insets = new Insets(2, 2, 2, 2);
            JButton nameBtn = new JButton(name);
            nameBtn.setActionCommand(name);
            nameBtn.addActionListener(nameListener);
            userPanel.add(nameBtn, c);
            i += 1;
        }
    }

    private void init() {
        basePanel = new JPanel();
        basePanel.setLayout(new GridLayout(1, 2, 4, 4));
        basePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        typePanel = new JPanel();
        typePanel.setLayout(new GridBagLayout());
        typePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        JScrollPane pane = new JScrollPane(userPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        basePanel.add(pane);

        pane = new JScrollPane(typePanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        basePanel.add(pane);

        this.add(basePanel);
    }

    private void close() {
        this.dispose();
    }

}
