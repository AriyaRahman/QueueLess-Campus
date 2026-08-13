package storage;

import model.Token;
import model.TokenStatus;
import util.CSVExportUtil;
import util.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles loading and saving Token records to tokens.csv.
 * Format: tokenId,serviceId,studentId,tokenNumber,status,issueTime,calledTime,completedTime
 */
public class TokenRepository {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);

    public List<Token> findAll() {
        List<Token> tokens = new ArrayList<>();
        for (String line : CSVExportUtil.readLines(Constants.TOKENS_FILE)) {
            tokens.add(fromLine(line));
        }
        return tokens;
    }

    public List<Token> findByService(String serviceId) {
        List<Token> result = new ArrayList<>();
        for (Token t : findAll()) {
            if (t.getServiceId().equals(serviceId)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Token> findByStudent(String studentId) {
        List<Token> result = new ArrayList<>();
        for (Token t : findAll()) {
            if (t.getStudentId().equals(studentId)) {
                result.add(t);
            }
        }
        return result;
    }

    public Optional<Token> findById(String tokenId) {
        for (Token t : findAll()) {
            if (t.getTokenId().equals(tokenId)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public void add(Token token) {
        CSVExportUtil.appendLine(Constants.TOKENS_FILE, toLine(token));
    }

    public void update(Token token) {
        List<Token> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getTokenId().equals(token.getTokenId())) {
                all.set(i, token);
                break;
            }
        }
        saveAll(all);
    }

    public void saveAll(List<Token> tokens) {
        List<String> lines = new ArrayList<>();
        for (Token t : tokens) {
            lines.add(toLine(t));
        }
        CSVExportUtil.writeLines(Constants.TOKENS_FILE, lines);
    }

    private String toLine(Token t) {
        return String.join(Constants.CSV_DELIMITER,
                t.getTokenId(),
                t.getServiceId(),
                t.getStudentId(),
                String.valueOf(t.getTokenNumber()),
                t.getStatus().name(),
                t.getIssueTime().format(FORMATTER),
                t.getCalledTime() == null ? "" : t.getCalledTime().format(FORMATTER),
                t.getCompletedTime() == null ? "" : t.getCompletedTime().format(FORMATTER));
    }

    private Token fromLine(String line) {
        String[] parts = line.split(Constants.CSV_DELIMITER, -1);
        LocalDateTime calledTime = parts[6].isEmpty() ? null : LocalDateTime.parse(parts[6], FORMATTER);
        LocalDateTime completedTime = parts[7].isEmpty() ? null : LocalDateTime.parse(parts[7], FORMATTER);
        return new Token(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]),
                TokenStatus.valueOf(parts[4]), LocalDateTime.parse(parts[5], FORMATTER),
                calledTime, completedTime);
    }
}
