import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Residuel {

	private Map<Arbre, Etat> map = new HashMap<Arbre, Etat>(); // Map de l'arbre represantant une expression associer a un état
    private ArrayList<Couple> tmp = new ArrayList<Couple>();
    private char[] alphabet;
	
	
	public Automate residuelExpression(Arbre arbreExpr){
        System.out.println("Expression rationnelle :"+arbreExpr+"\n");
		Etat initial = new Etat(true,arbreExpr.contientMotVide,0); // construction de l'état Initial
        alphabet = new char[arbreExpr.alphabet().size()];
        int i = 0;
        for (Character c : arbreExpr.alphabet()){
            alphabet[i++]=c;
        }
        Arrays.sort(alphabet);
		this.map.put(arbreExpr,initial); // on associe a l'expression de depart un état initial
		this.residueRecursif(arbreExpr, initial,"");
		Automate automate = new Automate();
		automate.ajouteEtatRecursif(initial);
		return automate;
	}
	
	public void residueRecursif(Arbre arbre, Etat etat, String last){
		for(char l : alphabet){
			Arbre arbreResiuelLettre=arbre.residuel(l);// on calcul le residuel de notre arbre aavec notre lettre l
            System.out.println("(" + last + l + "¯¹) " + arbre.toString() + " = " + arbreResiuelLettre.toString());
			boolean empile= true;
			for(Arbre a : this.map.keySet()){ // dans notre map





                if(a.toString().equals(arbreResiuelLettre.toString())&& // si l'arbre existe deja et
                        (((a.epsilon ||a.contientMotVide)&&(arbreResiuelLettre.epsilon ||arbreResiuelLettre.contientMotVide)) //qu'ils contiennent tous les deux le mot vide OU
                                        ||((!a.contientMotVide&&!a.epsilon)&&(!arbreResiuelLettre.epsilon &&!arbreResiuelLettre.contientMotVide)))){




                    etat.ajouteTransition(l,map.get(a)); // on ajoute a l'etat une transition vers l'etat correspondant dans la map
					empile=false; // donc on fera pas de nouvel etat
					break; // et on est pas obligé de continuer
				}
			}
			
			if (empile && arbreResiuelLettre.symbole!='0'){  // si on doit le faire et que notre arbre n'est pas vide
				Etat e = new Etat(false,arbreResiuelLettre.contientMotVide||arbreResiuelLettre.epsilon,map.size());
				etat.ajouteTransition(l,e); // on fait une transition vers l'etat créé
				map.put(arbreResiuelLettre, e); // et on l'ajoute a notre map
                if(arbreResiuelLettre.symbole!='1')tmp.add(new Couple(arbreResiuelLettre, e,last+l)); // si l'arbre n'est pas le mot vide on continue ajoute a notre liste le couple suivant a faire
			}
		}
        if(!tmp.isEmpty()){ // si la liste n'est pas vide on rappel cette fonction recusivement sur le couple
            Couple c = tmp.get(0);
            tmp.remove(0);
			residueRecursif(c.a, c.e, c.l);
		}
	}

    class Couple{
        Arbre a;
        Etat e;
        String l;

        public Couple(Arbre a, Etat e, String l) {
            this.a=a;
            this.e=e;
            this.l= l;
        }
    }
}

