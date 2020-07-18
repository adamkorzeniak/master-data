package com.adamkorzeniak.masterdata.entity.book.repository;

import com.adamkorzeniak.masterdata.entity.book.model.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTagRepository extends JpaRepository<BookTag, Long>, JpaSpecificationExecutor<BookTag> {
}
