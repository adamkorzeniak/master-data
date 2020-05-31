package com.adamkorzeniak.masterdata.features.book.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.book.model.Book;
import com.adamkorzeniak.masterdata.features.book.repository.BookRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           ApiQueryService apiQueryService) {
        this.bookRepository = bookRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Book> searchBooks(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Book> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return bookRepository.findAll(spec);
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book addBook(Book book) {
        book.setId(-1L);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        book.setId(id);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean isBookExist(Long id) {
        return bookRepository.existsById(id);
    }
}
