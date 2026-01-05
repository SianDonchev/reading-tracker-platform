package com.readingtracker.reading.repo;

import com.readingtracker.reading.domain.ReadingStatus;
import com.readingtracker.reading.domain.UserBookStatus;
import com.readingtracker.reading.domain.UserBookStatusId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookStatusRepository extends JpaRepository<UserBookStatus, UserBookStatusId> {

    List<UserBookStatus> findByIdBookIdAndStatus(Long bookId, ReadingStatus status);

    List<UserBookStatus> findByIdBookId(Long bookId);
}
