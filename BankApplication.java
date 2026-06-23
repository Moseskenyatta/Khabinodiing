import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;

public class BankApplication extends JFrame {

    private JTextField txtFirstName, txtLastName, txtNIN, txtJointNIN, txtEmail, txtConfirmEmail, txtPhone, txtDeposit;
    private JPasswordField txtPIN, txtConfirmPIN;
    private JComboBox<Integer> cbYear, cbDay;
    private JComboBox<String> cbMonth;
    private JComboBox<String> cbAccountType, cbBranch;
    private JTextArea txtSummary;
    private JButton btnSubmit, btnReset;
    
    private JLabel errFirst, errLast, errNIN, errJointNIN, errEmail, errConfirmEmail, errPhone, errDeposit, errPIN, errConfirmPIN, errDOB;
    private JLabel lblJointNIN; 

    private final String[] months = {
        "January", "February", "March", "April", "May", "June", 
        "July", "August", "September", "October", "November", "December"
    };

    private static final String DB_URL = "jdbc:ucanaccess://FirstBankUG.accdb";

    public BankApplication() {
        setTitle("First Bank Uganda - New Account Opening Form");
        setSize(750, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 51, 102));
        JLabel lblHeader = new JLabel("FIRST BANK UGANDA - OOP IMPLEMENTATION");
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(lblHeader);
        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        addFormRow(formPanel, "First Name:", txtFirstName = new JTextField(15), errFirst = createErrLabel(), gbc, row++);
        addFormRow(formPanel, "Last Name:", txtLastName = new JTextField(15), errLast = createErrLabel(), gbc, row++);
        addFormRow(formPanel, "National ID (NIN):", txtNIN = new JTextField(15), errNIN = createErrLabel(), gbc, row++);
        
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        cbYear = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= currentYear - 100; i--) cbYear.addItem(i);
        cbMonth = new JComboBox<>(months);
        cbDay = new JComboBox<>();
        updateDays();
        dobPanel.add(new JLabel("Year:")); dobPanel.add(cbYear);
        dobPanel.add(new JLabel("Month:")); dobPanel.add(cbMonth);
        dobPanel.add(new JLabel("Day:")); dobPanel.add(cbDay);
        
        errDOB = createErrLabel();
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; formPanel.add(dobPanel, gbc);
        gbc.gridx = 2; formPanel.add(errDOB, gbc);
        row++;

        cbYear.addActionListener(e -> updateDays());
        cbMonth.addActionListener(e -> updateDays());

        addFormRow(formPanel, "Email:", txtEmail = new JTextField(15), gbc, row++, errEmail = createErrLabel());
        addFormRow(formPanel, "Confirm Email:", txtConfirmEmail = new JTextField(15), gbc, row++, errConfirmEmail = createErrLabel());
        addFormRow(formPanel, "Phone Number:", txtPhone = new JTextField(15), gbc, row++, errPhone = createErrLabel());

        String[] accountTypes = {"Savings", "Current", "Fixed Deposit", "Student", "Joint"};
        cbAccountType = new JComboBox<>(accountTypes);
        addFormRow(formPanel, "Account Type:", cbAccountType, gbc, row++, null);
        
        String[] branches = {"Kampala", "Gulu", "Mbarara", "Jinja", "Mbale"};
        cbBranch = new JComboBox<>(branches);
        addFormRow(formPanel, "Branch:", cbBranch, gbc, row++, null);

        lblJointNIN = new JLabel("Secondary NIN (Joint):");
        txtJointNIN = new JTextField(15);
        errJointNIN = createErrLabel();
        lblJointNIN.setVisible(false); txtJointNIN.setVisible(false); errJointNIN.setVisible(false);
        
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(lblJointNIN, gbc);
        gbc.gridx = 1; formPanel.add(txtJointNIN, gbc);
        gbc.gridx = 2; formPanel.add(errJointNIN, gbc);
        row++;

        cbAccountType.addActionListener(e -> {
            boolean isJoint = "Joint".equals(cbAccountType.getSelectedItem());
            lblJointNIN.setVisible(isJoint); txtJointNIN.setVisible(isJoint); errJointNIN.setVisible(isJoint);
            formPanel.revalidate(); formPanel.repaint();
        });

        addFormRow(formPanel, "Opening Deposit (UGX):", txtDeposit = new JTextField(15), gbc, row++, errDeposit = createErrLabel());
        addFormRow(formPanel, "PIN (4-6 Digits):", txtPIN = new JPasswordField(15), gbc, row++, errPIN = createErrLabel());
        addFormRow(formPanel, "Confirm PIN:", txtConfirmPIN = new JPasswordField(15), gbc, row++, errConfirmPIN = createErrLabel());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnSubmit = new JButton("Submit Polymorphic Application");
        btnReset = new JButton("Reset Form");
        btnPanel.add(btnSubmit); btnPanel.add(btnReset);
        
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        formPanel.add(btnPanel, gbc);
        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        JLabel lblSummary = new JLabel("Account Summary is Below:");
        lblSummary.setFont(new Font("Arial", Font.BOLD, 12));
        bottomPanel.add(lblSummary, BorderLayout.NORTH);

        txtSummary = new JTextArea(6, 40);
        txtSummary.setEditable(false);
        txtSummary.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtSummary.setBackground(new Color(245, 245, 245));
        bottomPanel.add(new JScrollPane(txtSummary), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnSubmit.addActionListener(e -> handleSubmit());
        btnReset.addActionListener(e -> handleReset());
    }

    private JLabel createErrLabel() {
        JLabel lbl = new JLabel("");
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Arial", Font.PLAIN, 11));
        return lbl;
    }

    private void addFormRow(JPanel panel, String labelText, Component comp, GridBagConstraints gbc, int row, JLabel errLabel) {
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1; panel.add(comp, gbc);
        gbc.gridx = 2; if(errLabel != null) panel.add(errLabel, gbc);
    }

    private void addFormRow(JPanel panel, String labelText, Component comp, JLabel errLabel, GridBagConstraints gbc, int row) {
        addFormRow(panel, labelText, comp, gbc, row, errLabel);
    }

    private void updateDays() {
        if (cbYear.getSelectedItem() == null || cbMonth.getSelectedItem() == null) return;
        int year = (int) cbYear.getSelectedItem();
        int monthIndex = cbMonth.getSelectedIndex();
        Object currentSelectedDay = cbDay.getSelectedItem();

        int maxDays = 31;
        if (monthIndex == 3 || monthIndex == 5 || monthIndex == 8 || monthIndex == 10) {
            maxDays = 30;
        } else if (monthIndex == 1) {
            maxDays = ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
        }

        cbDay.removeAllItems();
        for (int i = 1; i <= maxDays; i++) cbDay.addItem(i);
        if (currentSelectedDay != null && (int) currentSelectedDay <= maxDays) {
            cbDay.setSelectedItem(currentSelectedDay);
        }
    }

    private void clearInlineErrors() {
        errFirst.setText(""); errLast.setText(""); errNIN.setText(""); errJointNIN.setText("");
        errEmail.setText(""); errConfirmEmail.setText(""); errPhone.setText("");
        errDeposit.setText(""); errPIN.setText(""); errConfirmPIN.setText(""); errDOB.setText("");
    }

    private void handleSubmit() {
        clearInlineErrors();
        ArrayList<String> errorsList = new ArrayList<>();

        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String nin = txtNIN.getText().trim();
        String jointNin = txtJointNIN.getText().trim();
        String email = txtEmail.getText().trim();
        String confirmEmail = txtConfirmEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String depositRaw = txtDeposit.getText().trim();
        String pin = new String(txtPIN.getPassword()).trim();
        String confirmPin = new String(txtConfirmPIN.getPassword()).trim();
        String accountType = (String) cbAccountType.getSelectedItem();
        String branch = (String) cbBranch.getSelectedItem();

        // Standard Validations
        if (!firstName.matches("^[a-zA-Z]{2,30}$")) {
            errFirst.setText("Invalid name (2-30 letters).");
            errorsList.add("First Name error.");
        }
        if (!lastName.matches("^[a-zA-Z]{2,30}$")) {
            errLast.setText("Invalid name (2-30 letters).");
            errorsList.add("Last Name error.");
        }
        if (!nin.matches("^[A-Z0-9]{14}$")) {
            errNIN.setText("Must be 14 Upper Alphanumeric.");
            errorsList.add("Primary NIN syntax error.");
        }

        int selectedYear = (int) cbYear.getSelectedItem();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int age = currentYear - selectedYear;
        if (age < 18 || age > 75) {
            errDOB.setText("Age must be 18-75.");
            errorsList.add("Global Age bounds restricted to 18-75.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errEmail.setText("Invalid Format.");
            errorsList.add("Email configuration error.");
        }
        if (!email.equalsIgnoreCase(confirmEmail)) {
            errConfirmEmail.setText("Mismatch.");
            errorsList.add("Email verification mismatch.");
        }
        if (!phone.matches("^\\+256\\d{9}$")) {
            errPhone.setText("Use +256XXXXXXXXX");
            errorsList.add("Ugandan phone layout violation.");
        }

        double depositAmount = 0;
        try {
            depositAmount = Double.parseDouble(depositRaw);
        } catch (NumberFormatException e) {
            errDeposit.setText("Must be numeric.");
            errorsList.add("Deposit format configuration fault.");
        }

        // --- POLYMORPHIC OBJECT VALIDATION ENFORCEMENT ---
        Account userAccount = null;
        if (errorsList.isEmpty()) {
            switch (accountType) {
                case "Savings":       userAccount = new SavingsAccount(firstName, lastName, nin, email, phone, depositAmount); break;
                case "Current":       userAccount = new CurrentAccount(firstName, lastName, nin, email, phone, depositAmount); break;
                case "Fixed Deposit": userAccount = new FixedDepositAccount(firstName, lastName, nin, email, phone, depositAmount); break;
                case "Student":       userAccount = new StudentAccount(firstName, lastName, nin, email, phone, depositAmount); break;
                case "Joint":         userAccount = new JointAccount(firstName, lastName, nin, email, phone, depositAmount); break;
            }

            try {
                // Polymorphic method calling strategy
                userAccount.validateExtraRules(age, jointNin);
                if (depositAmount < userAccount.getMinimumDeposit()) {
                    errDeposit.setText("Below minimum.");
                    errorsList.add(String.format("Minimum deposit for %s is UGX %,.0f.", accountType, userAccount.getMinimumDeposit()));
                }
            } catch (IllegalArgumentException ex) {
                if("Student".equals(accountType)) errDOB.setText("Age must be 18-25.");
                if("Joint".equals(accountType)) errJointNIN.setText("Invalid Joint NIN.");
                errorsList.add(ex.getMessage());
            }
        }

        if (!pin.matches("^\\d{4,6}$") || pin.matches("^(\\d)\\1+$")) {
            errPIN.setText("Invalid layout/identical digits.");
            errorsList.add("PIN structure violation.");
        }
        if (!pin.equals(confirmPin)) {
            errConfirmPIN.setText("Mismatch.");
            errorsList.add("PIN matching configuration variant error.");
        }

        if (!errorsList.isEmpty()) {
            StringBuilder errorReport = new StringBuilder("The following errors occurred:\n\n");
            for (String errMsg : errorsList) errorReport.append("- ").append(errMsg).append("\n");
            JOptionPane.showMessageDialog(this, errorReport.toString(), "Application Error Summary", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Branch mapping setup
        String branchCode = "KLA";
        switch (branch) {
            case "Kampala": branchCode = "KLA"; break;
            case "Gulu":    branchCode = "GUL"; break;
            case "Mbarara": branchCode = "MBR"; break;
            case "Jinja":   branchCode = "JNJ"; break;
            case "Mbale":   branchCode = "MBL"; break;
        }

        String generatedAccNo = "";
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String countQuery = "SELECT COUNT(*) FROM Accounts WHERE branch = ? AND year_opened = ?";
            int currentSequenceCount = 1;
            try (PreparedStatement checkStmt = conn.prepareStatement(countQuery)) {
                checkStmt.setString(1, branch);
                checkStmt.setInt(2, currentYear);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) currentSequenceCount = rs.getInt(1) + 1;
            }

            generatedAccNo = String.format("%s-%d-%06d", branchCode, currentYear, currentSequenceCount);

            String insertQuery = "INSERT INTO Accounts (account_number, first_name, last_name, nin, joint_nin, email, phone, account_type, branch, year_opened, deposit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, generatedAccNo);
                insertStmt.setString(2, firstName);
                insertStmt.setString(3, lastName);
                insertStmt.setString(4, nin);
                insertStmt.setString(5, "Joint".equals(accountType) ? jointNin : null);
                insertStmt.setString(6, email);
                insertStmt.setString(7, phone);
                insertStmt.setString(8, accountType);
                insertStmt.setString(9, branch);
                insertStmt.setInt(10, currentYear);
                insertStmt.setDouble(11, depositAmount);
                insertStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Failure:\n" + ex.getMessage(), "Persistence Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dobFormatted = String.format("%d-%02d-%02d", selectedYear, cbMonth.getSelectedIndex() + 1, (int)cbDay.getSelectedItem());
        String cleanRecordOutput = String.format(
            "ACC: %s | %s %s | %s | %s | DOB %s | %s | Deposit %,.0f | %s",
            generatedAccNo, firstName, lastName, accountType, branch, dobFormatted, phone, depositAmount, email
        );

        txtSummary.setText(cleanRecordOutput);
        JOptionPane.showMessageDialog(this, "Account successfully stored polymorphically!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleReset() {
        txtFirstName.setText(""); txtLastName.setText(""); txtNIN.setText(""); txtJointNIN.setText("");
        txtEmail.setText(""); txtConfirmEmail.setText(""); txtPhone.setText(""); txtDeposit.setText("");
        txtPIN.setText(""); txtConfirmPIN.setText("");
        cbYear.setSelectedIndex(0); cbMonth.setSelectedIndex(0); updateDays();
        cbAccountType.setSelectedIndex(0); cbBranch.setSelectedIndex(0);
        txtSummary.setText(""); clearInlineErrors();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankApplication().setVisible(true));
    }
}