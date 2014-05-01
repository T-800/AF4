import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Unaire extends Arbre{
	
	public String toString() {
        if(this.fils instanceof Feuille)return (fils.toString()+ this.symbole);
        return ("(" + fils.toString() + ")" + this.symbole);
    }
	
	public Unaire(Arbre fils, char s) {
        this.symbole = s;
        this.fils = fils;
        this.contientMotVide= true;
        this.premiers.addAll(this.fils.premiers);
        this.derniers.addAll(this.fils.derniers);
    }
	
    public Map<Feuille, Set<Feuille>> succ() {
    	Map<Feuille, Set<Feuille>> succ = fils.succ();
        for (Feuille f : derniers) {
            succ.get(f).addAll(premiers);
        }
        return succ;
    }

	@Override
	Arbre residuel(char c) { //
		if((fils.residuel(c).toString().equals(fils.toString()))) // si le residuel de mon fils est égal a moi meme je me renvoi
			return this.simplification();
		else if(fils.residuel(c).contientMotVide)
			return this.simplification();
		else 
			return new Binaire(fils.residuel(c),this,'.').simplification(); // je renvoi le residuel de mon fils concatener a moi-même ex (ab)* ->  b(ab)*
	}
	
	Arbre simplification(){
		if(fils.symbole == '*')return fils.simplification();// appel recursif
		else if(fils.symbole == '+'){
			if(fils.gauche.symbole=='*'){
				Arbre a1 = new Binaire(fils.gauche.fils.simplification(),fils.droit.simplification(),'+');
				Arbre a2 = new Unaire(a1.simplification(),'*');
				return a2.simplification();
			}
			if(fils.droit.symbole=='*'){
				Arbre a1 = new Binaire(fils.droit.fils.simplification(),fils.gauche.simplification(),'+');
				Arbre a2 = new Unaire(a1.simplification(),'*');
				return a2.simplification();
			}
		}
		return this;
	}

	@Override
	public HashSet<String> contientArbre() {
		HashSet<String> set = new HashSet<String>();
		if(fils.symbole == '+'){
			for(String s : fils.contientArbre())set.add(s.concat(this.symbole.toString()));
		}
		set.add(this.toString());
		return set;
	}
}
