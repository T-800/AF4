import java.util.Scanner;

public class main2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        Start();


		Arbre test = Arbre.lirePostfixe("bc.b+ab..*");
		//System.out.println(test);
		/*Automate auto = new Automate();
		auto = test.toAutomate();
		System.out.println(auto);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		moore algo = new moore();
		System.out.println(algo.mooreMinimisation(auto.determinise()).suppressPoubelle());
		//auto = auto.determinise();
		*/

		/*
		Etat init = new Etat(true,test.contientMotVide,0);
		residuel.map.put(test,init);
		residuel.residueRecursif(test, init);
		Automate auto2 = new Automate();
		auto2.ajouteEtatRecursif(init);
		System.out.println(auto2.determinise());
        clear();
        */
	}

    public static void clear() {
        System.out.print(((char) 27) + "[2J"); // ANSI efface la console
        System.out.print(((char) 27) + "[H"); // ANSI remet le curseur en haut de la console
    }

    public static void Start(){
        int main = -1;
        Automate automate;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("MENU PRINCIPAL");
            System.out.println();
            System.out.println("1 : Ouvrir fichier Automate");
            System.out.println("2 : Entrer une expression régulière");
            System.out.println("3 : Tester l'égalité de deux Automates");
            System.out.println();
            System.out.println("0 : Quitter");
            System.out.println();
            System.out.print("Choix : ");
            main = sc.nextInt();

            switch (main){
                case 1:
                    String path;
                    System.out.print("Entrez le chemin du fichier : ");
                    path = sc.next();
                    clear();
                    automate = new Automate(path);
                    Menu_Automate(automate);
                    break;
                case 2:
                    clear();
                    System.out.println("expression régulière");
                    System.out.print("Entrer une expression régulière : ");
                    //String pos = sc.next();
                    //Menu_Regex(pos);
                    Menu_Regex("abab.+*.b*abb.+.+");
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    break;
            }

        }while (main != 0);
    }

    public static void Menu_Automate(Automate automate){
        int main = -1;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("MENU AUTOMATE");
            System.out.println();
            System.out.println("1 : Affichage");
            System.out.println("2 : Determinisation");
            System.out.println("3 : Complementaire");
            System.out.println("4 : Minimisation (Moore)");
            System.out.println("5 : Sauvegarder");
            System.out.println();
            System.out.println("0 : Menu");
            main = sc.nextInt();

            switch (main){
                case 1:
                    clear();
                    System.out.println("~~~~~~~~~~~~~~~");
                    System.out.println("|  AFFICHAGE  |");
                    System.out.println("~~~~~~~~~~~~~~~");
                    System.out.println(automate);

                    System.out.println();
                    System.out.println("~~~~~~~~~~~~~~~");
                    break;
                case 2:
                    clear();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("|  DETERMINISATION  |");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~");
                    automate = automate.determinise();
                    break;

                case 3:
                    clear();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("|  COMPLÉMENTAIRE  |");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~");
                    automate = automate.complementaire();
                    break;

                case 4:
                    clear();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("|  MINIMISATION (Moore)  |");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    Moore algo = new Moore();
                    automate = algo.mooreMinimisation(automate.determinise()).suppressPoubelle();
                    automate = automate .determinise();
                    break;
                case 5:
                    clear();
                    System.out.println("~~~~~~~~~~~~~~~~~");
                    System.out.println("|  SAUVEGARDER  |");
                    System.out.println("~~~~~~~~~~~~~~~~~");
                    System.out.println();
                    System.out.print("Entrer le chemin de sauvegarde :");
                    String path = sc.next();

                    automate.toFile(path);
                    break;
                case 0:
                    clear();
                    main = 0;
                    break;
                default:
                    break;
            }

        }while (main != 0);
        return;
    }


    public static void Menu_Regex(String s){
        clear();
        int main = -1;
        Scanner sc = new Scanner(System.in);
        Arbre a;
        a = Arbre.lirePostfixe(s);
        boolean auto = false;
        Automate automate = null;
        do{


            System.out.println("MENU EXPRESSION RATIONNELLE");
            System.out.println("Votre expression rationnelle est :"+a);
            System.out.println();
            System.out.println("1 : Glushkov");
            System.out.println("2 : Résiduels");
            System.out.println();
            System.out.println("0 : Menu");
            main = sc.nextInt();

            switch (main){
                case 1:
                    clear();
                    System.out.println("~~~~~~~~~~~~~~");
                    System.out.println("|  GLUSHKOV  |");
                    System.out.println("~~~~~~~~~~~~~~");

                    automate = Automate.toAutomate(a);
                    auto = true;
                    main = 0;
                    break;

                case 2:
                    clear();
                    System.out.println("~~~~~~~~~~~~~~");
                    System.out.println("|  RÉSIDUEL  |");
                    System.out.println("~~~~~~~~~~~~~~");
                    Residuel resi1 = new Residuel();
                    automate = resi1.residuelExpression(a);
                    auto = true;
                    main = 0;

                    break;

                case 0:
                    clear();
                    break;
                default:
                    break;
            }

        }while (main != 0);
        if (auto)Menu_Automate(automate);
        return;
    }
}
