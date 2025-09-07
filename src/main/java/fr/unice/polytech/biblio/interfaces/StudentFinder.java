package fr.unice.polytech.biblio.interfaces;

import java.util.List;
import java.util.Optional;
public interface StudentFinder<T extends StudentInterface> {
    List<T > findAll();
    Optional<T> findByName(String name);
    Optional<T> findByNumber(int studentNumber);
}
