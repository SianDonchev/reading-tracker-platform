package com.readingtracker.reading.app;

import com.readingtracker.reading.domain.UserBookStatus;
import com.readingtracker.reading.domain.UserBookStatusId;
import com.readingtracker.reading.repo.UserBookStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ReadingCommandService {

    private final UserBookStatusRepository repo;

    public ReadingCommandService(UserBookStatusRepository repo) {
        this.repo = repo;
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

        return repo.save(entity);
    }
}
