package edu.uw.service;

import java.io.InputStream;
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

	@Autowired
	PersonService personService;

	/**
	 * This method process csv file
	 */
	@Override
	public void process(InputStream inputStream) {

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

			try {
				person = reader.read();
				if (person != null) {
					personService.save(person);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (person != null);

	}

}
