import java.util.*;

public class Residuel {

	private Arbre language;
	private Map<Arbre, Etat> map = new HashMap<Arbre, Etat>();
    private Queue<Couple> tmp = new LinkedList<Couple>();
	
	
	public Automate residuelExpression(Arbre arbreExpr){
        System.out.println("Expression rationnelle :"+arbreExpr+"\n");
		Etat initial = new Etat(true,arbreExpr.contientMotVide,0); // construction de l'état Initial
		this.language = arbreExpr;
		this.map.put(arbreExpr,initial);
		this.residueRecursif(arbreExpr, initial,"");
		Automate automate = new Automate();
		automate.ajouteEtatRecursif(initial);
		return automate;
	}
	
	public void residueRecursif(Arbre arbre, Etat etat, String last){
        Set<Character> resultat = new HashSet<Character>();
        char[] lettres = new char[arbre.alphabet().size()];

		int i = 0;
		for (Character c :arbre.alphabet()){
			lettres[i++]=c;
		}
        Arrays.sort(lettres);
		for(char l : lettres){
			Arbre arbreR=arbre.residuel(l,language);
            System.out.println("("+last+l+"¯¹) "+arbre.toString()+" = "+arbreR.toString());
			boolean empile= true;
			for(Arbre a : this.map.keySet()){
				if(a.toString().equals(arbreR.toString())&&(((a.contient1||a.contientMotVide)&&(arbreR.contient1||arbreR.contientMotVide))||((!a.contientMotVide&&!a.contient1)&&(!arbreR.contient1&&!arbreR.contientMotVide)))){
					etat.ajouteTransition(l,map.get(a));
					empile=false;
					break;
				}
			}
			
			if (empile && arbreR.symbole!='0'){
				Etat e = new Etat(false,arbreR.contientMotVide||arbreR.contient1,map.size());
				etat.ajouteTransition(l,e);
				map.put(arbreR, e);
                if(arbreR.symbole!='1')tmp.add(new Couple(arbreR, e,last+l));
			}
		}
        if(!tmp.isEmpty()){
            Couple c = tmp.remove();
			residueRecursif(c.a, c.e,c.l);
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

