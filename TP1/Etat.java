
import java.util.HashMap;
import java.util.Set;

public class Etat {

	HashMap<Character, EnsEtat> transitions;
	boolean init;
	boolean term;
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
		this.init = init;
		this.term = term;
		this.id = id;
	}

	public Etat(boolean estInit, boolean estTerm) {
		this.init = estInit;
		this.term = estTerm;
		this.transitions = new HashMap<Character, EnsEtat>();
	}

	public boolean isInit() {
		return init;
	}

	public boolean isTerm() {
		return term;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public void setTerm(boolean term) {
		this.term = term;
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

	/**2) Implémentation des automates**/

	/* Question 1 */
	public EnsEtat succ(char c){
		return transitions.get(c);
	}

	public EnsEtat succ(){
		EnsEtat e = new EnsEtat();
		for(char c : transitions.keySet()){
			e.addAll(this.succ(c));
		}
		return e;
	}

	/* Question 2*/

	void ajouteTransition(char c, Etat e){
		EnsEtat tmp = transitions.get(c);
		if(tmp == null){
			tmp = new EnsEtat();
			tmp.add(e);
			transitions.put(c,tmp);
		}
		else {
			tmp.add(e);
			transitions.put(c,tmp);
		}
	}

	public String toString(){
		String s = "initial :"+  this.init+"\n";
		if(this.term)s+= "terminal";
		for(char  key : transitions.keySet()){
			s+=key+" -> ";
			s+=transitions.get(key).toString();

			s+="\n";
		}
	return s;
	}


}
