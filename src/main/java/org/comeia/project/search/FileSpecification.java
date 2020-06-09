package org.comeia.project.search;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.comeia.project.domain.File;
import org.comeia.project.util.FilterUtil;
import org.springframework.data.jpa.domain.Specification;

public class FileSpecification {

	public static Specification<File> listAllByCriteria(List<SearchCriteria> criterias) {
		return (root, query, builder) -> {
			List<Predicate> predicates = addPredicates(criterias, root, builder);
			predicates.add(builder.equal(root.<Boolean> get("deleted"), false));
			return FilterUtil.andTogether(predicates, builder);
		};
	}
	
	private static List<Predicate> addPredicates(
			List<SearchCriteria> criterias, Root<File> root,
			CriteriaBuilder builder) {
		
		List<Predicate> predicates = new ArrayList<>();
		for (SearchCriteria criteria : criterias) {
			addPredicate(root, builder, predicates, criteria);
		}
		return predicates;
	}
	
	private static void addPredicate(Root<File> root, CriteriaBuilder builder, 
			List<Predicate> predicates, SearchCriteria criteria) {

		if ( criteria.getKey().equalsIgnoreCase("Name") ) {
			predicates.add(builder.like(
					builder.lower(root.<String> get( criteria.getKey())), 
					criteria.getValue().toString().toLowerCase()));
			return;
		}
		
	}
}