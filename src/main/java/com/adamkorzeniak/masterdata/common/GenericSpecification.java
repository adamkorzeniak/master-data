package com.adamkorzeniak.masterdata.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.adamkorzeniak.masterdata.exception.FilterNotSupportedException;

public class GenericSpecification<T> implements Specification<T> {

	private List<FilterParameter> filters = new ArrayList<>();

	public GenericSpecification(List<FilterParameter> filters) {
		this.filters = filters;
	}

	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

		Predicate p = cb.conjunction();

		for (FilterParameter filter : filters) {
			switch (filter.getFunction()) {
			case SEARCH:
				p.getExpressions().add(cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%"));
				break;
			case MATCH:
				p.getExpressions().add(cb.equal(root.get(filter.getField()), filter.getValue()));
				break;
			case MIN:
				p.getExpressions().add(cb.ge(root.get(filter.getField()), Double.parseDouble(filter.getValue())));
				break;
			case MAX:
				p.getExpressions().add(cb.le(root.get(filter.getField()), Double.parseDouble(filter.getValue())));
				break;
			case EXIST:
				boolean exist = Boolean.parseBoolean(filter.getValue());
				if(exist) {
					p.getExpressions().add(cb.isNotNull(root.get(filter.getField())));
				} else {
					p.getExpressions().add(cb.isNull(root.get(filter.getField())));
				}
				break;
			case ORDER_ASC:
				cq.orderBy(cb.asc(root.get(filter.getField())));
				break;
			case ORDER_DESC:
				cq.orderBy(cb.desc(root.get(filter.getField())));
				break;
			default:
				throw new FilterNotSupportedException(filter.getFunction());
			}
		}

		return p;
	}
}