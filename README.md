# From Moodle To Tuto

**From Moodle To Tuto** est un programme créé pour la cellule LAS de l'Association Angevine du Tutorat PASS-LAS (2ATP).
Il a pour objectif de faciliter la création des supports PDF à partir des QCM créés sur la plateforme Moodle de l'Université d'Angers.

Ce projet est réservé à l'utilisation faite dans le cadre du rôle de tuteur/tutrice, Chef(fe) de Projet, Vice-Président(e) de la cellule LAS.
A l'heure actuelle, ce programme n'est pas adapté aux besoins des autres cellules de la 2ATP.

**From Moodle To Tuto © 2024 by Antoine LETESSIER is licensed under a** [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License][cc-by-nc-nd].

[![CC BY-NC-ND 4.0][cc-by-nc-nd-image]][cc-by-nc-nd]

[cc-by-nc-nd]: http://creativecommons.org/licenses/by-nc-nd/4.0/
[cc-by-nc-nd-image]: https://licensebuttons.net/l/by-nc-nd/4.0/88x31.png
[cc-by-nc-nd-shield]: https://img.shields.io/badge/License-CC%20BY--SA%204.0-lightgrey.svg

## Utilisation

### Première Utilisation

1. Téléchargez la dernière version du logiciel à droite de cette page dans la catégorie **"Releases"**.
   Téléchargez uniquement le fichier en .jar
2. Installez la dernière version de Java *(si elle n'est pas déjà installée)* : https://www.oracle.com/fr/java/technologies/downloads/#jdk21-windows
   Java est nécessaire pour faire fonctionner le programme. Il se peut que Java demande à redémarrer votre ordinateur pour finaliser son installation.
4. Une fois téléchargé, placez le fichier .jar dans un dossier *Par exemple, dans un dossier "Générateur de colle"*
5. Lancez une fois le fichier .jar une première fois. Une fenêtre s'ouvre et indique "Le dossier questions a été créé". Cliquez sur "Ok", le programme se ferme automatiquement.

   ![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/4597886d-3173-4626-b09c-e534aad519fe)

### Utilisation
1. Allez sur Moodle, puis dans le test de chaque module. Allez dans l'onglet "Questions" du test Moodle.
![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/d26bb0e7-384e-4c85-a682-6061ed94e875)
2. Cliquez sur la loupe pour chaque question du test
![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/71029646-2c19-417b-8f15-e2a6bdc0c843)
3. Descendez en bas de la page ouverte et cliquez sur "Télécharger cette question au format XML"
   ![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/20d464c9-1615-4e3e-9c7c-559c9d81077e)

   ** ATTENTION ! Seuls les QCM sont acceptés par le logiciel ! Si des QROCS (ou autre format) sont proposés, il faudra les ajouter manuellement au fichier Word généré.

4. Prenez le fichier XML que vous venez de télécharger et déposez le dans le dossier correspond au module du test. Vous trouverez ce dossier dans le dossier "questions", à côté du fichier .jar *(Ici, la question est en Chimie)*
   ![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/4f150c8a-8291-4433-a4c9-6ba1db8d58e2)
   ![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/2177b21f-f93a-4bf4-a3bd-5ba491290b80)

5. Relancez le logiciel. Les questions sont indexées ! Suivez les indications de l'interface, puis cliquez sur "Générer la colle" pour créer les fichiers dans le répertoire que vous avez indiqué.

### Signaler un bug :
Cliquez sur le bouton "Issue" de cette page (en haut de la page) et cliquez sur "New Issue".
Décrivez votre problème et j'essaierai de le résoudre le plus rapidement possible !

**Vérifiez bien que le problème n'est pas déjà connu ou que l'erreur n'est pas décrite sur cette page avant d'ouvrir un nouveau ticket !**

### Erreurs

#### Fichier non reconnu
Cette erreur indique qu'une question n'est pas au format reconnu par le logiciel.
Le logiciel ne reconnait que les QCM au format ABCDE (format classique). Ainsi, les QROCS, KFP, questions à plus de 5 choix ne seront pas reconnus et afficheront cette erreur

__Résoudre cette erreur :__
Retirez la question qui pose problème.
Vous devrez l'ajouter manuellement au fichier Word généré 😄

![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/4ba2aa6d-934e-4867-9623-9c257e687695)

#### Aucune question n'a été trouvée

Aucun fichier n'a été trouvé dans le dossier "questions".
__Résoudre cette erreur :__
Ajoutez des fichiers dans le dossier question. Il n'est pas nécessaire de remplir tous les dossiers "module". Seuls les dossiers "module" contenant des questions seront traités.

![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/c50ee88d-0a57-4b14-8e07-49221a6ffbdd)

#### Une erreur est survenue pendant la création du fichier Word
Le répertoire de destination n'existe pas ou le fichier Word est ouvert actuellement.

__Résoudre cette erreur :__
Indiquez un dossier qui existe et sur lequel vous avez les droits de modification (si vous n'êtes pas sur votre ordinateur) et fermez le fichier Word s'il est ouvert.

![image](https://github.com/Frivec/2ATPLASMoodleToWord/assets/46108576/51a1c3df-7993-4583-84c6-6efb7bbb8d22)





   




