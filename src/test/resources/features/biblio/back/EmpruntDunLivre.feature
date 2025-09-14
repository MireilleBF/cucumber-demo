# language: fr
Fonctionnalité: Emprunter un livre

  Contexte:
    Etant donné une bibliothèque avec un etudiant de nom "Marcel" et de noEtudiant 123456
    Et un etudiant de nom "Walid" et de noEtudiant 123457
    Et un livre de titre "UML pour les nuls"
    Et un livre de titre "Design Patterns for dummies" en deux exemplaires

  Scénario: emprunt d'un livre
    Quand "Marcel" emprunte le livre "UML pour les nuls"
    Alors Il y a 1 dans son nombre d'emprunts
    Et Il y a le livre "UML pour les nuls" dans un emprunt de la liste d'emprunts
    Et Le livre "UML pour les nuls" est indisponible

  Scénario: emprunt d'un exemplaire d'un livre
    Quand "Marcel" emprunte le livre "Design Patterns for dummies"
    Alors Il y a 1 dans son nombre d'emprunts
    Et Il y a le livre "Design Patterns for dummies" dans un emprunt de la liste d'emprunts
    # car il y en a 2 exemplaires
    Et Le livre "Design Patterns for dummies" est disponible

  Scénario: rendu d'un livre
    Etant donné que "Marcel" a emprunté le livre "UML pour les nuls"
    Quand "Marcel" rend le livre "UML pour les nuls"
    Alors Il y a 0 dans son nombre d'emprunts
    Et Le livre "UML pour les nuls" est disponible

  Scénario: emprunt du même livre en 1 exemplaire, non disponible
    Quand "Marcel" emprunte le livre d'id "U-0"
    Et "Marcel" emprunte le livre d'id "U-0"
    Alors une exception de type "BookAlreadyBorrowedException" est levée avec le message "Book U-0 already borrowed"
    Et Il y a 1 dans le nombre d'emprunts de "Marcel"
    Et Il y a le livre d'id "U-0" dans les emprunts de "Marcel"
    Et Le livre d'id "U-0" est indisponible

  Scénario: emprunt du même livre en 2 exemplaires par deux personnes différentes
    Quand "Marcel" emprunte le livre d'id "DPfd-1"
    Et "Walid" emprunte le livre d'id "DPfd-2"
    Alors Il y a 1 dans le nombre d'emprunts de "Marcel"
    Et Il y a 1 dans le nombre d'emprunts de "Walid"
    Et Il y a le livre d'id "DPfd-1" dans les emprunts de "Marcel"
    Et Il y a le livre d'id "DPfd-2" dans les emprunts de "Walid"
    Et Le livre d'id "DPfd-1" est indisponible
    Et Le livre d'id "DPfd-2" est indisponible

  Scénario: emprunt d'un même livre en 2 exemplaires par la même personne
    Quand "Marcel" emprunte le livre d'id "DPfd-1"
    Et "Marcel" emprunte le livre d'id "DPfd-2"
    Alors une exception de type "BookAlreadyBorrowedException" est levée avec le message "Same book DPfd-1 already borrowed"
    Et Il y a 1 dans le nombre d'emprunts de "Marcel"
    Et Il y a le livre d'id "DPfd-1" dans les emprunts de "Marcel"
    Et Le livre d'id "DPfd-1" est indisponible
    Et Le livre d'id "DPfd-2" est disponible