package com.library.service;

import com.library.exception.AlreadyCheckedOutException;
import com.library.exception.BookNotFoundException;
import com.library.exception.InvalidReturnException;
import com.library.exception.UserNotFoundException;
import com.library.model.Book;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LibraryServiceImpl service;

    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Alice");

        book = new Book();
        book.setId(10L);
        book.setTitle("Spring Boot Fundamentals");
        book.setAuthor("Robert C. Martin");
        book.setAvailable(true);
    }

    @Test
    void test_checkoutBook_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

        String result = service.checkoutBook(1L, 10L);

        assertEquals("Book checked out successfully!", result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void test_checkoutBook_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(UserNotFoundException.class, () ->
                service.checkoutBook(1L, 10L));

        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    void test_checkoutBook_BookNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(10L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(BookNotFoundException.class, () ->
                service.checkoutBook(1L, 10L));

        assertTrue(ex.getMessage().contains("Book not found"));
    }

    @Test
    void test_checkoutBook_AlreadyCheckedOut() {
        book.setAvailable(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

        Exception ex = assertThrows(AlreadyCheckedOutException.class, () ->
                service.checkoutBook(1L, 10L));

        assertTrue(ex.getMessage().contains("already checked out"));
    }

    @Test
    void test_returnBook_Success() {
        book.setAvailable(false);
        book.setCheckedOutBy(user);
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

        String result = service.returnBook(1L, 10L);

        assertEquals("Book returned successfully!", result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void test_returnBook_BookNotFound() {
        when(bookRepository.findById(10L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(BookNotFoundException.class, () ->
                service.returnBook(1L, 10L));

        assertTrue(ex.getMessage().contains("Book not found"));
    }

    @Test
    void test_returnBook_InvalidReturn() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setName("Bob");

        book.setCheckedOutBy(anotherUser);
        book.setAvailable(false);

        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

        Exception ex = assertThrows(InvalidReturnException.class, () ->
                service.returnBook(1L, 10L));

        assertTrue(ex.getMessage().contains("not authorized"));
    }
}