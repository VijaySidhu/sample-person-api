package edu.uw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import edu.uw.model.PersonEntity;

@Repository
public interface PersonRepository extends  JpaRepository<PersonEntity, Long> , JpaSpecificationExecutor<PersonEntity>{


}
