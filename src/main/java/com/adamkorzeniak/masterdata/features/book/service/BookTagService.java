package com.adamkorzeniak.masterdata.features.book.service;

import com.adamkorzeniak.masterdata.features.book.model.BookTag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookTagService {

    List<BookTag> searchBookTags(Map<String, String> allRequestParams);

    Optional<BookTag> findBookTagById(Long id);

    BookTag addBookTag(BookTag bookTag);

    BookTag updateBookTag(Long id, BookTag bookTag);

    void deleteBookTag(Long id);

    boolean isBookTagExist(Long id);
}
