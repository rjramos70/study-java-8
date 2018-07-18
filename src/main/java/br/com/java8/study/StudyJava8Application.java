package br.com.java8.study;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.java8.study.entities.Car;
import br.com.java8.study.entities.DefaultPerson;
import br.com.java8.study.entities.Jhon;
import br.com.java8.study.entities.Mary;
import br.com.java8.study.entities.Paul;
import br.com.java8.study.entities.Person;
import br.com.java8.study.entities.annotations.Annotations.Holder;
import br.com.java8.study.entities.annotations.Annotations.NonEmpty;
import br.com.java8.study.entities.inference.Value;
import br.com.java8.study.entities.streams.Status;
import br.com.java8.study.entities.streams.Task;
import br.com.java8.study.interfaces.Defaulable;
import br.com.java8.study.interfaces.DefaultableFactory;
import br.com.java8.study.interfaces.PersonFactory;
import br.com.java8.study.interfaces.PersonInterface;
import br.com.java8.study.interfaces.impl.DefaultableImpl;
import br.com.java8.study.interfaces.impl.OverridebleImpl;

@SpringBootApplication
public class StudyJava8Application {
	
	private static final Logger log = LoggerFactory.getLogger(StudyJava8Application.class);

	@SuppressWarnings( "unused" )
	public static void main(String[] args) {
		SpringApplication.run(StudyJava8Application.class, args);
		
		System.out.println(" ----------- LAMBDA EXPRESSIONS --------------- ");
		
		List<Person> rosters = Person.createRoster();	// get a List of Person
		Person[] rostersAsArray = rosters.toArray(new Person[rosters.size()]); 	// transform the List in an Array of Person
		
		System.out.println(" ----------- Original List --------------- ");
		Arrays.asList(rostersAsArray).forEach( e -> e.printPerson());
		
		Arrays.asList(rostersAsArray).sort( (a, b) -> {
			return Person.compareByAge(a, b);
		} );
		
		System.out.println(" ----------- Orded by Age --------------- ");
		Arrays.asList(rostersAsArray).forEach( e -> e.printPerson());
		
		Arrays.asList(rostersAsArray).sort( (a, b) -> {
			return Person.compareByName(a, b);
		} );
		
		System.out.println(" ------------ Orded by Name -------------- ");
		Arrays.asList(rostersAsArray).forEach( e -> e.printPerson());
		
		// Convert Array to List
		System.out.println(" ------------ Print asList 1 -------------- ");
		List<Person> rosterList = Arrays.asList(rostersAsArray);
		rosterList.forEach( b -> b.printPerson());
		
		System.out.println(" ------------ Print asList 2 -------------- ");
		List<Person> rosterList2 = Arrays.stream(rostersAsArray).collect(Collectors.toList());
		rosterList2.forEach( b -> b.printPerson());
		
		System.out.println("--------------------------");
		Arrays.asList("a", "b", "c").forEach( e -> System.out.println(e));
		System.out.println("--------------------------");
		Arrays.asList("d", "e", "f").forEach( (String e) -> System.out.println(e));
		System.out.println("--------------------------");
		Arrays.asList("g", "h", "i").forEach( e -> {
			System.out.println(e);
			System.out.println(e);
		});
		System.out.println("--------------------------");
		String SEPARATOR = ", ";
		Arrays.asList("a", "b", "c").forEach( (String e) -> System.out.println(e + SEPARATOR) );
		System.out.println("--------------------------");
		
		// ----------------------------------------------------------------------------
		
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
		
		System.out.println("--------------------------");
		Defaulable defaultable = DefaultableFactory.create( DefaultableImpl::new );
		System.out.println(defaultable.notRequired());
		
		defaultable = DefaultableFactory.create( OverridebleImpl::new );
		System.out.println(defaultable.notRequired());
		
		System.out.println("--------------------------");
		// The first type of method references is constructor reference with the 
		// syntax Class::new or alternatively, for generics, Class< T >::new. 
		// Please notice that the constructor has no arguments.
		final Car car = Car.create( Car::new );
		final List<Car> cars = Arrays.asList(car);
		
		// The second type is reference to static method with the 
		// syntax Class::static_method. Please notice that the method 
		// accepts exactly one parameter of type Car.
		cars.forEach( Car::collide );
		
		// The third type is reference to instance method of arbitrary object of 
		// specific type with the syntax Class::method. Please notice, no arguments 
		// are accepted by the method.
		cars.forEach( Car::repair );
		
		// And the last, fourth type is reference to instance method of particular class 
		// instance the syntax instance::method. Please notice that method accepts exactly 
		// one parameter of type Car.
		final Car police = Car.create( Car::new );
		cars.forEach( police::follow );
		
		System.out.println("------------ Using Inference --------------");
		final Value<String> value = new Value<>();
		System.out.println(value.getOrDefault("22", Value.defaultValue()));
		
		// 2.6 Extended Annotations Support
		final Holder<String> holder = new @NonEmpty Holder<String>();
		@NonEmpty Collection<@NonEmpty String> strings = new ArrayList<>();
		
		
		// 4.1 Optional
		Optional<String> fullName = Optional.ofNullable( null );
		System.out.println( "Full Name is set? " + fullName.isPresent() );
		System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]") );
		System.out.println( fullName.map( s -> "Hey " + s + "!").orElse( "Hey Stranger" ) );
		System.out.println();
		
