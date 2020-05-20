package moe.gensoukyo.mcgproject.cilent.gui;

import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author drzzm32
 * @date 2020/4/13
 */
public class StickerEditor extends JFrame {

    public static class StickerInfo {
        public int model = 0;
        public int animate = 0;
        public String url = "null";
        public long color = 0xFFFFFFFF;
        public double offsetX = 0, offsetY = 0, offsetZ = 0;
        public double rotateX = 0, rotateY = 0, rotateZ = 0;
        public double scaleX = 1, scaleY = 1, scaleZ = 1;
        public int width, height;
        public int frameWidth, frameHeight, frameTime, frameExtend;
        
        public void toSticker(TileSticker sticker) {
            sticker.model = model;
            sticker.animate = animate;
            sticker.url = url;
            sticker.color = color;
            sticker.offsetX = offsetX;
            sticker.offsetY = offsetY;
            sticker.offsetZ = offsetZ;
            sticker.rotateX = rotateX;
            sticker.rotateY = rotateY;
            sticker.rotateZ = rotateZ;
            sticker.scaleX = scaleX;
            sticker.scaleY = scaleY;
            sticker.scaleZ = scaleZ;
            sticker.width = width;
            sticker.height = height;
            sticker.frameWidth = frameWidth;
            sticker.frameHeight = frameHeight;
            sticker.frameTime = frameTime;
            sticker.frameExtend = frameExtend;
        }
    }

    @FunctionalInterface
    public interface ICallback {
        void invoke(StickerInfo info);
    }

    private ICallback callback;
    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    private JPanel basePanel;
    private JButton btnOK;
    private JPanel infoPanel;
    private JComboBox<String> model;
    private JComboBox<String> animate;
    private JTextField url;
    private JTextField color;
    private JTextField offsetX;
    private JTextField offsetY;
    private JTextField offsetZ;
    private JTextField rotateX;
    private JTextField rotateY;
    private JTextField rotateZ;
    private JTextField scaleX;
    private JTextField scaleY;
    private JTextField scaleZ;
    private JTextField width;
    private JTextField height;
    private JTextField frameWidth;
    private JTextField frameHeight;
    private JTextField frameTime;
    private JTextField frameExtend;

    public StickerEditor(TileSticker sticker) {
        super("Sticker Editor");
        this.callback = null;

        init();
        setSize(320, 320);

        setContentPane(basePanel);
        getRootPane().setDefaultButton(btnOK);

        btnOK.addActionListener(e -> close());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        basePanel.registerKeyboardAction(e -> close(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);

        model.setSelectedIndex(sticker.model);
        animate.setSelectedIndex(sticker.animate);
        url.setText(sticker.url);
        color.setText(String.format("%X", sticker.color));
        offsetX.setText(String.format("%1.2f", sticker.offsetX));
        offsetY.setText(String.format("%1.2f", sticker.offsetY));
        offsetZ.setText(String.format("%1.2f", sticker.offsetZ));
        rotateX.setText(String.format("%1.2f", sticker.rotateX));
        rotateY.setText(String.format("%1.2f", sticker.rotateY));
        rotateZ.setText(String.format("%1.2f", sticker.rotateZ));
        scaleX.setText(String.format("%1.2f", sticker.scaleX));
        scaleY.setText(String.format("%1.2f", sticker.scaleY));
        scaleZ.setText(String.format("%1.2f", sticker.scaleZ));
        width.setText(String.format("%d", sticker.width));
        height.setText(String.format("%d", sticker.height));
        frameWidth.setText(String.format("%d", sticker.frameWidth));
        frameHeight.setText(String.format("%d", sticker.frameHeight));
        frameTime.setText(String.format("%d", sticker.frameTime));
        frameExtend.setText(String.format("%d", sticker.frameExtend));
    }

    private long getLong(String str, long def) {
        try {
            return Long.parseLong(str, 16);
        } catch (NumberFormatException e) {
            return def;
        }
    }
    
    private double getDouble(String str, double def) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private int getInteger(String str, int def) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }
    
