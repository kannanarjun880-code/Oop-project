package view;

import model.User;
import model.Subject;
import model.Quiz;
import model.Question;
import model.Student;
import model.Result;
import dao.SubjectDAO;
import dao.QuizDAO;
import dao.QuestionDAO;
import dao.UserDAO;
import dao.ResultDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminDashboard extends JFrame {
    private User admin;
    private JTabbedPane tabbedPane;
    
    private SubjectDAO subjectDAO;
    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;
    private UserDAO userDAO;
    private ResultDAO resultDAO;
    
    private JTable tblSubjects;
    private DefaultTableModel subjectsTableModel;
    private JButton btnAddSubject, btnDeleteSubject;
    
    private JTable tblQuizzes;
    private DefaultTableModel quizzesTableModel;
    private JButton btnAddQuiz, btnDeleteQuiz;
    
    private JTable tblQuestions;
    private DefaultTableModel questionsTableModel;
    private JButton btnAddQuestion, btnEditQuestion, btnDeleteQuestion;
    
    private JTable tblStudents;
    private DefaultTableModel studentsTableModel;
    
    private JTable tblResults;
    private DefaultTableModel resultsTableModel;
    
    public AdminDashboard(User admin) {
        this.admin = admin;
        initializeDAOs();
        initializeUI();
        loadData();
    }
    
    private void initializeDAOs() {
        subjectDAO = new SubjectDAO();
        quizDAO = new QuizDAO();
        questionDAO = new QuestionDAO();
        userDAO = new UserDAO();
        resultDAO = new ResultDAO();
    }
    
    private void initializeUI() {
        setTitle("Admin Dashboard - Online Quiz System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        createSubjectsTab();
        createQuizzesTab();
        createQuestionsTab();
        createStudentsTab();
        createResultsTab();
        
        add(tabbedPane);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Admin: " + admin.getName());
        JMenuItem menuLogout = new JMenuItem("Logout");
        menuLogout.addActionListener(e -> logout());
        menu.add(menuLogout);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    
    private void createSubjectsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Name", "Description", "Created By"};
        subjectsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSubjects = new JTable(subjectsTableModel);
        JScrollPane scrollPane = new JScrollPane(tblSubjects);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAddSubject = new JButton("Add Subject");
        btnDeleteSubject = new JButton("Delete Subject");
        
        btnAddSubject.addActionListener(e -> showAddSubjectDialog());
        btnDeleteSubject.addActionListener(e -> deleteSubject());
        
        buttonPanel.add(btnAddSubject);
        buttonPanel.add(btnDeleteSubject);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Subjects", panel);
    }
    
    private void createQuizzesTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Title", "Subject", "Time Limit", "Questions", "Active"};
        quizzesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblQuizzes = new JTable(quizzesTableModel);
        JScrollPane scrollPane = new JScrollPane(tblQuizzes);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAddQuiz = new JButton("Add Quiz");
        btnDeleteQuiz = new JButton("Delete Quiz");
        
        btnAddQuiz.addActionListener(e -> showAddQuizDialog());
        btnDeleteQuiz.addActionListener(e -> deleteQuiz());
        
        buttonPanel.add(btnAddQuiz);
        buttonPanel.add(btnDeleteQuiz);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Quizzes", panel);
    }
    
    private void createQuestionsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Question", "Option A", "Option B", "Option C", "Option D", "Correct", "Marks"};
        questionsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblQuestions = new JTable(questionsTableModel);
        JScrollPane scrollPane = new JScrollPane(tblQuestions);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAddQuestion = new JButton("Add Question");
        btnEditQuestion = new JButton("Edit Question");
        btnDeleteQuestion = new JButton("Delete Question");
        
        btnAddQuestion.addActionListener(e -> showAddQuestionDialog());
        btnEditQuestion.addActionListener(e -> editQuestion());
        btnDeleteQuestion.addActionListener(e -> deleteQuestion());
        
        buttonPanel.add(btnAddQuestion);
        buttonPanel.add(btnEditQuestion);
        buttonPanel.add(btnDeleteQuestion);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Questions", panel);
    }
    
    private void createStudentsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Name", "Email"};
        studentsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblStudents = new JTable(studentsTableModel);
        JScrollPane scrollPane = new JScrollPane(tblStudents);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Students", panel);
    }
    
    private void createResultsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Student", "Quiz", "Score", "Total", "Percentage", "Time Taken", "Date"};
        resultsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblResults = new JTable(resultsTableModel);
        JScrollPane scrollPane = new JScrollPane(tblResults);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Results", panel);
    }
    
    private void loadData() {
        loadSubjects();
        loadQuizzes();
        loadQuestions();
        loadStudents();
        loadResults();
    }
    
    private void loadSubjects() {
        subjectsTableModel.setRowCount(0);
        List<Subject> subjects = subjectDAO.getAllSubjects();
        for (Subject subject : subjects) {
            subjectsTableModel.addRow(new Object[]{
                subject.getId(),
                subject.getName(),
                subject.getDescription(),
                subject.getCreatedBy()
            });
        }
    }
    
    private void loadQuizzes() {
        quizzesTableModel.setRowCount(0);
        List<Quiz> quizzes = quizDAO.getAllQuizzes();
        for (Quiz quiz : quizzes) {
            quizzesTableModel.addRow(new Object[]{
                quiz.getId(),
                quiz.getTitle(),
                "Subject ID: " + quiz.getSubjectId(),
                quiz.getTimeLimit() + " min",
                quiz.getTotalQuestions(),
                quiz.isActive() ? "Yes" : "No"
            });
        }
    }
    
    private void loadQuestions() {
        questionsTableModel.setRowCount(0);
        List<Quiz> quizzes = quizDAO.getAllQuizzes();
        for (Quiz quiz : quizzes) {
            List<Question> questions = questionDAO.getQuestionsByQuiz(quiz.getId());
            for (Question question : questions) {
                String questionText = question.getQuestionText();
                if (questionText.length() > 50) {
                    questionText = questionText.substring(0, 47) + "...";
                }
                
                questionsTableModel.addRow(new Object[]{
                    question.getId(),
                    questionText,
                    question.getOptionA(),
                    question.getOptionB(),
                    question.getOptionC(),
                    question.getOptionD(),
                    question.getCorrectOption(),
                    question.getMarks()
                });
            }
        }
    }
    
    private void loadStudents() {
        studentsTableModel.setRowCount(0);
        List<Student> students = userDAO.getAllStudents();
        for (Student student : students) {
            studentsTableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getEmail()
            });
        }
    }
    
    private void loadResults() {
        resultsTableModel.setRowCount(0);
        List<Result> results = resultDAO.getAllResults();
        for (Result result : results) {
            resultsTableModel.addRow(new Object[]{
                result.getId(),
                "Student ID: " + result.getStudentId(),
                "Quiz ID: " + result.getQuizId(),
                result.getScore(),
                result.getTotalQuestions(),
                String.format("%.2f%%", result.getPercentage()),
                result.getTimeTaken() + " sec",
                result.getSubmittedAt()
            });
        }
    }
    
    private void showAddSubjectDialog() {
        JTextField txtName = new JTextField();
        JTextField txtDescription = new JTextField();
        
        Object[] message = {
            "Name:", txtName,
            "Description:", txtDescription
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Add Subject", 
                                                 JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String description = txtDescription.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Subject name is required", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Subject subject = new Subject();
            subject.setName(name);
            subject.setDescription(description);
            subject.setCreatedBy(admin.getId());
            
            if (subjectDAO.addSubject(subject)) {
                JOptionPane.showMessageDialog(this, "Subject added successfully");
                loadSubjects();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add subject");
            }
        }
    }
    
    private void deleteSubject() {
        int selectedRow = tblSubjects.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a subject to delete");
            return;
        }
        
        int subjectId = (int) subjectsTableModel.getValueAt(selectedRow, 0);
        String subjectName = (String) subjectsTableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete subject: " + subjectName + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (subjectDAO.deleteSubject(subjectId)) {
                JOptionPane.showMessageDialog(this, "Subject deleted successfully");
                loadSubjects();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete subject");
            }
        }
    }
    
    private void showAddQuizDialog() {
        JOptionPane.showMessageDialog(this, "Add Quiz functionality would go here");
    }
    
    private void deleteQuiz() {
        int selectedRow = tblQuizzes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a quiz to delete");
            return;
        }
        
        int quizId = (int) quizzesTableModel.getValueAt(selectedRow, 0);
        String quizTitle = (String) quizzesTableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete quiz: " + quizTitle + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (quizDAO.deleteQuiz(quizId)) {
                JOptionPane.showMessageDialog(this, "Quiz deleted successfully");
                loadQuizzes();
                loadQuestions();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete quiz");
            }
        }
    }
    
    private void showAddQuestionDialog() {
        JOptionPane.showMessageDialog(this, "Add Question functionality would go here");
    }
    
    private void editQuestion() {
        JOptionPane.showMessageDialog(this, "Edit Question functionality would go here");
    }
    
    private void deleteQuestion() {
        int selectedRow = tblQuestions.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to delete");
            return;
        }
        
        int questionId = (int) questionsTableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this question?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (questionDAO.deleteQuestion(questionId)) {
                JOptionPane.showMessageDialog(this, "Question deleted successfully");
                loadQuestions();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete question");
            }
        }
    }
    
    private void logout() {
        dispose();
        new LoginForm().setVisible(true);
    }
}