		// another Optional example
		Optional<String> firstName = Optional.of( "Tom" );
		System.out.println( "Full Name is set? " + firstName.isPresent() );
		System.out.println( "Full Name: " + firstName.orElseGet( () -> "[none]") );
		System.out.println( firstName.map( s -> "Hey " + s + "!").orElse( "Hey Stranger" ) );
		System.out.println();
		
		
		// 4.2 Streams ( Java 8 )
		final Collection<Task> tasks = Arrays.asList(
				new Task(Status.OPEN, 5),
				new Task(Status.OPEN, 13),
				new Task(Status.CLOSED, 8)
		);
		
		// How many points in total all OPEN tasks have?
		// Calculate total points of all active tasks using sum()
		final long totalPointsOfOpenTasks = tasks
				.stream()
				.filter( task -> task.getStatus() == Status.OPEN)	// intermediate operation (always LAZY)
				.mapToInt( Task::getPoints )
				.sum();												// terminal operation (In almost all cases, terminal operations are eager)
		System.out.println( "Total points: " + totalPointsOfOpenTasks );
		
		// Calculate total points of all tasks
		final double totalPoints = tasks
				.stream()
				.parallel()
				.map( task -> task.getPoints())		// or map( Task::getPoints )
				.reduce(0, Integer::sum);
		/* It is very similar to the first example except the fact that we 
		 * try to process all the tasks in parallel and calculate the final 
		 * result using reduce method.
		 */
		System.out.println( "Total points (all tasks): " + totalPoints );
		
		/* Often, there is a need to performing a grouping of the collection 
		 * elements by some criteria. Streams can help with that as well as
		 * an example below demonstrates.
		 */
		
		// Group tasks by their status
		final Map<Status, List<Task>> map = tasks
				.stream()
				.collect(Collectors.groupingBy( Task::getStatus ));
		System.out.println( "Map: " + map );
		
		/* To finish up with the tasks example, let us calculate the overall 
		 * percentage (or weight) of each task across the whole collection, 
		 * based on its points.
		 */
		// Calculate the weight of each tasks (as percent of total points)
		final Collection<String> result = tasks
				.stream()										// Stream<String>
				.mapToInt( Task::getPoints )					// IntStream
				.asLongStream()									// LongStream
				.mapToDouble( points -> points / totalPoints)	// DoubleStream
				.boxed()										// Stream<Double>
				.mapToLong( weigth -> (long) (weigth * 100))	// LongStream
				.mapToObj( percentage -> percentage + "%")		// Stream<String>
				.collect( Collectors.toList());					// List<String>
		System.out.println("Result:" + result );
		
