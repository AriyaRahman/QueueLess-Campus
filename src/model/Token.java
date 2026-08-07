package model;

import java.time.LocalDateTime;

/**
 * Represents a single queue ticket issued to a student for a given service.
 */
public class Token {
    private final String tokenId;
    private final String serviceId;
    private final String studentId;
    private final int tokenNumber;
    private TokenStatus status;
    private final LocalDateTime issueTime;
    private LocalDateTime calledTime;
    private LocalDateTime completedTime;

    public Token(String tokenId, String serviceId, String studentId, int tokenNumber,
                 TokenStatus status, LocalDateTime issueTime, LocalDateTime calledTime,
                 LocalDateTime completedTime) {
        this.tokenId = tokenId;
        this.serviceId = serviceId;
        this.studentId = studentId;
        this.tokenNumber = tokenNumber;
        this.status = status;
        this.issueTime = issueTime;
        this.calledTime = calledTime;
        this.completedTime = completedTime;
    }

    public String getTokenId() { return tokenId; }
    public String getServiceId() { return serviceId; }
    public String getStudentId() { return studentId; }
    public int getTokenNumber() { return tokenNumber; }
    public TokenStatus getStatus() { return status; }
    public LocalDateTime getIssueTime() { return issueTime; }
    public LocalDateTime getCalledTime() { return calledTime; }
    public LocalDateTime getCompletedTime() { return completedTime; }

    public void setStatus(TokenStatus status) { this.status = status; }
    public void setCalledTime(LocalDateTime calledTime) { this.calledTime = calledTime; }
    public void setCompletedTime(LocalDateTime completedTime) { this.completedTime = completedTime; }

    @Override
    public String toString() {
        return "#" + tokenNumber + " (" + status + ")";
    }

    /** Tokens are identified purely by their tokenId, regardless of which fields have since changed. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        return tokenId.equals(((Token) o).tokenId);
    }

    @Override
    public int hashCode() {
        return tokenId.hashCode();
    }
}
