package com.readingtracker.reading.app;

import com.readingtracker.reading.domain.UserBookStatus;
import com.readingtracker.reading.domain.UserBookStatusId;
import com.readingtracker.reading.repo.UserBookStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.readingtracker.common.events.ReadingStatusChangedEvent;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import java.time.Instant;

@Service
public class ReadingCommandService {

    private final UserBookStatusRepository repo;
    private final KafkaTemplate<String, ReadingStatusChangedEvent> kafkaTemplate;

    public ReadingCommandService(UserBookStatusRepository repo,
                                 KafkaTemplate<String, ReadingStatusChangedEvent> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public UserBookStatus start(long userId, long bookId) {
        var id = new UserBookStatusId(userId, bookId);
        var now = Instant.now();

        var entity = repo.findById(id)
                .orElseGet(() -> UserBookStatus.start(userId, bookId, now));

        entity.markInProgress(now);

        return repo.save(entity);
    }

    @Transactional
    public UserBookStatus finish(long userId, long bookId) {
        var id = new UserBookStatusId(userId, bookId);
        var now = Instant.now();

        var entity = repo.findById(id)
                .orElseGet(() -> UserBookStatus.start(userId, bookId, now));

        entity.markDone(now);

        var saved = repo.save(entity);

        var event = new ReadingStatusChangedEvent(
                UUID.randomUUID().toString(),
                "reading.status_changed",
                now,
                userId,
                bookId,
                saved.getStatus().name()
        );

        // key strategy: bookId (keeps ordering per book)
        kafkaTemplate.send("reading-events", String.valueOf(bookId), event);

        return saved;
    }
}
