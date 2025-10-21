package com.library.controller;

import com.library.service.LibraryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryServiceImpl service;

    /**
     * Handles the HTTP POST request to check out a book for a user.
     *
     * @param userId the ID of the user who wants to check out the book
     * @param bookId the ID of the book to be checked out
     * @return ResponseEntity containing a success message if the operation is successful
     */
    @PostMapping("/checkoutBook")
    public ResponseEntity<String> checkoutBook(
            @RequestParam Long userId, 
            @RequestParam Long bookId) {
        return ResponseEntity.ok(service.checkoutBook(userId, bookId));
    }

    /**
     * Handles the HTTP POST request to return a book that was checked out by a user.
     *
     * @param userId the ID of the user who is returning the book
     * @param bookId the ID of the book being returned
     * @return ResponseEntity containing a success message if the operation is successful
     */
    @PostMapping("/returnBook")
    public ResponseEntity<String> returnBook(
            @RequestParam Long userId, 
            @RequestParam Long bookId) {
        return ResponseEntity.ok(service.returnBook(userId, bookId));
    }

}
