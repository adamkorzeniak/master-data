package com.adamkorzeniak.masterdata.features.book.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.book.model.BookTag;
import com.adamkorzeniak.masterdata.features.book.service.BookTagService;
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
public class BookTagController {

    private static final String BOOK_TAG_RESOURCE_NAME = "BookTag";

    private final BookTagService bookTagService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public BookTagController(BookTagService bookTagService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.bookTagService = bookTagService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of bookTags with 200 OK.
     * <p>
     * If there are no bookTags it returns empty list with 204 No Content
     */
    @GetMapping("/tags")
    public ResponseEntity<List<Map<String, Object>>> findBookTags(@RequestParam Map<String, String> allRequestParams) {
        List<BookTag> bookTags = bookTagService.searchBookTags(allRequestParams);
        if (bookTags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(bookTags, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns bookTag with given id with 200 OK.
     * <p>
     * If bookTag with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/tags/{bookTagId}")
    public ResponseEntity<BookTag> findBookTagById(@PathVariable("bookTagId") Long bookTagId) {
        Optional<BookTag> bookTag = bookTagService.findBookTagById(bookTagId);
        if (bookTag.isEmpty()) {
            throw new NotFoundException(BOOK_TAG_RESOURCE_NAME, bookTagId);
        }
        return new ResponseEntity<>(bookTag.get(), HttpStatus.OK);
    }

    /**
     * Creates a bookTag in database.
     * Returns created bookTag with 201 Created.
     * <p>
     * If provided bookTag data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/tags")
    public ResponseEntity<BookTag> addBookTag(@RequestBody @Valid BookTag bookTag) {
        BookTag newBookTag = bookTagService.addBookTag(bookTag);
        return new ResponseEntity<>(newBookTag, HttpStatus.CREATED);
    }

    /**
     * Updates a bookTag with given id in database. Returns updated bookTag with 200 OK.
     * <p>
     * If bookTag with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided bookTag data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/tags/{bookTagId}")
    public ResponseEntity<BookTag> updateBookTag(@RequestBody @Valid BookTag bookTag, @PathVariable Long bookTagId) {
        boolean exists = bookTagService.isBookTagExist(bookTagId);
        if (!exists) {
            throw new NotFoundException(BOOK_TAG_RESOURCE_NAME, bookTagId);
        }
        BookTag newBookTag = bookTagService.updateBookTag(bookTagId, bookTag);
        return new ResponseEntity<>(newBookTag, HttpStatus.OK);
    }

    /**
     * Deletes a bookTag with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If bookTag with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/tags/{bookTagId}")
    public ResponseEntity<Void> deleteBookTag(@PathVariable Long bookTagId) {
        boolean exists = bookTagService.isBookTagExist(bookTagId);
        if (!exists) {
            throw new NotFoundException(BOOK_TAG_RESOURCE_NAME, bookTagId);
        }
        bookTagService.deleteBookTag(bookTagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
