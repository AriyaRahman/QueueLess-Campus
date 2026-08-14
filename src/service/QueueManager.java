package service;

import model.Token;
import model.TokenStatus;
import storage.TokenRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Keeps the in-memory queue for every service (Map of serviceId -> Queue of Token)
 * in sync with the CSV-backed TokenRepository. Uses a Queue so tokens are served
 * in first-come first-served order.
 */
public class QueueManager {

    private final TokenRepository tokenRepository;
    private final Map<String, Queue<Token>> liveQueues = new HashMap<>();
    private final Map<String, Token> currentlyServing = new HashMap<>();

    public QueueManager(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /** Lazily loads the waiting tokens for a service into memory, preserving issue order. */
    private Queue<Token> getQueue(String serviceId) {
        return liveQueues.computeIfAbsent(serviceId, id -> {
            Queue<Token> queue = new LinkedList<>();
            List<Token> waiting = new ArrayList<>();
            for (Token t : tokenRepository.findByService(id)) {
                if (t.getStatus() == TokenStatus.WAITING) {
                    waiting.add(t);
                }
            }
            waiting.sort(Comparator.comparing(Token::getIssueTime));
            queue.addAll(waiting);
            return queue;
        });
    }

    /** Issues a new token for the student at the end of the given service's queue. */
    public Token joinQueue(String studentId, String serviceId) {
        // load queue first, otherwise the token could get added twice on first join
        Queue<Token> queue = getQueue(serviceId);

        int nextNumber = nextTokenNumberForToday(serviceId);
        String tokenId = "TKN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Token token = new Token(tokenId, serviceId, studentId, nextNumber,
                TokenStatus.WAITING, LocalDateTime.now(), null, null);
        tokenRepository.add(token);
        queue.add(token);
        return token;
    }

    private int nextTokenNumberForToday(String serviceId) {
        LocalDate today = LocalDate.now();
        int max = 0;
        for (Token t : tokenRepository.findByService(serviceId)) {
            if (t.getIssueTime().toLocalDate().equals(today)) {
                max = Math.max(max, t.getTokenNumber());
            }
        }
        return max + 1;
    }

    /** Calls the next waiting student for a service. Returns empty if the queue is empty. */
    public Optional<Token> callNext(String serviceId) {
        Queue<Token> queue = getQueue(serviceId);
        Token next = queue.poll();
        if (next == null) {
            return Optional.empty();
        }
        next.setStatus(TokenStatus.CALLED);
        next.setCalledTime(LocalDateTime.now());
        tokenRepository.update(next);
        currentlyServing.put(serviceId, next);
        return Optional.of(next);
    }

    /** Marks the token currently being served for a service as completed. */
    public Optional<Token> completeCurrent(String serviceId) {
        Token current = currentlyServing.get(serviceId);
        if (current == null) {
            return Optional.empty();
        }
        current.setStatus(TokenStatus.COMPLETED);
        current.setCompletedTime(LocalDateTime.now());
        tokenRepository.update(current);
        currentlyServing.remove(serviceId);
        return Optional.of(current);
    }

    /** Cancels a still-waiting token, removing it from the live queue. */
    public boolean cancelToken(String tokenId) {
        Optional<Token> tokenOpt = tokenRepository.findById(tokenId);
        if (tokenOpt.isEmpty() || tokenOpt.get().getStatus() != TokenStatus.WAITING) {
            return false;
        }
        Token token = tokenOpt.get();
        // compare by ID, not object equality, since repository returns a new object
        getQueue(token.getServiceId()).removeIf(t -> t.getTokenId().equals(tokenId));
        token.setStatus(TokenStatus.CANCELLED);
        tokenRepository.update(token);
        return true;
    }

    /** Returns the students currently waiting for a service, in queue order. */
    public List<Token> getQueueSnapshot(String serviceId) {
        return new ArrayList<>(getQueue(serviceId));
    }

    /** Returns the token currently being served for a service, if any. */
    public Optional<Token> getCurrentlyServing(String serviceId) {
        return Optional.ofNullable(currentlyServing.get(serviceId));
    }

    /** 1-based position of a student's token within its service queue, or -1 if not waiting. */
    public int getPosition(String tokenId, String serviceId) {
        int position = 1;
        for (Token t : getQueue(serviceId)) {
            if (t.getTokenId().equals(tokenId)) {
                return position;
            }
            position++;
        }
        return -1;
    }

    public List<Token> getTokensForStudent(String studentId) {
        return tokenRepository.findByStudent(studentId);
    }
}
