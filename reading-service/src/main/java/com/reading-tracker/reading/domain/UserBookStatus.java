package com.readingtracker.reading.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user_book_status")
public class UserBookStatus {

    @EmbeddedId
    private UserBookStatusId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReadingStatus status;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    protected UserBookStatus() { }

    private UserBookStatus(UserBookStatusId id, ReadingStatus status, Instant startedAt, Instant finishedAt) {
        this.id = id;
        this.status = status;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public static UserBookStatus start(Long userId, Long bookId, Instant now) {
        return new UserBookStatus(new UserBookStatusId(userId, bookId), ReadingStatus.IN_PROGRESS, now, null);
    }

    public void markInProgress(Instant now) {
        if (this.status == ReadingStatus.DONE) {
            // v1 decision: if DONE, we don't revert back to IN_PROGRESS
            return;
        }
        this.status = ReadingStatus.IN_PROGRESS;
        if (this.startedAt == null) this.startedAt = now;
        this.finishedAt = null;
    }

    public void markDone(Instant now) {
        if (this.status == ReadingStatus.DONE) {
            // idempotent
            return;
        }
        this.status = ReadingStatus.DONE;
        if (this.startedAt == null) this.startedAt = now;
        this.finishedAt = now;
    }

    public UserBookStatusId getId() { return id; }
    public Long getUserId() { return id.getUserId(); }
    public Long getBookId() { return id.getBookId(); }
    public ReadingStatus getStatus() { return status; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getFinishedAt() { return finishedAt; }
}
