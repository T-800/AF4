#AF4

## 1) Determinisation :

L'état **Initial** est un état qui possède tous les numéros des états initiaux de l'automate
Les états **finaux** sont les états qui ont **au moins un** numéro d'un état final de l'automate

| État | a | b |
|:--|:--:|:--:|
| 0 | 0,1 | 0,2 |
| 1 | 1,**3** | 1 |
| 2 | 2 | 2,**3**|
| **3** | **/** | **/** |

![alt text](/media/data/git/AF4/revision/img1.JPG "Determinisation") ![alt text](/media/data/git/AF4/revision/img4.JPG "Determinisation")

## 2) Automate Fini Complet Deterministe

Pour transformer un AFD en un AFCD il faut ajouter un état "Poubelle" vers qui toutes les transitions manquante des autres états vont pointer

![alt text](/media/data/git/AF4/revision/img14.JPG "Determinisation")

## 3) Complémentaire

**L'automate doit être complet.**

Tous les états finaux deviennent non-finaux et inversement.

![alt text](/media/data/git/AF4/revision/img15.JPG "Determinisation")

## 4) Mirroir

+ On inverse les transitions

+ États Initiaux **=>** Terminaux

+ États Terminaux **=>** Initiaux

## 5) Union et Inter

![alt text](/media/data/git/AF4/revision/img17.png "Determinisation") ![alt text](/media/data/git/AF4/revision/img18.png "Determinisation")

### Union

||I| | II| |
| :--: | :---: |
| | a | b | a | b |
| 1 | **3 I** | **2 II** | **3 I** | **2 II** |
| **2** | **1 I** | **3 II** | **1 I** | **3 II** |
| **3** | **2 I** | **1 II** | **2 I** | **1 II** |

![alt text](/media/data/git/AF4/revision/img20.JPG "Determinisation")

### Inter

||I| | II| |
| :--: | :---: |
| | a | b | a | b |
| 1 | **3 I** | **2 II** | **3 I** | **2 II** |
| **2** | 1 I | **3 II** | 1 I | **3 II** |
| **3** | **2 I** | 1 II | **2 I** | 1 II |

![alt text](/media/data/git/AF4/revision/img19.JPG "Determinisation")

## 7) Moore
#### Minimisation d'un Automate
![alt text](/media/data/git/AF4/revision/img5.JPG "Moore")

| Etat | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
|:--:|:--:|:--:|:--|:--:|:--:|:--|:--:|
| Groupe | 0 | 0 | 0 | 0 | 0 | 1 | 1 |
| T | 1 0 | 1 0 | 0 1 | 0 1 | 0 1 | 1 0 | 1 0 |

| Groupe | 0 | 0 | 2 | 2 | 2 | 1 | 1 |
|:--:|:--:|:--:|:--|:--:|:--:|:--|:--:|
| T | 1 0 | 1 0 | 0 1 | **2** 1 | **2** 1 | 1 **2** | 1 **2** |

| Groupe | 0 | 0 | 3 | 2 | 2 | 1 | 1 |
|:--:|:--:|:--:|:--|:--:|:--:|:--|:--:|
| T | 1 0 | 1 0 | 0 1 | **3** 1 | 2 1 | 1 2 | 1 2 |

| Groupe | 0 | 0 | 3 | 2 | 4 | 1 | 1 |
|:--:|:--:|:--:|:--|:--:|:--:|:--|:--:|
| T | 1 0 | 1 0 | 0 1 | 3 1 | 2 1 | 1 **4** | 1 **4** |
![alt text](/media/data/git/AF4/revision/img6.JPG "Moore")
## 8) Mc Naughton et Yamada

#### Contruire une expression rationnelle à partir d'un automate

![alt text](/media/data/git/AF4/revision/img8.JPG "Arden") **Règle de suppression d'un état**

##### Soit 'A' l'automate suivant:

![alt text](/media/data/git/AF4/revision/img9.JPG "Arden")

* On ajoute à l'automate un état 'I' initial auquel on ajoute des epsilons transition vers les états initiaux
et un état 'F' Final vers qui vont pointer tous les états finaux avec des épsilon transitions

![alt text](/media/data/git/AF4/revision/img10.JPG "Arden")

* Suppression de l'état '2'

![alt text](/media/data/git/AF4/revision/img11.JPG "Arden")

* Suppression de l'état '0'

![alt text](/media/data/git/AF4/revision/img12.JPG "Arden")

* Suppression de l'état '1'

![alt text](/media/data/git/AF4/revision/img13.JPG "Arden")
## 9) Équation

#### Contruire une expression rationnelle à partir d'un automate

