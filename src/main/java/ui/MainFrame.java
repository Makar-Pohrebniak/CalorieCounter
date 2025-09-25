package ui;

import model.User;
import service.CalorieCalculator;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;

public class MainFrame extends JFrame {

    private JTextField nameField = new JTextField();
    private JTextField ageField = new JTextField();
    private JTextField heightField = new JTextField();
    private JTextField weightField = new JTextField();
    private JComboBox<String> genderBox = new JComboBox<>(new String[]{"male", "female"});
    private JComboBox<String> activityBox = new JComboBox<>(new String[]{

            "1.375 - Легка активність",
            "1.55 - Середня активність",
            "1.725 - Висока активність",

    });

    private JButton calcButton = new JButton("Порахувати");
    private JTextArea resultArea = new JTextArea();
    private JButton saveButton = new JButton("Зберегти результат");
    private JButton restartButton = new JButton("Почати знову");

    private JPanel inputPanel = new JPanel(new GridBagLayout());
    private JPanel resultPanel = new JPanel(new BorderLayout());
    private JPanel mainPanel = new JPanel(new CardLayout());

    public MainFrame() {
        setTitle("Калькулятор денних калорій");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        getContentPane().setBackground(new Color(200, 220, 240));
        setLayout(new BorderLayout());

        // --- Шрифти ---
        Font labelFont = new Font("Arial", Font.BOLD, 22);
        Font fieldFont = new Font("Arial", Font.PLAIN, 20);
        Font buttonFont = new Font("Arial", Font.BOLD, 26);
        Font headerFont = new Font("Arial", Font.BOLD, 28);
        Font resultFont = new Font("Arial", Font.BOLD, 22);

        setupField(nameField, fieldFont);
        setupField(ageField, fieldFont);
        setupField(heightField, fieldFont);
        setupField(weightField, fieldFont);
        setupField(genderBox, fieldFont);
        setupField(activityBox, fieldFont);

        setupButton(calcButton, buttonFont, new Color(0, 70, 140));
        setupButton(saveButton, buttonFont, new Color(0, 120, 215));
        setupButton(restartButton, buttonFont, new Color(180, 50, 50));

        // --- Input Panel ---
        inputPanel.setBackground(new Color(200, 220, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Заголовок
        JLabel headerLabel = new JLabel("Введіть параметри");
        headerLabel.setFont(headerFont);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(headerLabel, gbc);

        // Поля вводу з підписами
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Ім'я:");
        nameLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1; inputPanel.add(nameField, gbc);

        JLabel ageLabel = new JLabel("Вік:");
        ageLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(ageLabel, gbc);
        gbc.gridx = 1; inputPanel.add(ageField, gbc);

        JLabel heightLabel = new JLabel("Зріст (см):");
        heightLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(heightLabel, gbc);
        gbc.gridx = 1; inputPanel.add(heightField, gbc);

        JLabel weightLabel = new JLabel("Вага (кг):");
        weightLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(weightLabel, gbc);
        gbc.gridx = 1; inputPanel.add(weightField, gbc);

        JLabel genderLabel = new JLabel("Стать:");
        genderLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 5; inputPanel.add(genderLabel, gbc);
        gbc.gridx = 1; inputPanel.add(genderBox, gbc);

        JLabel activityLabel = new JLabel("Активність:");
        activityLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 6; inputPanel.add(activityLabel, gbc);
        gbc.gridx = 1; inputPanel.add(activityBox, gbc);

        // Кнопка Порахувати
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(calcButton, gbc);

        // --- Result Panel ---
        resultPanel.setBackground(new Color(220, 230, 250));
        JLabel resultHeader = new JLabel("Результат");
        resultHeader.setFont(headerFont);
        resultHeader.setHorizontalAlignment(SwingConstants.CENTER);

        resultArea.setBackground(new Color(240, 245, 255));
        resultArea.setForeground(Color.BLACK);
        resultArea.setFont(resultFont);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(new Color(240, 245, 255));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(restartButton);

        JPanel centerResultPanel = new JPanel();
        centerResultPanel.setLayout(new BoxLayout(centerResultPanel, BoxLayout.Y_AXIS));
        centerResultPanel.setBackground(new Color(240, 245, 255));
        centerResultPanel.add(Box.createVerticalGlue());
        resultHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerResultPanel.add(resultHeader);
        centerResultPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        resultArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultArea.setMaximumSize(new Dimension(600, 300));
        centerResultPanel.add(resultArea);
        centerResultPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerResultPanel.add(buttonsPanel);
        centerResultPanel.add(Box.createVerticalGlue());
        resultPanel.add(centerResultPanel, BorderLayout.CENTER);

        // --- Main Panel ---
        mainPanel.add(inputPanel, "input");
        mainPanel.add(resultPanel, "result");
        add(mainPanel, BorderLayout.CENTER);

        // --- Анімації ---
        addFieldAnimations(nameField);
        addFieldAnimations(ageField);
        addFieldAnimations(heightField);
        addFieldAnimations(weightField);
        addFieldAnimations(genderBox);
        addFieldAnimations(activityBox);

        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(KeyEvent e) { checkFields(); }
        };
        nameField.addKeyListener(keyListener);
        ageField.addKeyListener(keyListener);
        heightField.addKeyListener(keyListener);
        weightField.addKeyListener(keyListener);
        genderBox.addItemListener(e -> checkFields());
        activityBox.addItemListener(e -> checkFields());

        calcButton.addActionListener(e -> calculateAndShowResult());
        saveButton.addActionListener(e -> saveToFile());
        restartButton.addActionListener(e -> restart());

        setVisible(true);
    }

    private void setupField(JComponent comp, Font font) {
        comp.setBackground(new Color(180, 200, 240));
        comp.setForeground(Color.BLACK);
        comp.setFont(font);
        if (comp instanceof JTextField) ((JTextField) comp).setCaretColor(Color.BLACK);
        if (comp instanceof JComboBox) ((JComboBox<?>) comp).setFocusable(false);
    }

    private void setupButton(JButton button, Font font, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 70));
    }

    private void addFieldAnimations(JComponent comp) {
        comp.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { comp.setBackground(new Color(200, 220, 255)); }
            public void mouseExited(MouseEvent e) { comp.setBackground(new Color(180, 200, 240)); }
        });
    }

    private void checkFields() {
        boolean allFilled = !nameField.getText().isEmpty()
                && !ageField.getText().isEmpty()
                && !heightField.getText().isEmpty()
                && !weightField.getText().isEmpty();
        calcButton.setEnabled(allFilled);
        calcButton.setBackground(allFilled ? new Color(0, 180, 0) : new Color(0, 70, 140));
    }

    private void calculateAndShowResult() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            String gender = (String) genderBox.getSelectedItem();
            double activity = Double.parseDouble(activityBox.getSelectedItem().toString().split(" ")[0]);

            User user = new User(name, age, height, weight, gender, activity);
            double calories = CalorieCalculator.calculateCalories(user);

            double proteinMin = weight * 1.5;
            double proteinMax = weight * 2.2;
            double fatMin = weight * 0.8;
            double fatMax = weight * 1.2;
            double carb = (calories - (proteinMin + proteinMax)/2*4 - (fatMin + fatMax)/2*9)/4;

            StringBuilder sb = new StringBuilder();
            sb.append("Ім'я: ").append(name).append("\n")
                    .append("Вік: ").append(age).append("\n")
                    .append("Зріст: ").append(height).append(" см\n")
                    .append("Вага: ").append(weight).append(" кг\n")
                    .append("Стать: ").append(gender).append("\n")
                    .append("Активність: ").append(activity).append("\n\n")
                    .append("Калорії: ").append((int)calories).append(" ккал\n")
                    .append("Білки: ").append(String.format("%.1f", proteinMin))
                    .append(" - ").append(String.format("%.1f", proteinMax)).append(" г\n")
                    .append("Жири: ").append(String.format("%.1f", fatMin))
                    .append(" - ").append(String.format("%.1f", fatMax)).append(" г\n")
                    .append("Вуглеводи: ").append(String.format("%.1f", carb)).append(" г\n");

            resultArea.setText(sb.toString());
            switchPanel("result");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Помилка введення даних!", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("result.txt")) {
            writer.write(resultArea.getText());
            JOptionPane.showMessageDialog(this, "Результат збережено у result.txt");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Помилка збереження!", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void restart() {
        nameField.setText("");
        ageField.setText("");
        heightField.setText("");
        weightField.setText("");
        genderBox.setSelectedIndex(0);
        activityBox.setSelectedIndex(0);
        calcButton.setEnabled(false);
        calcButton.setBackground(new Color(0, 70, 140));
        switchPanel("input");
    }

    private void switchPanel(String name) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, name);
    }

    // --- Rounded button border ---
    private static class RoundedBorder extends AbstractBorder {
        private int radius;
        public RoundedBorder(int radius) { this.radius = radius; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.WHITE);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

}
