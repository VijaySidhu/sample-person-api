package edu.uw.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.uw.dao.PersonRepository;
import edu.uw.model.AddressEntity;
import edu.uw.model.AffiliationEntity;
import edu.uw.model.PersonEntity;
import edu.uw.ui.model.Address;
import edu.uw.ui.model.People;
import edu.uw.ui.model.PeopleResult;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository repository;

	@Override
	public PersonEntity save(PersonEntity person) throws Exception {
		return repository.save(person);
	}

	@Override
	public PersonEntity save(People person) throws Exception {
		return repository.save(uiTOEntity(person));
	}

	@Override
	@Transactional(readOnly = true)
	public PeopleResult findAll(String affiliation, Date activeOn, Integer zip, int page, int size) throws Exception {

		List<People> peoples = new ArrayList<People>();

		PageRequest pageRequest = new PageRequest(page - 1, size);
		Page<PersonEntity> pageResult = null;
		Specification<PersonEntity> spec = PersonSpecifications.createSpec(affiliation, activeOn, zip);
		if (spec != null) {
			pageResult = repository.findAll(spec, pageRequest);
		} else {
			pageResult = repository.findAll(pageRequest);
		}

		pageResult.getContent().stream().forEach(person -> {
			peoples.add(entityToUi(person));

		});

		return new PeopleResult(peoples, pageResult.getTotalElements(), pageResult.getNumber() + 1, pageResult.getSize());
	}

	@Override
	@Transactional(readOnly = true)
	public People findOne(Long personId) throws Exception {
		return entityToUi(repository.findOne(PersonSpecifications.hasPersonId(personId)));
	}

	private PersonEntity uiTOEntity(People people) {

		PersonEntity entity = null;

		try {

			Date bd = null;
			Date hd = null;
			Date sd = null;
			if (StringUtils.isNoneBlank(people.getBirthDate())) {
				bd = new SimpleDateFormat("dd-MMM-yy").parse(people.getBirthDate());
			}

			if (StringUtils.isNoneBlank(people.getHireDate())) {
				hd = new SimpleDateFormat("dd-MMM-yy").parse(people.getHireDate());
			}

			if (StringUtils.isNoneBlank(people.getSeparationDate())) {
				sd = new SimpleDateFormat("dd-MMM-yy").parse(people.getSeparationDate());
			}

			entity = new PersonEntity(people.getPersonId(), people.getFirstName(), people.getLastName(), bd, people.getLastEnrolledTerm(), people.getLastEnrolledYear(), hd, sd);

			Set<AffiliationEntity> affs = new HashSet<AffiliationEntity>();
			if (people.getAffiliations() != null) {
				for (String aff : people.getAffiliations()) {
					affs.add(new AffiliationEntity(aff, entity));
				}
			}

			AddressEntity ae = new AddressEntity(people.getAddress().getLine1(), people.getAddress().getLine2(), people.getAddress().getCity(), people.getAddress().getState(), people.getAddress().getZip());
			ae.setPerson(entity);
			entity.setAddress(ae);
			entity.setAffiliatons(affs);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return entity;

	}

	private People entityToUi(PersonEntity entity) {
		if (null == entity) {
			return null;
		}
		List<String> affs = new ArrayList<String>();
		entity.getAffiliatons().stream().forEach(aff -> {
			affs.add(aff.getName());
		});
		Address address = null;
		if (entity.getAddress() != null) {
			address = new Address(entity.getAddress().getLine1(), entity.getAddress().getLine2(), entity.getAddress().getCity(), entity.getAddress().getState(), entity.getAddress().getZip());
		}

		String bd = null;
		String hd = null;
		String sd = null;
		if (entity.getBirthDate() != null) {
			bd = new SimpleDateFormat("dd-MMM-yy").format(entity.getBirthDate());
		}

		if (entity.getHireDate() != null) {
			hd = new SimpleDateFormat("dd-MMM-yy").format(entity.getHireDate());
		}

		if (entity.getSeparationDate() != null) {
			sd = new SimpleDateFormat("dd-MMM-yy").format(entity.getSeparationDate());
		}

		People people = new People(entity.getPersonId(), entity.getFirstName(), entity.getLastName(), affs, bd, entity.getLastEnrolledTerm(), entity.getLastEnrolledYear(), hd, sd, address);
		return people;

	}

}