    private void close() {
        if (callback != null) {
            StickerInfo info = new StickerInfo();
            info.model = model.getSelectedIndex();
            info.animate = animate.getSelectedIndex();
            info.url = url.getText();
            info.color = getLong(color.getText(), 0xFFFFFFFF);
            info.offsetX = getDouble(offsetX.getText(), 0);
            info.offsetY = getDouble(offsetY.getText(), 0);
            info.offsetZ = getDouble(offsetZ.getText(), 0);
            info.rotateX = getDouble(rotateX.getText(), 0);
            info.rotateY = getDouble(rotateY.getText(), 0);
            info.rotateZ = getDouble(rotateZ.getText(), 0);
            info.scaleX = getDouble(scaleX.getText(), 1.0);
            info.scaleY = getDouble(scaleY.getText(), 1.0);
            info.scaleZ = getDouble(scaleZ.getText(), 1.0);
            info.width = getInteger(width.getText(), 1);
            info.height = getInteger(height.getText(), 1);
            info.frameWidth = getInteger(frameWidth.getText(), 1);
            info.frameHeight = getInteger(frameHeight.getText(), 1);
            info.frameTime = getInteger(frameTime.getText(), 1);
            info.frameExtend = getInteger(frameExtend.getText(), 1);
            
            callback.invoke(info);
        }

        this.dispose();
    }

    private void init() {
        basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout(0, 0));
        basePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), null));
        btnOK = new JButton();
        btnOK.setText("OK");
        basePanel.add(btnOK, BorderLayout.SOUTH);
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        basePanel.add(infoPanel, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("模型类型");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer1, gbc);
        model = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("单面模型");
        defaultComboBoxModel1.addElement("双面模型");
        model.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(model, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("资源路径");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label2, gbc);
        url = new JTextField();
        url.setHorizontalAlignment(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 10;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(url, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("图像尺寸");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label3, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer3, gbc);
        color = new JTextField();
        color.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(color, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("叠加颜色");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label4, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer5, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("帧尺寸");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer7, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("帧时间");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label6, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer8, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("平移控制");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label7, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer9, gbc);
        offsetX = new JTextField();
        offsetX.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 16;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(offsetX, gbc);
        offsetY = new JTextField();
        offsetY.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 16;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(offsetY, gbc);
        offsetZ = new JTextField();
        offsetZ.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 16;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(offsetZ, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 16;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer10, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("旋转控制");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 16;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label8, gbc);
        rotateX = new JTextField();
        rotateX.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 16;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(rotateX, gbc);
        rotateY = new JTextField();
        rotateY.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 16;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(rotateY, gbc);
        rotateZ = new JTextField();
        rotateZ.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 16;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(rotateZ, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer11, gbc);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        infoPanel.add(spacer12, gbc);
        final JPanel spacer13 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.VERTICAL;
        infoPanel.add(spacer13, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("缩放控制");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label9, gbc);
        final JPanel spacer14 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 17;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer14, gbc);
        scaleX = new JTextField();
        scaleX.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(scaleX, gbc);
        scaleY = new JTextField();
        scaleY.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(scaleY, gbc);
        scaleZ = new JTextField();
        scaleZ.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(scaleZ, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(panel1, gbc);
        width = new JTextField();
        width.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(width, gbc);
        height = new JTextField();
        height.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(height, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(panel2, gbc);
        frameWidth = new JTextField();
        frameWidth.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(frameWidth, gbc);
        frameHeight = new JTextField();
        frameHeight.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(frameHeight, gbc);
        final JSeparator separator1 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 12;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(separator1, gbc);
        final JPanel spacer15 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        infoPanel.add(spacer15, gbc);
        final JPanel spacer16 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.VERTICAL;
        infoPanel.add(spacer16, gbc);
        final JSeparator separator2 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 12;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(separator2, gbc);
        final JSeparator separator3 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 12;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(separator3, gbc);
        final JPanel spacer17 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.VERTICAL;
        infoPanel.add(spacer17, gbc);
        final JPanel spacer18 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.VERTICAL;
        infoPanel.add(spacer18, gbc);
        final JPanel spacer19 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.fill = GridBagConstraints.VERTICAL;
        infoPanel.add(spacer19, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("动画类型");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(label10, gbc);
        final JPanel spacer20 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        infoPanel.add(spacer20, gbc);
        animate = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("无动画");
        defaultComboBoxModel2.addElement("逐帧动画");
        defaultComboBoxModel2.addElement("逐帧动画（反向）");
        defaultComboBoxModel2.addElement("垂直滚动动画");
        defaultComboBoxModel2.addElement("垂直滚动动画（反向）");
        defaultComboBoxModel2.addElement("水平滚动动画");
        defaultComboBoxModel2.addElement("水平滚动动画（反向）");
        animate.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 10;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(animate, gbc);
        final JPanel spacer21 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(spacer21, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 12;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(panel3, gbc);
        frameTime = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(frameTime, gbc);
        frameExtend = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(frameExtend, gbc);
    }

}
