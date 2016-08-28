package edu.uw.service;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import edu.uw.model.PersonEntity;

@Component
public class DataLoaderImpl implements DataLoader {

	private static final Logger logger = LoggerFactory.getLogger(DataLoaderImpl.class);

	@Autowired
	PersonService personService;

	/**
	 * This method process csv file
	 */
	@Override
	public void process(InputStream inputStream) throws Exception {

		FlatFileItemReader<PersonEntity> reader = new FlatFileItemReader<PersonEntity>();
		reader.setResource(new InputStreamResource(inputStream));
		reader.setLinesToSkip(1);

		reader.setLineMapper(new DefaultLineMapper<PersonEntity>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						String token[] = { "PersonId", "Last Name", "First Name", "Affiliations", "BirthDate", "LastEnrolled", "HireDate", "SeparationDate", "Address" };
						setNames(token);
					}
				});
				setFieldSetMapper(new PersonFieldSet());
			}
		});

		reader.open(new ExecutionContext());
		PersonEntity person = null;

		do {

			person = reader.read();
			if (person != null) {
				personService.save(person);
			}

		} while (person != null);

	}

}
