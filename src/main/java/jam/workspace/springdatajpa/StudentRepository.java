package jam.workspace.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Repository
@Transactional
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.email =?1")
    Optional<Student> findStudentByEmail(String email);
    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstname, Integer age);
    List<Student> findStudentsByFirstNameEqualsOrAgeEquals(String firstname, Integer age);
    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 OR s.age >= ?2")
    List<Student> findStudentsByFirstnameEqualsOrAgeIsGreaterThanEqual(String firstname, Integer age);

    @Query(value = "SELECT * FROM student s WHERE s.first_name = :firstName OR s.age >= :age", nativeQuery = true)
    List<Student> findStudentsByFirstnameEqualsOrAgeIsGreaterThanEqualNative(@Param("firstName") String firstname, @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int deleteStudentById(Long id);
}
