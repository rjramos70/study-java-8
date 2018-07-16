package br.com.java8.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.java8.study.entities.DefaultPerson;
import br.com.java8.study.entities.Jhon;
import br.com.java8.study.entities.Mary;
import br.com.java8.study.entities.Paul;
import br.com.java8.study.interfaces.PersonFactory;
import br.com.java8.study.interfaces.PersonInterface;

@SpringBootApplication
public class StudyJava8Application {
	
	private static final Logger log = LoggerFactory.getLogger(StudyJava8Application.class);

	public static void main(String[] args) {
		SpringApplication.run(StudyJava8Application.class, args);
		
		PersonInterface person = PersonFactory.create( DefaultPerson::new );
		log.warn("DefaultPersono: {}", person.myName());
		// System.out.println( person.myName() );
		
		person = PersonFactory.create( Mary::new );
		log.warn("Mary: {}", person.myName());
		// System.out.println( person.myName() );
		
		person = PersonFactory.create( Jhon::new );
		log.warn("Jhon: {}", person.myName());
		// System.out.println( person.myName() );
		
		person = PersonFactory.create( Paul::new );
		log.warn("Paul: {}", person.myName());
		// System.out.println( person.myName() );
	}
}