#### Lemme d'Arden

_'L = X.L+Y => L = X*.Y'_

==

**Lautomate doit être déterministe**

![alt text](/media/data/git/AF4/revision/img2.JPG "Arden")

        L0 = a.L0 + a.L1 + ε
        L1 = b.L1 + b.L2
        L2 = a.L2 + a.L3
        L3 = b.L3 + b.L0

**On ne cherche que les états initiaux. S'il y a plusieurs états initiaux on fera l'union**

    L3 = b*.(b.L0)' Lemme d'Arden

On remplace 'L3' dans 'L2':

    L2 = a.L2 + a.b*.(b.L0)

Il nous reste donc :

    L0 = a.L0 + a.L1 + ε
    L1 = b.L1 + b.L2
    L2 = a.L2 + a.b*.(b.L0)

On applique le Lemme d'Arden sur 'L2'

    L2 = a*.a.b*.b.L0 => (Arden)

On remplace 'L2' dans 'L1'

    L0 = a.L0 + a.L1 + ε
    L1 = b.L1 + b.a*.a.b*.b.L0

On applique le Lemme d'Arden sur 'L1'

    L1 = b*.b.a*.a.b*.b.L0 => (Arden)
    L0 = a.L0 + a.b*.b.a*.a.b*.b.L0 + ε

    L0 = (a.+ a.b*.b.a*.a.b*.b).L0 + ε

    L0 = (a.+ a.b*.b.a*.a.b*.b)* => (Arden)




## 10) Résiduel

`L = b.(a.a)*.b.a.(b.b)*.b`


|État | |Expression|
|:--     |:--|:--|
|0|`L =`|` b.(a.a)*.b.a.(b.b)*.b`|
|**-**|`(a–¹)L =`|`Ø `  |
|1|`(b–¹)L = `|`(a.a)*.b.a.(b.b)*.b `  |
|2|`(ba–¹)L =`|`a.(a.a)*.b.a.(b.b)*.b `|
|3|`(bb–¹)L =`|`a.(b.b)*.b `|
|1|`(baa–¹)L = (b–¹)L =`|`(a.a)*.b.a.(b.b)*.b`|
|**-**|`(bab–¹)L =`|`Ø `|
|4|`(bba–¹)L =`|`(b.b)*.b `|
|**-**|`(bbb–¹)L =`|`Ø `|
|**-**|`(bbaa–¹)L =`|`Ø`|
|**5**|`(bbab–¹)L =`|`b.(b.b)*.b` **+ ε**|
|**-**|`(bbaba–¹)L =`|`Ø`|
|4|`(bbabb–¹)L = (bba–¹)L =`|`(b.b)*.b`|

![alt text](/media/data/git/AF4/revision/img3.JPG "residuel")

## 11) Thomson
![alt text](/media/data/git/AF4/revision/eq5.png "residuel")
![alt text](/media/data/git/AF4/revision/img16.png "residuel")

## 12) Glushkov
#### Contruire un AF à partir d'une éxpression rationnelle

ex :
`L = ((a.(a.b)*.b)+(b + a.a))*`

* On remplace chaque lettre par un numéro :
`L = ((α1.(α2.α3)*.α4)+(α5 + α6.α7))*`

* On rempli le tableau au ajoutant la ligne `0` qui sera l'état initial

| Lettre | a         | b      |
|:--     |:--:       |:--:    |
| 0      | α1,α6     | **α5** |
| α1     | α2        | **α4** |
| α2     | **-**     | α3     |
| α3     | α2        | **α4** |
| **α4** | α1,α6     | **α5** |
| **α5** | α1,α6     | **α5** |
| α6     | **α7**    | **-**  |
| **α7** | α1,α6     | **α5** |

* On dessine l'automate de la même maniere que pour la determinisation.


## 13) Monoïde de transition

![alt text](/media/data/git/AF4/revision/img21.png "residuel")*

| ε | a = aaa | b = aab | aa | bb = bbb = abb = baa | ab | ba = baa = aba | bab
|:--:|:--:|:--:|:--|:--:|:--:|:--|:--:|
| 0 | 1 | 2 | 2 | 2 | 0 | 1 | 0 |
| 1 | 2 | 0 | 1 | 2 | 2 | 1 | 0 |
| 2 | 1 | 2 | 2 | 2 | 0 | 1 | 0 |

Soit ![alt text](/media/data/git/AF4/revision/eq4.png "residuel")

## 14) Critère de cloture

## 15) Égalité de deux expressions rationnelles
2 methodes:

* Réduire les expressions rationnelles.

* Construire leurs Automates Minimal
