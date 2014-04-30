import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		
		String fonction = "";
		String fichier = "";
		
		/*if (args.length != 2) {
			System.out.println("Usage : <progname> <fonction> <fichier.txt>");
		}
		else{ 
		System.out.println("Argument 1 : " + args[0] + ", argument 2 : "
				+ args[1]);*/
		
		HashMap<String, Integer> h = new HashMap<String, Integer>();
		h.put("",0);
		h.put("exprToAutoEpsilon",1);
		h.put("estDeterministe",2);
		h.put("determinise",3);
		h.put("Accepte",4);
		h.put("complet",5);
		h.put("complementaire",6);
		h.put("miroir",7);
		h.put("copieAutomate",8);
		h.put("union",9);
		h.put("intersection",10);
		h.put("sontEgaux",11);
		h.put("minimisation",12);
		h.put("substitue",13);
		h.put("genereAuto1Lettre",14);
		h.put("testLongeurAutomate",15);
		h.put("etoile",16);
		h.put("suppEpsilonInAuto",17);
		h.put("unionEpsilon",18);
		h.put("concatenation",19);
		h.put("print", 20);
		
		//fonction = args[0];
		//fichier = args[1];
		 
		int fonct = 0;
		try{
			fonct = h.get(fonction);
		}
		catch(Exception e){
			System.out.println(e + "fonction non trouvée" );
		}
		// TODO 
		/*Lecteur l = new Lecteur("C:/Users/Aline/Desktop/recupAF4/src/figure1.txt");   /*"C:/Users/Aline/Dropbox/AF4/recupAF4/src/" + fichier); /h1/a/alepai94/workspace/projetAF4/src/" + fichier);*/
		
		Lecteur l = new Lecteur("C:/Users/Aline/Desktop/recupAF4/src/figure1.txt");
		String chaine = l.lecture();
		
		Automate A = l.fichierToAutomate(chaine);/* new Automate();*/
		//System.out.println("test : " + A.complementaire());
		Scanner sc = new Scanner(System.in);
		String auto = sc.nextLine();
		Automate B = Automate.exprToAutoEpsilon(auto);
		System.out.println("B: " + B);
		System.out.println("union A et B : " + A.union(B)); 
		//System.out.println(A);
		//Scanner sc = new Scanner(System.in);
		
		/*fonct = 0;
		
		try{
			switch(fonct){
			case 0 : 
				System.out.println("Arguments non valides");
				break;
			case 1: 
				System.out.println("Veuillez saisir une expression d'automate (postfixe) :");
				String str = sc.nextLine();
				System.out.println(Automate.exprToAutoEpsilon(str)); 
				break;
			case 2: 
				System.out.println(A.estDeterministe()); 
				break;
			case 3: 
				System.out.println(A.determinise()); 
				break;
			case 4: 
				System.out.println("Veuillez saisir un mot a tester :");
				String mot = sc.nextLine();
				System.out.println(A.Accepte(mot)); 
				break;
			case 5: 
				System.out.println(A.complet()); 
				break;
			case 6: 
				System.out.println(A.complementaire()); 
				break;
			case 7: 
				System.out.println(A.miroir()); 
				break;
			case 8: 
				System.out.println(A);
				System.out.println(A.copieAutomate());
				break;
			case 9: 
				System.out.println("Veuillez saisir une expression d'automate(postfixe) :");
				String autobis = sc.nextLine();
				Automate Bbis = Automate.exprToAutoEpsilon(autobis);
				System.out.println(A.union(Bbis)); 
				break;
			case 10: 
				System.out.println("Veuillez saisir une expression d'automate(postfixe) :");
				String auto1 = sc.nextLine();
				Automate B1 = Automate.exprToAutoEpsilon(auto1);
				System.out.println(A.intersection(B1)); break;
			case 11:
				System.out.println("Veuillez saisir une expression d'automate(postfixe) :");
				String auto2 = sc.nextLine();
				Automate B2 = Automate.exprToAutoEpsilon(auto2);
				System.out.println(A.sontEgaux(B2));break;
			case 12: 
				System.out.println(A.minimisation()); break;
			case 13: 
				// TODO char et non string + ggerer erreur si entrÃ©e de string + non chiffre
				System.out.println("Veuillez saisir la lettre a substituer :");
				String lettre1 = sc.nextLine(); 
				System.out.println("Veuillez saisir la lettre de substitution :");
				String lettre2 = sc.nextLine(); 
				//System.out.println(A.substitue(lettre1, lettre2));break;
			case 14: 
				//TODO mettre en entier + verifier qu'on entre pas un char ou string
				System.out.println("Veuillez saisir un entier u:");
				String u = sc.nextLine(); 
				System.out.println("Veuillez saisir un entier v:");
				String v = sc.nextLine(); 
				System.out.println("Veuillez saisir la lettre :");
				//TODO mettre en char
				String c = sc.nextLine(); 
				//System.out.println(Automate.genereAuto1Lettre(u, v, c)); break;
			case 15: 
				//TODO mettre en entier + verifier qu'on entre pas un char ou string
				System.out.println("Veuillez saisir un entier u:");
				String u1 = sc.nextLine(); 
				System.out.println("Veuillez saisir un entier v:");
				String v1 = sc.nextLine(); 
				//System.out.println(A.testLongeurAutomate(u1, v1)); break;
			case 16: 
				System.out.println(A.etoile()); break;
			case 17: 
				System.out.println(A);
				System.out.println(A.suppEpsilonInAuto());
				break;
			case 18: 
				System.out.println("Veuillez saisir une expression d'automate(postfixe) :");
				String auto3 = sc.nextLine();
				Automate B3 = Automate.exprToAutoEpsilon(auto3);
				System.out.println(A.unionEpsilon(B3)); break;
			case 19: 
				System.out.println("Veuillez saisir une expression d'automate(postfixe) :");
				String auto4 = sc.nextLine();
				Automate B4 = Automate.exprToAutoEpsilon(auto4);
				System.out.println(A.concatenation(B4)); break;
			case 20: 
				System.out.println(A); break;
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		/**
		 * TODO : verifier que arg[0] correspond a une fonction existante dans
		 * Automate verifier que le fichier existe
		 */
		/**
		 * TODO regarder si le fichier contient les donnÃ©es de la construction
		 * d'un auto ou une expression et construire l'automate correspondant
		 */
		/**
		 * TODO appliquer la fonction si tous les testes sont validÃ©s
		 * sinon imprimer message d'erreur
		 */
		/**
		 * TODO creer plein d'automates de test des fonctions
		 * creer un fichier d'explication des fichier
		 * creer un fichier d'explication du programme
		 */
	}
}