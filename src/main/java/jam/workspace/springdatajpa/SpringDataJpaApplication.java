package jam.workspace.springdatajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Student student = new Student(firstName, lastName, email, faker.number().numberBetween(17, 45));
            student.addBook(new Book(LocalDateTime.now().minusDays(4), "Clean Code"));
            student.addBook(new Book(LocalDateTime.now(), "Think and Grow Rich"));
            student.addBook(new Book(LocalDateTime.now().minusYears(1), "Spring Data JPA"));
            StudentIdCard studentIdCard = new StudentIdCard("1234567890", student);

            student.setStudentIdCard(studentIdCard);
            studentRepository.save(student);
//            studentIdCardRepository.findById(1L).ifPresent(System.out::println);
            studentRepository.findById(1L).ifPresent(student1 -> {
                System.out.println("Fetch book lazy...");
                List<Book> books = student1.getBooks();
                books.forEach(book -> System.out.println(student1.getFirstName() + " borrowed " + book.getBookName()));
            });
        };
    }

    private static void sorting(StudentRepository studentRepository) {
        studentRepository.findAll(Sort.by( "firstName").ascending().and(Sort.by("lastName").descending())).forEach(student -> System.out.println(student.getFirstName()));
    }

    private static void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 1; i <= 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            studentRepository.save(new Student(firstName, lastName, email, faker.number().numberBetween(17, 45)));
        }
    }

}
