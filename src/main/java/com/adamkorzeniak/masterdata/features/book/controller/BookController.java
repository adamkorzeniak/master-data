package com.adamkorzeniak.masterdata.features.book.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.book.model.Book;
import com.adamkorzeniak.masterdata.features.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Book")
public class BookController {

    private static final String BOOK_RESOURCE_NAME = "Book";

    private final BookService bookService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public BookController(BookService bookService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.bookService = bookService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of books with 200 OK.
     * <p>
     * If there are no books it returns empty list with 204 No Content
     */
    @GetMapping("/books")
    public ResponseEntity<List<Map<String, Object>>> findBooks(@RequestParam Map<String, String> allRequestParams) {
        List<Book> books = bookService.searchBooks(allRequestParams);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(books, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns book with given id with 200 OK.
     * <p>
     * If book with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> findBookById(@PathVariable("bookId") Long bookId) {
        Optional<Book> book = bookService.findBookById(bookId);
        if (book.isEmpty()) {
            throw new NotFoundException(BOOK_RESOURCE_NAME, bookId);
        }
        return new ResponseEntity<>(book.get(), HttpStatus.OK);
    }

    /**
     * Creates a book in database.
     * Returns created book with 201 Created.
     * <p>
     * If provided book data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody @Valid Book book) {
        Book newBook = bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    /**
     * Updates a book with given id in database. Returns updated book with 200 OK.
     * <p>
     * If book with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided book data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody @Valid Book book, @PathVariable Long bookId) {
        boolean exists = bookService.isBookExist(bookId);
        if (!exists) {
            throw new NotFoundException(BOOK_RESOURCE_NAME, bookId);
        }
        Book newBook = bookService.updateBook(bookId, book);
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    /**
     * Deletes a book with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If book with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        boolean exists = bookService.isBookExist(bookId);
        if (!exists) {
            throw new NotFoundException(BOOK_RESOURCE_NAME, bookId);
        }
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
