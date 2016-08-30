package edu.uw.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.uw.model.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long>, JpaSpecificationExecutor<PersonEntity> {

	public final static String FIND_BY_LAST_NAME = "SELECT * " + "FROM Person" + "WHERE lastName = :lastName";

	public List<PersonEntity> findByLastName(@Param("lastName") String lastName);

}
