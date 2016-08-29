package edu.uw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.uw.model.AffiliationEntity;

@Repository
public interface AffiliationRepository extends JpaRepository<AffiliationEntity, Long>{

}
