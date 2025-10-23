package model;

import java.sql.Timestamp;

public class Result {
    private int id;
    private int studentId;
    private int quizId;
    private int score;
    private int totalQuestions;
    private double percentage;
    private int timeTaken;
    private Timestamp submittedAt;
    
    public Result() {}
    
    public Result(int id, int studentId, int quizId, int score, int totalQuestions, 
                  double percentage, int timeTaken, Timestamp submittedAt) {
        this.id = id;
        this.studentId = studentId;
        this.quizId = quizId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.timeTaken = timeTaken;
        this.submittedAt = submittedAt;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }
    
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    
    public int getTimeTaken() { return timeTaken; }
    public void setTimeTaken(int timeTaken) { this.timeTaken = timeTaken; }
    
    public Timestamp getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Timestamp submittedAt) { this.submittedAt = submittedAt; }
}
