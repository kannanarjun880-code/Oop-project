package dao;

import model.Quiz;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    
    public boolean addQuiz(Quiz quiz) {
        String sql = "INSERT INTO quizzes (subject_id, title, description, time_limit, total_questions, created_by) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quiz.getSubjectId());
            stmt.setString(2, quiz.getTitle());
            stmt.setString(3, quiz.getDescription());
            stmt.setInt(4, quiz.getTimeLimit());
            stmt.setInt(5, quiz.getTotalQuestions());
            stmt.setInt(6, quiz.getCreatedBy());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Quiz> getQuizzesBySubject(int subjectId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.*, s.name as subject_name FROM quizzes q " +
                    "JOIN subjects s ON q.subject_id = s.id " +
                    "WHERE q.subject_id = ? AND q.is_active = TRUE";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt("id"));
                quiz.setSubjectId(rs.getInt("subject_id"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setTimeLimit(rs.getInt("time_limit"));
                quiz.setTotalQuestions(rs.getInt("total_questions"));
                quiz.setActive(rs.getBoolean("is_active"));
                quiz.setCreatedBy(rs.getInt("created_by"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }
    
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.*, s.name as subject_name FROM quizzes q " +
                    "JOIN subjects s ON q.subject_id = s.id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt("id"));
                quiz.setSubjectId(rs.getInt("subject_id"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setTimeLimit(rs.getInt("time_limit"));
                quiz.setTotalQuestions(rs.getInt("total_questions"));
                quiz.setActive(rs.getBoolean("is_active"));
                quiz.setCreatedBy(rs.getInt("created_by"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }
    
    public boolean deleteQuiz(int quizId) {
        String sql = "DELETE FROM quizzes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quizId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
