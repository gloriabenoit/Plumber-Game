# Jeu du Plombier (M1BI - 2024)
Ce projet est la production du cours de Programmation avancée de mon M1 à Université Paris Cité, organisé par M. Vincent Padovani. Ce projet visait à écrire en `java`, à l'aide de la librairie `swing`, une application permettant de jouer au jeu du plombier uniquement en utilisant la souris.

## Installation

Aucune installation externe est nécessaire, il suffit de récupérer le répertoire. Le jeu commence en lançant `MainMenu.java`.

## Principe

Le but du jeu est de relier une série de tuyau de manière à ce qu’ils soient tous connectés entre eux, sans qu’aucun ne soit ouvert sur la bordure du plateau. Le tuyau central est en rouge, indiquant que le courant d'eau y passe. Il faut donc tourner les tuyaux jusqu'à ce qu'ils soient tous rouge. 
Le jeu contient une douzaine de niveaux, de difficulté croissante. 

Chaque niveau possède quatre boutons:
- *Menu* permet de revenir à l'accueil.
- *Retour arrière* permet d'annuler tous les coups effectués. Le bouton est grisé si l'historique de coup est vide.
- *Solution* affiche la solution. Cela ne permet néanmoins pas de passer au niveau suivant, il faut revenir au menu principal pour cela.
- *Redémarrage* randomise toutes les cases de la grille. Cela réinitialise aussi l'historique des coups.

Lors d'une partie, un score est retenu selon le nombre de niveaux réussis à la suite.
