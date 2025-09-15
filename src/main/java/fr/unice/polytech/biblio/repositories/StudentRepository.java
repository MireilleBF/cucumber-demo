package fr.unice.polytech.biblio.repositories;

import fr.unice.polytech.biblio.entities.Etudiant;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StudentRepository  extends BasicRepositoryImpl<Etudiant, Integer> {

    //Pour mimer une requete dans la BD
    private final Map<String, Integer> students = new HashMap<>();


    @Override
    public void save(Etudiant entity, Integer uuid) {
        super.save(entity, uuid);
        students.put(entity.getName(), uuid);
    }

    public Optional<Etudiant> findByName(String name) {
        Integer uuid = students.get(name);
        if (uuid != null) {
            return findById(uuid);
        }
        return Optional.empty();
    }
}