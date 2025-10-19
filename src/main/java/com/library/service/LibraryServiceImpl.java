package com.library.service;

import com.library.exception.AlreadyCheckedOutException;
import com.library.exception.BookNotFoundException;
import com.library.exception.InvalidReturnException;
import com.library.exception.UserNotFoundException;
import com.library.model.Book;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public String checkoutBook(Long userId, Long bookId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (!book.isAvailable()) {
            throw new AlreadyCheckedOutException("Book " + book.getTitle() + "' is already checked out.");
        }

        book.setAvailable(false);
        book.setCheckedOutBy(user);
        bookRepo.save(book);

        return "Book checked out successfully!";
    }

    @Override
    public String returnBook(Long userId, Long bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found to return"));

        if (book.getCheckedOutBy() == null || !book.getCheckedOutBy().getId().equals(userId)) {
            throw new InvalidReturnException("This user is not authorized to return this book.");
        }

        book.setCheckedOutBy(null);
        book.setAvailable(true);
        bookRepo.save(book);

        return "Book returned successfully!";
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }
}
