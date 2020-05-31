package com.adamkorzeniak.masterdata.features.book.service;

import com.adamkorzeniak.masterdata.features.book.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {

    List<Book> searchBooks(Map<String, String> allRequestParams);

    Optional<Book> findBookById(Long id);

    Book addBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    boolean isBookExist(Long id);
}
