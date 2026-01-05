package com.readingtracker.reading.api;

import com.readingtracker.reading.app.ReadingCommandService;
import com.readingtracker.reading.domain.UserBookStatus;
import com.readingtracker.reading.repo.UserBookStatusRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ReadingController {

    private final ReadingCommandService commandService;
    private final UserBookStatusRepository repo;

    public ReadingController(ReadingCommandService commandService, UserBookStatusRepository repo) {
        this.commandService = commandService;
        this.repo = repo;
    }

    @PostMapping("/users/{userId}/books/{bookId}/start")
    public UserBookStatus start(@PathVariable("userId") long userId,
                                @PathVariable("bookId") long bookId) {
        return commandService.start(userId, bookId);
    }

    @PostMapping("/users/{userId}/books/{bookId}/finish")
    public UserBookStatus finish(@PathVariable("userId") long userId,
                                 @PathVariable("bookId") long bookId) {
        return commandService.finish(userId, bookId);
    }

    @GetMapping("/books/{bookId}/readers")
    public List<UserBookStatus> readers(@PathVariable("bookId") long bookId) {
        return repo.findByIdBookId(bookId);
    }

}
