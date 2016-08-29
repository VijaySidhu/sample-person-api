package edu.uw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.uw.model.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>{

}
