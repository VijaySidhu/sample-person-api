package edu.uw.service;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import edu.uw.model.PersonEntity;

public class PersonSpecifications {

	public static Specification<PersonEntity> hasAffiliations(String affiliation) {
		return new Specification<PersonEntity>() {
			@Override
			public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				return cb.equal(root.join("affiliatons").get("name"), affiliation);
			}
		};
	}

	public static Specification<PersonEntity> hasZip(int zip) {
		return new Specification<PersonEntity>() {
			@Override
			public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.join("address").get("zip"), zip);
			}
		};
	}

	public static Specification<PersonEntity> hasActive(Date date) {
		return new Specification<PersonEntity>() {
			@Override
			public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("hireDate"), date);
			}
		};
	}

	public static Specification<PersonEntity> hasPersonId(Long personId) {
		return new Specification<PersonEntity>() {
			@Override
			public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("personId"), personId);
			}
		};
	}

	public static Specification<PersonEntity> createSpec(String affiliation, Date activeOn, Integer zip) {
		Specifications<PersonEntity> spec = null;

		if (StringUtils.isNoneBlank(affiliation)) {
			spec = Specifications.where(hasAffiliations(affiliation));
		}

		if (activeOn != null) {

			if (spec == null) {
				spec = Specifications.where(hasActive(activeOn));
			} else {
				spec = spec.and(Specifications.where(hasActive(activeOn)));
			}

		}

		if (zip != null) {

			if (spec == null) {
				spec = Specifications.where(hasZip(zip));
			} else {
				spec = spec.and(Specifications.where(hasZip(zip)));
			}

		}

		return spec;

	}

	public static Specification<PersonEntity> createSpecLastName(String LastName) {
		Specifications<PersonEntity> spec = null;

		if (StringUtils.isNoneBlank(LastName)) {
			spec = Specifications.where(hasLastName(LastName));
		}

		return spec;

	}

	public static Specification<PersonEntity> hasLastName(String LastName) {
		return new Specification<PersonEntity>() {
			@Override
			public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				return cb.equal(root.join("LastName").get("lastName"), LastName);
			}
		};
	}

}
