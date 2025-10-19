package com.library.controller;

import com.library.model.Book;
import com.library.model.User;
import com.library.service.LibraryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryServiceImpl service;

    @PostMapping("/checkoutBook")
    public ResponseEntity<String> checkoutBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(service.checkoutBook(userId, bookId));
    }

    @PostMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(service.returnBook(userId, bookId));
    }

}
