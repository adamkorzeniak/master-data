package com.adamkorzeniak.masterdata.template;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.qwertyP.model.Qwerty;
import com.adamkorzeniak.masterdata.features.qwertyP.repository.QwertyRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QwertyServiceImpl implements QwertyService {

    private final QwertyRepository qwertyRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public QwertyServiceImpl(QwertyRepository qwertyRepository,
                            ApiQueryService apiQueryService) {
        this.qwertyRepository = qwertyRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Qwerty> searchQwertys(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Qwerty> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return qwertyRepository.findAll(spec);
    }

    @Override
    public Optional<Qwerty> findQwertyById(Long id) {
        return qwertyRepository.findById(id);
    }

    @Override
    public Qwerty addQwerty(Qwerty qwerty) {
        qwerty.setId(-1L);
        return qwertyRepository.save(qwerty);
    }

    @Override
    public Qwerty updateQwerty(Long id, Qwerty qwerty) {
        qwerty.setId(id);
        return qwertyRepository.save(qwerty);
    }

    @Override
    public void deleteQwerty(Long id) {
        qwertyRepository.deleteById(id);
    }

    @Override
    public boolean isQwertyExist(Long id) {
        return qwertyRepository.existsById(id);
    }
}
