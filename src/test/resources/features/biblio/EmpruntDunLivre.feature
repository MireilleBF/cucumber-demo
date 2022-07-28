#language: fr
Fonctionnalité: Emprunter un livre

  Contexte:
    Etant donné un etudiant de nom "Marcel" et de noEtudiant 123456
    Et un livre de titre "UML pour les nuls"

  Scénario: Pas d'emprunt par défaut
    Quand "Marcel" demande son nombre d'emprunt
    Alors Il y a 0 dans son nombre d'emprunts

  Scénario: emprunt d'un livre
    Quand "Marcel" emprunte le livre "UML pour les nuls"
    Alors Il y a 1 dans son nombre d'emprunts
      Et Il y a le livre "UML pour les nuls" dans un emprunt de la liste d'emprunts
      Et Le livre "UML pour les nuls" est indisponible

  Scénario: rendu d'un livre
    Quand "Marcel" rend le livre "UML pour les nuls"
    Alors Il y a 0 dans son nombre d'emprunts
    Et Le livre "UML pour les nuls" est disponible
