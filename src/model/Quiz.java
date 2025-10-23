package model;

public class Quiz {
    private int id;
    private int subjectId;
    private String title;
    private String description;
    private int timeLimit;
    private int totalQuestions;
    private boolean isActive;
    private int createdBy;
    
    public Quiz() {}
    
    public Quiz(int id, int subjectId, String title, String description, 
                int timeLimit, int totalQuestions, boolean isActive, int createdBy) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.timeLimit = timeLimit;
        this.totalQuestions = totalQuestions;
        this.isActive = isActive;
        this.createdBy = createdBy;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getTimeLimit() { return timeLimit; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }
    
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
}
