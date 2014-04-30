
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EnsEtat extends HashSet<Etat> {

	public EnsEtat() {
	}
	
	/**
	 * Permet l'affichage d'un ensEtat ou d'un automate
	 */
	public String toString() {
		String res ="{";
		for(Etat e : this) {
			res += e.toString()+ " ";
		}
		return res + "}";
	}
	
	/**
	 * Realise l'union de la fonction succ(c) de Etat sur les etats de l'ensemble.
	 * @param c
	 * @return EnsEtat
	 */
	public EnsEtat succ(char c){
		EnsEtat res = new EnsEtat();
		for(Etat e : this){
			if(e.succ(c)!= null)
				res.addAll(e.succ(c));
		}
		return res;
	}
	
	/**
	 * Realise l'union de la fonction succ() de Etat sur les etats de l'ensemble.
	 * @return EnsEtat
	 */
	public EnsEtat succ(){
		EnsEtat res = new EnsEtat();
		for(Etat e : this){
			res.addAll(e.succ());
		}
		return res;
	}
	/**
	 * 
	 * @return
	 */
	public Etat donne1(){
		Iterator<Etat> i=this.iterator();	
		return i.next();
	}
	/**
	 * Verifie si l'ens. contient un etat terminal ou non.
	 * @return boolean
	 */
	public boolean contientTerminal(){
		for(Etat e : this){
			if(e.isTerm()) return true;
		}
		return false;
	}

	/**
	 * Verifie si une partie d'un mot est accepte par un EnsEtat.
	 * @param a mot qui doit etre accepte ou non
	 * @param i indice de debut de lecture du mot
	 * @return boolean
	 */
	public boolean accepte(String a, int i){
			if(i == a.length()) 
				return this.contientTerminal();
			else 
				return this.succ(a.charAt(i)).accepte(a, i+1);
	}
	
	/** Renvoie l'ens. des lettres etiquettant les transitions partant de l'ens. d'etat
	 * @return
	 */
	public Set<Character> alphabet(){
		Set<Character> res = new HashSet<Character>();
		for (Etat e : this) {
			res.addAll(e.alphabetE());
		}
		return res;
	}
	
}