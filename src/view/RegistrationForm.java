package view;

import model.Student;
import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationForm extends JDialog {
    private JTextField txtName, txtEmail;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegister, btnCancel;
    private UserDAO userDAO;
    private LoginForm parent;
    
    public RegistrationForm(LoginForm parent) {
        super(parent, "Student Registration", true);
        this.parent = parent;
        userDAO = new UserDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitle = new JLabel("Student Registration", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        JLabel lblName = new JLabel("Full Name:");
        txtName = new JTextField();
        
        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        
        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();
        
        JLabel lblConfirmPassword = new JLabel("Confirm Password:");
        txtConfirmPassword = new JPasswordField();
        
        btnRegister = new JButton("Register");
        btnCancel = new JButton("Cancel");
        
        formPanel.add(lblName);
        formPanel.add(txtName);
        formPanel.add(lblEmail);
        formPanel.add(txtEmail);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);
        formPanel.add(lblConfirmPassword);
        formPanel.add(txtConfirmPassword);
        formPanel.add(btnRegister);
        formPanel.add(btnCancel);
        
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerStudent();
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void registerStudent() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (userDAO.emailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email already registered", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password);
        
        if (userDAO.registerStudent(student)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
