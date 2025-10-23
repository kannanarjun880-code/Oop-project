package view;

import model.User;
import model.Subject;
import model.Quiz;
import model.Result;
import dao.SubjectDAO;
import dao.QuizDAO;
import dao.ResultDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentDashboard extends JFrame {
    private User student;
    private JTabbedPane tabbedPane;
    
    private SubjectDAO subjectDAO;
    private QuizDAO quizDAO;
    private ResultDAO resultDAO;
    
    private JComboBox<String> cmbSubjects;
    private JTable tblQuizzes;
    private DefaultTableModel quizzesTableModel;
    private JButton btnTakeQuiz;
    
    private JTable tblResults;
    private DefaultTableModel resultsTableModel;
    
    public StudentDashboard(User student) {
        this.student = student;
        initializeDAOs();
        initializeUI();
        loadData();
    }
    
    private void initializeDAOs() {
        subjectDAO = new SubjectDAO();
        quizDAO = new QuizDAO();
        resultDAO = new ResultDAO();
    }
    
    private void initializeUI() {
        setTitle("Student Dashboard - Online Quiz System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        createQuizzesTab();
        createResultsTab();
        
        add(tabbedPane);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Student: " + student.getName());
        JMenuItem menuLogout = new JMenuItem("Logout");
        menuLogout.addActionListener(e -> logout());
        menu.add(menuLogout);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    
    private void createQuizzesTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Select Subject:"));
        cmbSubjects = new JComboBox<>();
        cmbSubjects.addItem("All Subjects");
        cmbSubjects.addActionListener(e -> loadQuizzesBySubject());
        filterPanel.add(cmbSubjects);
        
        String[] columns = {"ID", "Title", "Subject", "Description", "Time Limit", "Questions"};
        quizzesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblQuizzes = new JTable(quizzesTableModel);
        JScrollPane scrollPane = new JScrollPane(tblQuizzes);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnTakeQuiz = new JButton("Take Quiz");
        btnTakeQuiz.addActionListener(e -> takeQuiz());
        buttonPanel.add(btnTakeQuiz);
        
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Available Quizzes", panel);
    }
    
    private void createResultsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Quiz", "Subject", "Score", "Total", "Percentage", "Time Taken", "Date"};
        resultsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblResults = new JTable(resultsTableModel);
        JScrollPane scrollPane = new JScrollPane(tblResults);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("My Results", panel);
    }
    
    private void loadData() {
        loadSubjects();
        loadQuizzes();
        loadResults();
    }
    
    private void loadSubjects() {
        cmbSubjects.removeAllItems();
        cmbSubjects.addItem("All Subjects");
        
        List<Subject> subjects = subjectDAO.getAllSubjects();
        for (Subject subject : subjects) {
            cmbSubjects.addItem(subject.getName());
        }
    }
    
    private void loadQuizzes() {
        quizzesTableModel.setRowCount(0);
        List<Quiz> quizzes = quizDAO.getAllQuizzes();
        for (Quiz quiz : quizzes) {
            if (quiz.isActive()) {
                quizzesTableModel.addRow(new Object[]{
                    quiz.getId(),
                    quiz.getTitle(),
                    "Subject ID: " + quiz.getSubjectId(),
                    quiz.getDescription(),
                    quiz.getTimeLimit() + " min",
                    quiz.getTotalQuestions()
                });
            }
        }
    }
    
    private void loadQuizzesBySubject() {
        String selectedSubject = (String) cmbSubjects.getSelectedItem();
        if ("All Subjects".equals(selectedSubject)) {
            loadQuizzes();
            return;
        }
        
        quizzesTableModel.setRowCount(0);
        loadQuizzes();
    }
    
    private void loadResults() {
        resultsTableModel.setRowCount(0);
        List<Result> results = resultDAO.getResultsByStudent(student.getId());
        for (Result result : results) {
            resultsTableModel.addRow(new Object[]{
                result.getId(),
                "Quiz ID: " + result.getQuizId(),
                "Subject ID",
                result.getScore(),
                result.getTotalQuestions(),
                String.format("%.2f%%", result.getPercentage()),
                result.getTimeTaken() + " sec",
                result.getSubmittedAt()
            });
        }
    }
    
    private void takeQuiz() {
        int selectedRow = tblQuizzes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a quiz to take");
            return;
        }
        
        int quizId = (int) quizzesTableModel.getValueAt(selectedRow, 0);
        String quizTitle = (String) quizzesTableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Start quiz: " + quizTitle + "?",
            "Confirm Quiz", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "Quiz interface would open here for quiz ID: " + quizId);
        }
    }
    
    private void logout() {
        dispose();
        new LoginForm().setVisible(true);
    }
}
