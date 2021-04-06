package service.failedSignIn;

import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service("failedSignInHandler")
public final class FailedSignInHandlerImpl implements FailedSignInHandler {

    private final ConcurrentMap<String, Attempt> nameToAttempt = new ConcurrentHashMap<>();

    /**
     * returns null in case you can retry now, otherwise -
     * returns {@link java.time.LocalTime} object which represents,
     * how much you must wait before next attempt.
     */
    @Override
    @Nullable
    public LocalTime addAttempt(String userName) {
        Attempt attempt = new Attempt();
        Attempt existing = nameToAttempt.putIfAbsent(userName, attempt);
        if (existing == null) {
            return attempt.next();
        }
        return existing.next();
    }

    @Override
    public void reactOnSuccess(String userName) {
        nameToAttempt.remove(userName);
    }

    @Nullable
    @Override
    public LocalTime whenCanRetry(String userName) {
        Attempt attempt = nameToAttempt.get(userName);
        if (attempt == null) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        return attempt.timeBeforeRetry(now);
    }

    private static class Attempt {

        private static final Map<Integer, LocalTime> MISTAKES_TO_PUNISHMENT = new HashMap<>();
        private static final LocalTime BIGGEST_PUNISHMENT = LocalTime.of(23, 59, 59);
        private final Object lock = new Object();
        private int count = 0;
        private LocalDateTime timeOfCreation = LocalDateTime.now();

        static {
            MISTAKES_TO_PUNISHMENT.put(5, LocalTime.of(0, 5));
            MISTAKES_TO_PUNISHMENT.put(10, LocalTime.of(0, 45));
            MISTAKES_TO_PUNISHMENT.put(15, LocalTime.of(2, 0, 0));
            MISTAKES_TO_PUNISHMENT.put(20, BIGGEST_PUNISHMENT);
        }

        /**
         * returns null in case you can retry now, otherwise -
         * returns {@link java.time.LocalTime} object which represents,
         * how much you must wait before next attempt.
         */
        public LocalTime next() {
            LocalDateTime now = LocalDateTime.now();
            synchronized (lock) {
                if (count < 5) {
                    count++;
                    timeOfCreation = now;
                    return null;
                }
                LocalDateTime timeOfCreationPlusPunishment = timeOfCreationPlusPunishment();
                if (timeOfCreationPlusPunishment.isAfter(now)) {
                    if (count < Integer.MAX_VALUE) {
                        count++;
                    }
                    timeOfCreation = now;
                    return null;
                }
                return timeBeforeRetry(timeOfCreationPlusPunishment, now);
            }
        }

        private LocalDateTime timeOfCreationPlusPunishment() {
            synchronized (lock) {
                int mistakesKey = count - (count % 5); // it is int
                if(mistakesKey < 5){
                    return timeOfCreation;
                }
                LocalTime punishment = MISTAKES_TO_PUNISHMENT.getOrDefault(mistakesKey, BIGGEST_PUNISHMENT);
                return timeOfCreation
                        .plusHours(punishment.getHour())
                        .plusMinutes(punishment.getMinute());
            }
        }

        private LocalTime timeBeforeRetry(LocalDateTime timeOfCreationPlusPunishment,
                                          LocalDateTime now) {
            if (timeOfCreationPlusPunishment.isAfter(now)) {
                return timeOfCreationPlusPunishment
                        .minusHours(now.getHour())
                        .minusMinutes(now.getMinute())
                        .minusSeconds(now.getSecond()).toLocalTime();
            }
            return null;
        }

        public LocalTime timeBeforeRetry(LocalDateTime now) {
            return timeBeforeRetry(timeOfCreationPlusPunishment(), now);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Attempt attempt = (Attempt) o;
            return count == attempt.count &&
                    Objects.equals(lock, attempt.lock) &&
                    Objects.equals(timeOfCreation, attempt.timeOfCreation);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lock, count, timeOfCreation);
        }

        @Override
        public String toString() {
            return "Attempt{" +
                    "lock=" + lock +
                    ", count=" + count +
                    ", timeOfCreation=" + timeOfCreation +
                    '}';
        }
    }

}
