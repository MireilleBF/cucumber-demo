package fr.unice.polytech.biblio.services;

import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.repositories.StudentRepository;
import fr.unice.polytech.biblio.services.exceptions.ResourceAlreadyExistsException;
import fr.unice.polytech.biblio.services.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*
    * This class is used to manage the students of the university.
    * We choose to return optional and not to deal with exceptions as we did in the library.
    * We could have used exceptions, but we wanted to show that we can use optional.
 */
public class StudentRegistry {

    private StudentRepository studentRepository = new StudentRepository();

    public List<Etudiant> findAll() {
        List<Etudiant> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public Optional<Etudiant> findByName(String name) {
        return studentRepository.findByName(name);
    }

    public Optional<Etudiant> findByNumber(int studentNumber) {
        return studentRepository.findById(studentNumber);
    }

    public void addStudent(String name, int studentNumber) throws ResourceAlreadyExistsException {
        if (studentRepository.findById(studentNumber).isPresent()) {
            throw new ResourceAlreadyExistsException("Student with number " + studentNumber + " already exists");
        }
        Etudiant student = new Etudiant();
        student.setName(name);
        student.setStudentNumber(studentNumber);
        studentRepository.save(student, studentNumber);
    }

    public void removeStudent(int studentNumber) throws ResourceNotFoundException {
        if (studentRepository.findById(studentNumber).isPresent()) {
            studentRepository.deleteById(studentNumber);
        } else {
            throw new ResourceNotFoundException("Student with number " + studentNumber + " not found");
        }
    }

    public void updateStudent(int studentNumber, String name) {
        if (studentRepository.findById(studentNumber).isPresent()) {
            Etudiant student = studentRepository.findById(studentNumber).get();
            student.setName(name);
            studentRepository.save(student, studentNumber);
            return;
        }
        // If the student does not exist, we create it
        Etudiant student = new Etudiant();
        student.setName(name);
        student.setStudentNumber(studentNumber);
        studentRepository.save(student, studentNumber);
    }

    public StudentRegistry() {
        // Mimic loading of members from a database
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setName("John Doe");
        etudiant1.setStudentNumber(123456);
        studentRepository.save(etudiant1, 123456);

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setName("Jane Doe");
        etudiant2.setStudentNumber(654321);
        studentRepository.save(etudiant2, 654321);
    }
}
