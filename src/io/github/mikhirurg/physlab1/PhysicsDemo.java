package io.github.mikhirurg.physlab1;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PhysicsDemo {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 600;
    private static PhysicsPanel modPanel;
    private static PhysGraphicsPanel graphPanel;
    private static JTextField xStart;
    private static JTextField xEnd;
    private static JTextField hStart;
    private static JTextField hEnd;
    private static JTextField mu;
    private static JTextField startSpeed;
    private static JTextField x1;
    private static JTextField x2;
    private static JTextArea log;
    private static JCheckBox showDots;
    private static JCheckBox showGrid;


    public static JButton button;
    public static JButton reset;
    private final static int ipadX = 30;
    private final static int ipadY = 20;
    private static int id = 1;

    static Timer timer;

    private static long animationStartTime;
    private static long animationDuration;

    private static long currTime;
    private static long totalTime;

    static Rail rail;
    static ArrayList<PhysicsExperimentContainer> experiments;

    private final static int mul = 1000;

    public static void addComponentsToPane(Container pane) {
        rail.calculate();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        modPanel = new PhysicsPanel(rail);
        modPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        modPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        modPanel.setBackground(Color.GRAY);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 11;
        c.weightx = 1;
        c.weighty = 1;
        pane.add(modPanel, c);

        graphPanel = new PhysGraphicsPanel(experiments);
        graphPanel.setScale((WIDTH / 2.0) / (rail.getMaxTime() * rail.getMaxTime() / 2 * 1000));
        graphPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        graphPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        graphPanel.setBackground(Color.WHITE);
        c.gridy = 12;
        c.gridheight = 1;
        pane.add(graphPanel, c);

        JLabel label = new JLabel("Координаты начала и конца рельса (мм):");
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.ipadx = ipadX;
        c.ipady = ipadY;
        c.weightx = 0;
        c.weighty = 0;
        pane.add(label, c);

        xStart = new JTextField(String.valueOf(rail.getxStart()));
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(xStart, c);

        xEnd = new JTextField(String.valueOf(rail.getxEnd()));
        c.gridx = 2;
        c.gridy = 1;
        c.gridheight = 1;
        pane.add(xEnd, c);

        label = new JLabel("Высота начала и конца рельса (мм):");
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.ipadx = ipadX;
        c.ipady = ipadY;
        pane.add(label, c);

        hStart = new JTextField(String.valueOf(rail.getHStart()));
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(hStart, c);

        hEnd = new JTextField(String.valueOf(rail.getHEnd()));
        c.gridx = 2;
        c.gridy = 3;
        c.gridheight = 1;
        pane.add(hEnd, c);

        label = new JLabel("Коэффициент трения: ");
        c.gridx = 1;
        c.gridy = 4;
        c.gridheight = 1;
        c.ipadx = ipadX;
        c.ipady = ipadY;
        pane.add(label, c);

        label = new JLabel("Начальная скорость (мм/с): ");
        c.gridx = 2;
        c.gridy = 4;
        c.gridheight = 1;
        pane.add(label, c);

        mu = new JTextField(String.valueOf(rail.getMu()));
        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(mu, c);

        startSpeed = new JTextField(String.valueOf(rail.getStartSpeed()));
        c.gridx = 2;
        c.gridy = 5;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(startSpeed, c);

        label = new JLabel("Координаты оптических ворот (мм): ");
        c.gridx = 1;
        c.gridy = 6;
        c.gridheight = 1;
        c.ipadx = ipadX;
        c.ipady = ipadY;
        pane.add(label, c);

        label = new JLabel("X1: ");
        c.gridx = 1;
        c.gridy = 7;
        c.gridheight = 1;
        c.ipadx = ipadX;
        c.ipady = ipadY;
        pane.add(label, c);

        label = new JLabel("X2: ");
        c.gridx = 2;
        c.gridy = 7;
        c.gridheight = 1;
        pane.add(label, c);

        x1 = new JTextField(String.valueOf(rail.getX1()));
        c.gridx = 1;
        c.gridy = 8;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(x1, c);

        x2 = new JTextField(String.valueOf(rail.getX2()));
        c.gridx = 2;
        c.gridy = 8;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(x2, c);

        showDots = new JCheckBox("Отобразить координаты точек");
        c.gridx = 1;
        c.gridy = 9;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        c.insets = new Insets(ipadY, 0, 0, 0);
        pane.add(showDots, c);

        showGrid = new JCheckBox("Отобразить сетку");
        c.gridx = 2;
        c.gridy = 9;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(showGrid, c);

        button = new JButton("Старт");
        c.fill = GridBagConstraints.SOUTHEAST;
        c.anchor = GridBagConstraints.SOUTH;
        c.gridx = 2;
        c.gridy = 10;
        c.gridheight = 1;


        showGrid.addActionListener(e -> {
            graphPanel.setShowGrid(showGrid.isSelected());
            graphPanel.repaint();
        });

        showDots.addActionListener(e -> {
            graphPanel.setShowDots(showDots.isSelected());
            graphPanel.repaint();
        });

        button.addActionListener(e -> {
            if (!timer.isRunning()) {
                try {
                    double dXStart = Double.parseDouble(xStart.getText());
                    double dXEnd = Double.parseDouble(xEnd.getText());
                    double dHStart = Double.parseDouble(hStart.getText());
                    double dHEnd = Double.parseDouble(hEnd.getText());
                    double dmu = Double.parseDouble(mu.getText());
                    double dStartSpeed = Double.parseDouble(startSpeed.getText());
                    double dx1 = Double.parseDouble(x1.getText());
                    double dx2 = Double.parseDouble(x2.getText());

                    if (dmu < 0 || dStartSpeed < 0 || dXStart > dXEnd
                            || dXEnd < dx1 || dXEnd < dx2
                            || dXStart > dx1 || dXStart > dx2) {
                        throw new NumberFormatException();
                    }

                    rail.setxStart(dXStart);
                    rail.setxEnd(dXEnd);
                    rail.setHStart(dHStart);
                    rail.setHEnd(dHEnd);
                    rail.setMu(dmu);
                    rail.setStartSpeed(dStartSpeed);
                    rail.setX1(dx1);
                    rail.setX2(dx2);
                    rail.calculate();

                    modPanel.updateObject();
                    graphPanel.setScale((graphPanel.getWidth() / 2.0) / (rail.getMaxTime() * rail.getMaxTime() / 2 * 1000));

                    animationStartTime = System.nanoTime();
                    button.setText("Стоп");
                    timer.start();
                } catch (NumberFormatException ne) {
                    xStart.setText(String.valueOf(rail.getxStart()));
                    xEnd.setText(String.valueOf(rail.getxEnd()));
                    hStart.setText(String.valueOf(rail.getHStart()));
                    hEnd.setText(String.valueOf(rail.getHEnd()));
                    mu.setText(String.valueOf(rail.getMu()));
                    startSpeed.setText(String.valueOf(rail.getStartSpeed()));
                    x1.setText(String.valueOf(rail.getX1()));
                    x2.setText(String.valueOf(rail.getX2()));

                    JOptionPane.showMessageDialog(pane, "Ошибка! Неправильный формат данных!");
                }
            } else {
                timer.stop();
                button.setText("Старт");
            }
        });

        pane.add(button, c);

        reset = new JButton("Сброс");
        c.fill = GridBagConstraints.SOUTHWEST;
        c.anchor = GridBagConstraints.SOUTH;
        c.gridx = 1;
        c.gridy = 10;
        c.gridheight = 1;

        reset.addActionListener(e -> {
            log.setText("");
            experiments.clear();
            id = 1;
            graphPanel.resetShift();
            graphPanel.repaint();
        });

        pane.add(reset, c);

        log = new JTextArea("");
        log.setEditable(false);
        c.gridx = 1;
        c.gridy = 12;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.ipadx = 0;
        c.ipady = 0;
        c.fill = GridBagConstraints.NORTH;
        c.anchor = GridBagConstraints.NORTH;
        JScrollPane scrollPane = new JScrollPane(log);
        scrollPane.setMinimumSize(new Dimension((int) (HEIGHT / 2.0), (int) (HEIGHT / 1.3)));
        scrollPane.setPreferredSize(new Dimension((int) (HEIGHT / 2.0), (int) (HEIGHT / 1.3)));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pane.add(scrollPane, c);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("PhysLab 1.02. Mikhail Ushakov M3102.");
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            System.err.println("Error while installing LaF");
        }

        rail = new Rail(
                0.0 * mul,
                0.8 * mul,
                0.2 * mul,
                0.192 * mul,
                0.0,
                0.00 * mul,
                0.15 * mul,
                0.7 * mul
        );
        rail.calculate();

        experiments = new ArrayList<>();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentsToPane(frame.getContentPane());

        frame.pack();

        timer = new Timer(30, e -> {
            currTime = System.nanoTime();
            totalTime = currTime - animationStartTime;
            if (totalTime > rail.getAnimDur()) {
                PhysicsExperimentContainer container = new PhysicsExperimentContainer(
                        id++, rail.getX1(), rail.getX2(), rail.getT1(), rail.getT2(), rail.getSinA(), rail.getAcceleration()
                );
                experiments.add(container);
                log.append(container.toString());
                modPanel.setTime(rail.getMaxTime());
                graphPanel.repaint();
                button.setText("Старт");
                timer.stop();
            } else {
                modPanel.setTime(totalTime / 1000000000.0);
            }
            modPanel.repaint();
        });

        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PhysicsDemo::createAndShowGUI);
    }
}