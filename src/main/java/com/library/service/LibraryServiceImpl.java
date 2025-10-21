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

    /**
     * Processes a book checkout request for a user.
     * Validates the user and book existence, then updates the book's status.
     *
     * @param userId the ID of the user checking out the book
     * @param bookId the ID of the book to be checked out
     * @return a success message if the operation is successful
     * @throws UserNotFoundException if no user is found with the given ID
     * @throws BookNotFoundException if no book is found with the given ID
     * @throws AlreadyCheckedOutException if the book is already checked out
     */
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

    /**
     * Processes a book return request from a user.
     * Validates the return request and updates the book's status.
     *
     * @param userId the ID of the user returning the book
     * @param bookId the ID of the book being returned
     * @return a success message if the operation is successful
     * @throws BookNotFoundException if no book is found with the given ID
     * @throws InvalidReturnException if the user is not authorized to return the book
     */
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

    /**
     * Saves a user to the database.
     *
     * @param user the user object to be saved
     * @return the saved user entity with generated ID
     */
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    /**
     * Saves a book to the database.
     *
     * @param book the book object to be saved
     * @return the saved book entity with generated ID
     */
    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }
}
