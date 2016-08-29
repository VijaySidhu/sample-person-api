package edu.uw.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;

import edu.uw.exceptions.SystemException;
import edu.uw.model.AddressEntity;
import edu.uw.model.AffiliationEntity;
import edu.uw.model.PersonEntity;

public class PersonFieldSet implements FieldSetMapper<PersonEntity> {

	private static final Logger logger = LoggerFactory.getLogger(PersonFieldSet.class);

	@Override
	public PersonEntity mapFieldSet(FieldSet fieldSet) throws BindException {

		PersonEntity person = new PersonEntity();
		try {
			person.setPersonId(fieldSet.readLong(0));
			person.setFirstName(fieldSet.readString(1));
			person.setLastName(fieldSet.readString(2));
			if (fieldSet.readString(4) != null) {
				person.setBirthDate(new SimpleDateFormat("dd-MMM-yy").parse(fieldSet.readString(4)));
			}

			String[] yt = fieldSet.readString(5).split(" ");
			if (yt != null && yt.length == 2) {
				person.setLastEnrolledTerm(yt[0]);
				person.setLastEnrolledYear(new Integer(yt[1]));
			}

			if (StringUtils.isNoneBlank(fieldSet.readString(6))) {
				person.setHireDate(new SimpleDateFormat("dd-MMM-yy").parse(fieldSet.readString(6)));
			}

			if (StringUtils.isNoneBlank(fieldSet.readString(7))) {
				person.setSeparationDate(new SimpleDateFormat("dd-MMM-yy").parse(fieldSet.readString(7)));
			}

			String adds[] = fieldSet.readString(8).split(",");
			if (adds != null && adds.length == 3) {

				AddressEntity address = new AddressEntity();
				address.setLine1(adds[0]);
				address.setCity(adds[1]);
				String sz[] = adds[2].trim().split(" ");
				address.setState(sz[0]);
				address.setZip(new Integer(sz[1]));
				address.setPerson(person);
				person.setAddress(address);
			} else if (adds != null && adds.length == 4) {
				AddressEntity address = new AddressEntity();
				address.setLine1(adds[0]);
				address.setLine2(adds[1]);
				address.setCity(adds[2]);
				String sz[] = adds[3].trim().split(" ");
				address.setState(sz[0]);
				address.setZip(new Integer(sz[1]));
				address.setPerson(person);
				person.setAddress(address);

			}

			Set<AffiliationEntity> affiliations = new HashSet<AffiliationEntity>();

			String[] affs = fieldSet.readString(3).split(",");
			for (String aff : affs) {
				AffiliationEntity affiliation = new AffiliationEntity();
				affiliation.setName(aff);
				affiliation.setPerson(person);
				affiliations.add(affiliation);
			}

			person.setAffiliatons(affiliations);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			SystemException syException = new SystemException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal System Error");
			throw new RuntimeException(syException);
		}

		return person;
	}

}
