package edu.uw.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import edu.uw.exceptions.NoRecordFoundException;
import edu.uw.exceptions.PersonNotFoundException;
import edu.uw.exceptions.PersonServiceException;
import edu.uw.exceptions.SystemException;
import edu.uw.service.DataLoader;
import edu.uw.service.PersonService;
import edu.uw.ui.model.People;
import edu.uw.ui.model.PeopleResult;

@RestController
@RequestMapping("/people")
public class PeopleResource {

	@Autowired
	DataLoader dataLoader;

	@Autowired
	PersonService personService;

	private static final Logger logger = LoggerFactory.getLogger(PeopleResource.class);

	/**
	 * This method allows client to upload csv file to db returns 201 if
	 * Successful 400 for bad request
	 * 
	 * @param file
	 * @returns
	 * @throws Exception
	 */
	@PostMapping("/upload")
	public @ResponseBody ResponseEntity<String> uploadPeople(@RequestParam(value = "file", required = true) MultipartFile file) throws SystemException {
		ResponseEntity<String> res = null;
		try {
			dataLoader.process(file.getInputStream());
			String body = "File Uploaded Successfully";
			res = new ResponseEntity<String>(body, HttpStatus.CREATED);
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
			throw new SystemException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal System Error");
		}
		return res;
	}

	/**
	 * This method creates new person and assign unique identifier on each post
	 * 
	 * @param people
	 * @param ucb
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public @ResponseBody ResponseEntity<Void> createPeople(@RequestBody People people, UriComponentsBuilder ucb) throws SystemException {
		try {
			personService.save(people);
		} catch (PersonServiceException personServiceExe) {
			logger.error(personServiceExe.getMessage(), personServiceExe);
			throw new SystemException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal System Error");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucb.path("/people/{id}").buildAndExpand(people.getPersonId()).toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/**
	 * This resource allows client to retrieve list of people filtered to single
	 * affiliation and if person has more than one affiliation it displays all
	 * affiliations
	 * 
	 * This resource allows user to filter people who were active on specific
	 * date
	 * 
	 * @param affiliation
	 * @param activeOnStr
	 * @param zip
	 * @param page
	 * @param size
	 * @return
	 * @throws ParseException
	 */

	@GetMapping
	public @ResponseBody PeopleResult findAll(@RequestParam(value = "affiliation", required = false) String affiliation, @RequestParam(value = "activeOn", required = false) String activeOnStr, @RequestParam(value = "zip", required = false) Integer zip, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "size", required = false, defaultValue = "20") int size) throws Exception {
		PeopleResult peopleResult = null;
		Date activeOn = null;
		try {
			if (StringUtils.isNoneBlank(activeOnStr)) {
				activeOn = new SimpleDateFormat("dd-MMM-yy").parse(activeOnStr);
			}
			peopleResult = personService.findAll(affiliation, activeOn, zip, page, size);
		} catch (PersonServiceException personServiceExe) {
			logger.error(personServiceExe.getMessage(), personServiceExe);
			throw new SystemException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal System Error");
		}
		if (null != peopleResult && peopleResult.getTotal() < 1) {
			logger.error("No record found");
			throw new NoRecordFoundException();
		}

		return peopleResult;
	}

	/**
	 * This resource allow client to retrieve information of person by person id
	 * return 404 if requested person not found
	 * 
	 * @param personId
	 * @return
	 */
	@GetMapping("/{id}")
	public @ResponseBody People findOne(@PathVariable(value = "id") Long personId) throws PersonNotFoundException, SystemException {
		People person = null;
		try {
			person = personService.findOne(personId);
		} catch (PersonServiceException personServiceExe) {
			logger.error(personServiceExe.getMessage(), personServiceExe);
			throw new SystemException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal System Error");
		}
		if (null == person) {
			logger.error("Requested person not found");
			throw new PersonNotFoundException();
		}
		return person;
	}

}