		/* the Stream API is not only about Java collections. The typical I/O operations 
		 * like reading the text file line by line is a very good candidate to benefit 
		 * from stream processing. Here is a small example to confirm that.
		 */
		final Path path = new File("fileToTestStream.txt").toPath();
		try( Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)){
			lines.onClose( () -> System.out.println("Done....") ).forEach( System.out::println );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* 
		 * 4.3 Date/Time API (JSR 310) 
		 */
		System.out.println( "=> Date/Time API (JSR 310)" );
		// Get the system clock as UTC offset
		final Clock clock = Clock.systemUTC();
		System.out.println( clock.instant() );
		System.out.println( clock.millis() );
		
		System.out.println("----------------------");
		// Get the local date
		final LocalDate date = LocalDate.now();
		final LocalDate dateFromClock = LocalDate.now(clock);
		
		System.out.println( "date         : " + date );
		System.out.println( "dateFromClock: " + dateFromClock );
		
		// Get the local time
		final LocalTime time = LocalTime.now();
		final LocalTime timeFromClock = LocalTime.now(clock);
		final LocalTime timeFromZone = LocalTime.now( ZoneId.systemDefault() );
		
		System.out.println( "time         : " + time );
		System.out.println( "timeFromClock: " + timeFromClock );
		System.out.println( "timeFromZone : " + timeFromZone );
		
		// Get the local date/time
		final LocalDateTime dateTime = LocalDateTime.now();
		final LocalDateTime dateTimeFromClock = LocalDateTime.now(clock);
		
		System.out.println( "dateTime         : " + dateTime );
		System.out.println( "dateTimeFromClock: " + dateTimeFromClock );
		
		System.out.println("----------------------");
		
		// Get the zoned date/time
		final ZonedDateTime zonedDateTime = ZonedDateTime.now();
		final ZonedDateTime zonedDateTimeFromClock = zonedDateTime.now(clock);
		final ZonedDateTime zonedDateTimeFromZone = zonedDateTime.now( ZoneId.of( "America/Los_Angeles" ) );
		
		System.out.println( "zonedDateTime          : " + zonedDateTime );
		System.out.println( "zonedDateTimeFromClock : " + zonedDateTimeFromClock );
		System.out.println( "zonedDateTimeFromZone  : " + zonedDateTimeFromZone );
		
		System.out.println("----------------------");
		
		// Get duration between two dates
		final LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
		final LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );
		
		final Duration duration = Duration.between(from, to);
		
		System.out.println( "Duration in days  : " + duration.toDays() );
		System.out.println( "Duration in hours : " + duration.toHours() );
		
		
		
		/* 4.4 Nashorn JavaScript engine */
		
		/* Java 8 comes with new Nashorn JavaScript engine which allows developing and running 
		 * certain kinds of JavaScript applications on JVM. Nashorn JavaScript engine is just 
		 * another implementation of javax.script.ScriptEngine and follows the same set of rules, 
		 * permitting Java and JavaScript interoperability. 
		 */
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName( "JavaScript" );
		
		System.out.println( engine.getClass().getName() );
		System.out.println( engine.getClass().getSimpleName() );
		System.out.println( engine.getClass().getTypeName());
		System.out.println( engine.getClass().getDeclaredFields());
		System.out.println( engine.getClass().getFields());
		System.out.println( engine.getClass().getMethods());
		
		Method[] methods = engine.getClass().getMethods();
		for (Method method : methods) {
			System.out.println("method name: " + method.getName());
		}
		
		System.out.println("--------- Using Lambda for list all methods from Method.class -------------");
		List<Method> methosList = Arrays.asList(engine.getClass().getMethods());
		methosList.forEach( a -> {
			System.out.println(a.getName());
		} );
		
		// System.out.println( engine.getClass().get);
		
		String functionString = "function f(){ "
							  + "  return 1;   "
							  + "};            "
							  + "              "
							  + "f() + 1;      ";
		try {
			System.out.println( "Result : " + engine.eval( functionString ) );
		} catch (ScriptException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("----------------------");
		
		/* 4.5 Base64 */
		/* 
		 * the support of Base64 encoding has made its way into Java standard 
		 * library with Java 8 release 
		 */
		final String text = "Base64 finally in Java 8!";
		System.out.println( "Original text : " + text );
		
		final String encoded = Base64
								.getEncoder()
								.encodeToString( text.getBytes( StandardCharsets.UTF_8 ) );
		System.out.println( "encoded       : " + encoded );
		
		final String decoded = new String( Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8 );
		System.out.println( "decoded       : " + decoded );
		
		System.out.println( "Base64.getUrlEncoder()    : " + Base64.getUrlEncoder() );
		System.out.println( "Base64.getDecoder()       : " + Base64.getDecoder() );
		System.out.println( "Base64.getMimeEncoder()   : " + Base64.getMimeEncoder() );
		System.out.println( "Base64.getMimeDecoder()   : " + Base64.getMimeDecoder() );
		
		/* 4.6 Parallel Arrays 
		 * 
		 * Java 8 release adds a lot of new methods to allow parallel arrays processing. Arguably, 
		 * the most important one is parallelSort() which may significantly speedup the sorting on 
		 * multicore machines.
		 */
		System.out.println("------ 4.6 Parallel Arrays --------------------------");
		long[] arrayOfLong = new long[ 20000 ];
		
		Arrays.parallelSetAll(arrayOfLong, index -> ThreadLocalRandom.current().nextInt( 100000 ));
		
		System.out.print("Unsorted: ");
		Arrays.stream( arrayOfLong ).limit( 10 ).forEach( i -> System.out.print( i + ", "));
		System.out.println();
		
		Arrays.parallelSort( arrayOfLong );
		
		System.out.print("Sorted  : ");
		Arrays.stream( arrayOfLong ).limit( 10 ).forEach( i -> System.out.print( i + ", "));
		System.out.println();
		
		
		
		
		
		
		
		
		
	}
}
