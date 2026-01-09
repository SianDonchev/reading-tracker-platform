package com.readingtracker.common.events;

import java.time.Instant;

public record ReadingStatusChangedEvent(
        String eventId,
        String eventType,
        Instant occurredAt,
        long userId,
        long bookId,
        String status
) {}
