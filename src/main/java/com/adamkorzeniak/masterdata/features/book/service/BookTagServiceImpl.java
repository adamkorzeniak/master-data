package com.adamkorzeniak.masterdata.features.book.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.book.model.BookTag;
import com.adamkorzeniak.masterdata.features.book.repository.BookTagRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookTagServiceImpl implements BookTagService {

    private final BookTagRepository bookTagRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public BookTagServiceImpl(BookTagRepository bookTagRepository,
                              ApiQueryService apiQueryService) {
        this.bookTagRepository = bookTagRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<BookTag> searchBookTags(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<BookTag> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return bookTagRepository.findAll(spec);
    }

    @Override
    public Optional<BookTag> findBookTagById(Long id) {
        return bookTagRepository.findById(id);
    }

    @Override
    public BookTag addBookTag(BookTag bookTag) {
        bookTag.setId(-1L);
        return bookTagRepository.save(bookTag);
    }

    @Override
    public BookTag updateBookTag(Long id, BookTag bookTag) {
        bookTag.setId(id);
        return bookTagRepository.save(bookTag);
    }

    @Override
    public void deleteBookTag(Long id) {
        bookTagRepository.deleteById(id);
    }

    @Override
    public boolean isBookTagExist(Long id) {
        return bookTagRepository.existsById(id);
    }
}
