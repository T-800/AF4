import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Etat {

	HashMap<Character, EnsEtat> transitions;
	boolean estInit;
	boolean estTerm;
	int id;

	public Etat() {
		this.transitions = new HashMap<Character, EnsEtat>();
	}

	public Etat(int id) {
		this.transitions = new HashMap<Character, EnsEtat>();
		this.id = id;
	}

	public Etat(boolean init, boolean term, int id) {
		this.transitions = new HashMap<Character, EnsEtat>();
		this.estInit = init;
		this.estTerm = term;
		this.id = id;
	}

	public Etat(boolean estInit, boolean estTerm) {
		this.estInit = estInit;
		this.estTerm = estTerm;
		this.transitions = new HashMap<Character, EnsEtat>();
	}

	public boolean isInit() {
		return estInit;
	}

	public boolean isTerm() {
		return estTerm;
	}
	
	/**
	 * gere le boolean Init d'un état
	 * @param init
	 */
	public void setInit(boolean init) {
		this.estInit = init;
	}

	/**
	 * Gere le boolean term d'un etat
	 * @param term
	 */
	public void setTerm(boolean term) {
		this.estTerm = term;
	}

	/**
	 * Renvoie les successuer de l'etat courant e pour la lettre c
	 * 
	 * @param c
	 * @return
	 */
	public EnsEtat succ(char c) {
		EnsEtat ens = new EnsEtat();
		if(transitions.get(c)!=null)
			ens.addAll(transitions.get(c));
		return ens;
	}

	/**
	 * Renvoie tous les successuer de l'etat courant e pour toutes les clefs
	 * 
	 * @return
	 */
	public EnsEtat succ() {
		EnsEtat res = new EnsEtat();
		for (char c : transitions.keySet()) {
			res.addAll(succ(c));
		}
		return res;
	}

	/**
	 * Permet d'ajouter une transition vers l'etat e
	 * 
	 * @param c
	 * @param e
	 */
	public void ajouterTransition(char c, Etat e) {
		if (transitions.get(c) != null) {
			transitions.get(c).add(e);
		} else {
			EnsEtat x = new EnsEtat();
			x.add(e);
			transitions.put(c, x);
		}
	}
	
	public void supprimerTransitionEpsilon(){
		if(transitions.get('E')!=null){
			transitions.remove('E');
		}
	}

	/**
	 * Permet l'affichage d'un etat
	 */
	public String toString() {
		String res = this.id + "";
		if (this.isInit())
			res += "i";
		if (this.isTerm())
			res += "t";
		res += "[";

		for (char c : this.transitions.keySet()) {
			for (Etat e : succ(c)) {
				res += c + "->" + e.id + " ";
			}
		}
		return res + "]";
	}

	/**
	 * Renvoie l'ens. des lettres etiquettant les transitions partant de l'etat
	 * @return liste de char
	 */
	public Set<Character> alphabetE() {
		Set<Character> res = new HashSet<Character>();
		for (char c : transitions.keySet()) {
			res.add(c);
		}
		return res;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			final Etat other = (Etat) obj;
			return (id == other.id);
		}
	}


	
}