import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Feuille extends Arbre{

	public Feuille(char s) {
        this.symbole = s;
        this.contientMotVide = (this.symbole == 'Ɛ');
        this.premiers.add(this);
        this.derniers.add(this);
    }

    public String toString(){
        return ("" + this.symbole);
    }
	
	@Override
	public Map<Feuille,Set<Feuille>> succ(){
		Map<Feuille, Set<Feuille>> succ = new HashMap<Feuille, Set<Feuille>>();
        succ.put(this, new HashSet<Feuille>());
        return succ;
	}

	@Override
	Arbre residuel(char c) {
        if (c == this.symbole){
            return new Feuille('Ɛ'); // on renvoi une feuille correcpondant au mot vide (epsilon)
        }
		return new Feuille('0'); // on renvoi une feuille corrependant a l'ensemble vide 0
	}

	@Override
	public HashSet<String> contientArbre() { // le seul sous-arbre qu'une feuille contient  est elle meme
		HashSet<String> set = new HashSet<String>();
		set.add(this.toString());
		return set;
	}

	@Override
	Arbre simplification() { // la simplification d'une feuille est elle meme
		return this;
	}
	
}