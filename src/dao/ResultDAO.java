package dao;

import model.Result;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    
    public boolean saveResult(Result result) {
        String sql = "INSERT INTO results (student_id, quiz_id, score, total_questions, percentage, time_taken) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, result.getStudentId());
            stmt.setInt(2, result.getQuizId());
            stmt.setInt(3, result.getScore());
            stmt.setInt(4, result.getTotalQuestions());
            stmt.setDouble(5, result.getPercentage());
            stmt.setInt(6, result.getTimeTaken());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Result> getResultsByStudent(int studentId) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, q.title as quiz_title, s.name as subject_name " +
                    "FROM results r " +
                    "JOIN quizzes q ON r.quiz_id = q.id " +
                    "JOIN subjects s ON q.subject_id = s.id " +
                    "WHERE r.student_id = ? ORDER BY r.submitted_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Result result = new Result();
                result.setId(rs.getInt("id"));
                result.setStudentId(rs.getInt("student_id"));
                result.setQuizId(rs.getInt("quiz_id"));
                result.setScore(rs.getInt("score"));
                result.setTotalQuestions(rs.getInt("total_questions"));
                result.setPercentage(rs.getDouble("percentage"));
                result.setTimeTaken(rs.getInt("time_taken"));
                result.setSubmittedAt(rs.getTimestamp("submitted_at"));
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
    
    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, u.name as student_name, q.title as quiz_title, s.name as subject_name " +
                    "FROM results r " +
                    "JOIN users u ON r.student_id = u.id " +
                    "JOIN quizzes q ON r.quiz_id = q.id " +
                    "JOIN subjects s ON q.subject_id = s.id " +
                    "ORDER BY r.submitted_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Result result = new Result();
                result.setId(rs.getInt("id"));
                result.setStudentId(rs.getInt("student_id"));
                result.setQuizId(rs.getInt("quiz_id"));
                result.setScore(rs.getInt("score"));
                result.setTotalQuestions(rs.getInt("total_questions"));
                result.setPercentage(rs.getDouble("percentage"));
                result.setTimeTaken(rs.getInt("time_taken"));
                result.setSubmittedAt(rs.getTimestamp("submitted_at"));
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
