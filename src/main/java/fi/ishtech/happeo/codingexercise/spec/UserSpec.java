package fi.ishtech.happeo.codingexercise.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import fi.ishtech.happeo.codingexercise.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 *
 * @author Muneer Ahmed Syed
 */
@Data
@AllArgsConstructor(staticName = "of")
public class UserSpec implements Specification<User> {

	private static final long serialVersionUID = 7348846648436755987L;

	private Long organisationId;
	private Boolean isActive;

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = toPredicateList(root, cb);

		return cb.and(predicates.stream().toArray(Predicate[]::new));
	}

	private List<Predicate> toPredicateList(Root<User> root, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		addPredicateEq(predicates, root, cb, this.getOrganisationId(), "organisationId");
		addPredicateEq(predicates, root, cb, this.getIsActive(), "isActive");

		return predicates;
	}

	private void addPredicateEq(List<Predicate> predicates, Root<User> root, CriteriaBuilder cb, Number attribValue,
			String attribName) {
		if (attribValue != null) {
			predicates.add(cb.equal(root.get(attribName), attribValue));
		}
	}

	private void addPredicateEq(List<Predicate> predicates, Root<User> root, CriteriaBuilder cb, Boolean attribValue,
			String attribName) {
		if (attribValue != null) {
			predicates.add(cb.equal(root.get(attribName), attribValue));
		}
	}
}
