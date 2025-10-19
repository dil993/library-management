package com.library.service;

import org.springframework.stereotype.Component;

@Component
public interface LibraryService {
     String checkoutBook(Long userId, Long bookId);
     String returnBook(Long userId, Long bookId);
}
