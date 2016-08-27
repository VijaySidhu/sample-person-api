package edu.uw.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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

	/**
	 * This method allows client to upload csv file to db
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/upload")
	public @ResponseBody String uploadPeople(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
		dataLoader.process(file.getInputStream());
		return "Sucesfully uploaded";
	}

	/**
	 * This method creates new person and assign unique identifier on each post
	 * 
	 * @param people
	 * @return
	 */
	@PostMapping
	public @ResponseBody String createPeople(@RequestBody People people) {
		personService.save(people);
		return "Sucesfully Created";
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
	public @ResponseBody PeopleResult findAll(@RequestParam(value = "affiliation", required = false) String affiliation, @RequestParam(value = "activeOn", required = false) String activeOnStr, @RequestParam(value = "zip", required = false) Integer zip, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "size", required = false, defaultValue = "20") int size) throws ParseException {

		Date activeOn = null;
		if (StringUtils.isNoneBlank(activeOnStr)) {
			activeOn = new SimpleDateFormat("dd-MMM-yy").parse(activeOnStr);
		}
		return personService.findAll(affiliation, activeOn, zip, page, size);
	}

	/**
	 * This resource allow client to retrieve information of person by person id
	 * @param personId
	 * @return
	 */
	@GetMapping("/{id}")
	public @ResponseBody People findOne(@PathVariable(value = "id") Long personId) {
		return personService.findOne(personId);
	}

}
