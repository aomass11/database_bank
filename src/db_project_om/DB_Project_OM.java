package db_project_om;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Project_OM extends JFrame
{
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/om?characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

    private JTextField idField, moneyField, interestRateField, firstNameField, lastNameField, ageField;
    private JComboBox<String> openDay, openMonth, openYear, birthDay, birthMonth, birthYear;
    private JButton saveButton, showButton, searchButton, deleteButton; // เพิ่มปุ่มลบ

    public DB_Project_OM() 
    {
        setTitle("Account Money");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        Dimension textFieldSize = new Dimension(150, 25);

        // ID and Money Fields
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("ID:"), gbc);

        idField = new JTextField();
        idField.setPreferredSize(textFieldSize);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Money:"), gbc);

        moneyField = new JTextField();
        moneyField.setPreferredSize(textFieldSize);
        gbc.gridx = 3;
        panel.add(moneyField, gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("BATH:"), gbc);

        // Annual Interest Rate
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Annual Interest Rate:"), gbc);

        interestRateField = new JTextField();
        interestRateField.setPreferredSize(textFieldSize);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panel.add(interestRateField, gbc);
        gbc.gridwidth = 1;

        // Open Account Date
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Day Open Account:"), gbc);

        openDay = new JComboBox<>(generateNumbers(1, 31));
        openMonth = new JComboBox<>(generateNumbers(1, 12));
        openYear = new JComboBox<>(generateNumbers(1900, 2100));
        JPanel openDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        openDatePanel.add(openDay);
        openDatePanel.add(openMonth);
        openDatePanel.add(openYear);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panel.add(openDatePanel, gbc);
        gbc.gridwidth = 1;

        // First Name and Last Name Fields
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("First Name:"), gbc);
        firstNameField = new JTextField();
        firstNameField.setPreferredSize(textFieldSize);
        gbc.gridx = 1;
        panel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Last Name:"), gbc);
        lastNameField = new JTextField();
        lastNameField.setPreferredSize(textFieldSize);
        gbc.gridx = 1;
        panel.add(lastNameField, gbc);

        // Birth Date
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Birth Date:"), gbc);
        birthDay = new JComboBox<>(generateNumbers(1, 31));
        birthMonth = new JComboBox<>(generateNumbers(1, 12));
        birthYear = new JComboBox<>(generateNumbers(1900, 2100));
        JPanel birthDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        birthDatePanel.add(birthDay);
        birthDatePanel.add(birthMonth);
        birthDatePanel.add(birthYear);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panel.add(birthDatePanel, gbc);
        gbc.gridwidth = 1;

        // Age Field
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Age:"), gbc);
        ageField = new JTextField();
        ageField.setPreferredSize(textFieldSize);
        gbc.gridx = 1;
        panel.add(ageField, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("YEAR"), gbc);

        // Save, Show, Search, and Delete Buttons
        saveButton = new JButton("Save");
        showButton = new JButton("Show");
        searchButton = new JButton("Search"); // สร้างปุ่มค้นหา
        deleteButton = new JButton("Delete"); // สร้างปุ่มลบ

        gbc.gridx = 2; gbc.gridy = 7;
        gbc.gridwidth = 1;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(showButton);
        buttonPanel.add(searchButton); // เพิ่มปุ่มค้นหาลงใน panel
        buttonPanel.add(deleteButton); // เพิ่มปุ่มลบลงใน panel
        panel.add(buttonPanel, gbc);

        // Add ActionListeners
        saveButton.addActionListener(e -> saveData());
        showButton.addActionListener(e -> showData());
        searchButton.addActionListener(e -> searchData()); // เชื่อมต่อ ActionListener ของปุ่มค้นหา
        deleteButton.addActionListener(e -> deleteData()); // เชื่อมต่อ ActionListener ของปุ่มลบ

        add(panel);
    }

    private String[] generateNumbers(int start, int end) {
        String[] numbers = new String[end - start + 1];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = String.valueOf(start + i);
        }
        return numbers;
    }

    private void saveData() {
        String id = idField.getText();
        String money = moneyField.getText();
        String interestRate = interestRateField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String age = ageField.getText();
        
        String openDayStr = (String) openDay.getSelectedItem();
        String openMonthStr = (String) openMonth.getSelectedItem();
        String openYearStr = (String) openYear.getSelectedItem();
        
        String birthDayStr = (String) birthDay.getSelectedItem();
        String birthMonthStr = (String) birthMonth.getSelectedItem();
        String birthYearStr = (String) birthYear.getSelectedItem();

        // Simple validation
        if (id.isEmpty() || money.isEmpty() || interestRate.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Validate that money and interestRate are numbers
            Double.parseDouble(money);
            Double.parseDouble(interestRate);
            Integer.parseInt(age);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Money, Interest Rate, and Age must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO accountform (id, money, annual_interest_rate, first_name, last_name, age, open_day, open_month, open_year, birth_day, birth_month, birth_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, money);
            pstmt.setString(3, interestRate);
            pstmt.setString(4, firstName);
            pstmt.setString(5, lastName);
            pstmt.setString(6, age);
            pstmt.setString(7, openDayStr);
            pstmt.setString(8, openMonthStr);
            pstmt.setString(9, openYearStr);
            pstmt.setString(10, birthDayStr);
            pstmt.setString(11, birthMonthStr);
            pstmt.setString(12, birthYearStr);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data saved successfully to database!");

            // Clear fields after saving
            clearFields();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data to database: " + ex.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        moneyField.setText("");
        interestRateField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        openDay.setSelectedIndex(0);
        openMonth.setSelectedIndex(0);
        openYear.setSelectedIndex(0);
        birthDay.setSelectedIndex(0);
        birthMonth.setSelectedIndex(0);
        birthYear.setSelectedIndex(0);
    }

    private void showData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM accountform";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append("ID: ").append(rs.getString("id"))
                      .append(", Money: ").append(rs.getString("money"))
                      .append(", Interest Rate: ").append(rs.getString("annual_interest_rate"))
                      .append(", Name: ").append(rs.getString("first_name")).append(" ").append(rs.getString("last_name"))
                      .append(", Age: ").append(rs.getString("age"))
                      .append(", Open Account Date: ").append(rs.getString("open_day")).append("/").append(rs.getString("open_month")).append("/").append(rs.getString("open_year"))
                      .append(", Birth Date: ").append(rs.getString("birth_day")).append("/").append(rs.getString("birth_month")).append("/").append(rs.getString("birth_year"))
                      .append("\n");
            }
            JOptionPane.showMessageDialog(this, result.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error retrieving data from database: " + ex.getMessage());
        }
    }

    private void searchData() {
        String id = idField.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM accountform WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                moneyField.setText(rs.getString("money"));
                interestRateField.setText(rs.getString("annual_interest_rate"));
                firstNameField.setText(rs.getString("first_name"));
                lastNameField.setText(rs.getString("last_name"));
                ageField.setText(rs.getString("age"));
                openDay.setSelectedItem(rs.getString("open_day"));
                openMonth.setSelectedItem(rs.getString("open_month"));
                openYear.setSelectedItem(rs.getString("open_year"));
                birthDay.setSelectedItem(rs.getString("birth_day"));
                birthMonth.setSelectedItem(rs.getString("birth_month"));
                birthYear.setSelectedItem(rs.getString("birth_year"));
            } else {
                JOptionPane.showMessageDialog(this, "No account found with ID: " + id);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error retrieving data from database: " + ex.getMessage());
        }
    }

    private void deleteData() {
        String id = idField.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM accountform WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Account deleted successfully!");
                clearFields(); // Clear fields after deletion
            } else {
                JOptionPane.showMessageDialog(this, "No account found with ID: " + id);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting data from database: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DB_Project_OM form = new DB_Project_OM();
            form.setVisible(true);
        });
    }
}