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

  Plan du scénario: emprunt d'un livre non disponible
    Quand <nomEtudiant> emprunte le livre <titreLivre>
    Et <nomEtudiant> emprunte le livre <titreLivre>
    Alors Il y a <nombredEmprunts> dans son nombre d'emprunts
    Et Il y a le livre <titreLivre> dans un emprunt de la liste d'emprunts
    Et Le livre <titreLivre> est indisponible

    Exemples:
      | nomEtudiant | titreLivre                    | nombredEmprunts |
      | "Marcel"    | "UML pour les nuls"           |               1 |
      | "Walid"     | "Design Patterns for dummies" |               2 |
