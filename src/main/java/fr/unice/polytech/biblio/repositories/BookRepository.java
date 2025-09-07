package fr.unice.polytech.biblio.repositories;

import fr.unice.polytech.biblio.entities.Livre;

import java.util.*;

public class BookRepository  extends BasicRepositoryImpl<Livre, UUID> {

    //livres par titre - pour la recherche par titre, on simule une requete dans la BD
    private final HashMap<String, List<UUID>> booksByTitle = new HashMap<>();
    //Livre par identifiant biblio - pour la recherche par identifiant biblio, on simule aussi une requete dans la BD
    private final Map<String, Livre> bookByLibrayId = new HashMap<>();

    public void save(Livre livre) {
        UUID uuid = UUID.randomUUID();
        super.save(livre, uuid);
        bookByLibrayId.put(livre.getIdDansBiblio(), livre);
        if (booksByTitle.containsKey(livre.getTitre())) {
            booksByTitle.get(livre.getTitre()).add(uuid);
        } else {
            ArrayList<UUID> uuids = new ArrayList<>();
            uuids.add(uuid);
            booksByTitle.put(livre.getTitre(), uuids);
        }
    }

    // Recherche par titre : simule une requete dans la BD
    public List<Livre> getBooksByTitle(String titre) {
        if (booksByTitle.containsKey(titre)) {
            return booksByTitle.get(titre).stream().map(this::findById).map(Optional::get).toList();
        }
        return new ArrayList<>();
    }


    // Recherche par identifiant biblio : simule une requete dans la BD
    public Livre getBookByLibraryId(String id) {
        return bookByLibrayId.get(id);
    }
}