import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Automate extends EnsEtat {

	private EnsEtat initiaux;
	private EnsEtat finaux;

	public Automate() {
		super();
		initiaux = new EnsEtat();
		finaux = new EnsEtat();
	}

	public EnsEtat getInitiaux() {
		return initiaux;
	}

	public EnsEtat getFinaux() {
		return finaux;
	}

	/**
	 * Renvoye false si l'etat est deja present sinon ajoute l'etat et retourne
	 * true
	 * 
	 * @param e
	 * @return
	 */
	boolean ajouteEtatSeul(Etat e) {
		if (this.add(e) == true) {
			if (e.isInit()) {
				initiaux.add(e);
			}
			if (e.isTerm()) {
				finaux.add(e);
			}
			return true;
		}
		return false;
	}

	/**
	 * Ajoute successeurs de e a l'automate
	 * 
	 * @param e
	 * @return
	 */
	boolean ajoutEtatRecurssif(Etat e) {
		if (ajouteEtatSeul(e)) {
			for (Etat f : e.succ()) {
				this.ajoutEtatRecurssif(f);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retourne vrai si l'automate est deterministe, faux sinon
	 * 
	 * @return
	 */
	boolean estDeterministe() {
		for (Etat e : this) {
			for (char c : e.transitions.keySet()) {
				if (e.succ(c).size() >= 2)
					return false;
			}
		}
		return true;
	}

	/**
	 * Verifie si un mot est accepte par l'automate
	 * 
	 * @param s
	 *            mot
	 * @return boolean
	 */
	public boolean Accepte(String s) {
		return this.accepte(s, 0);
	}

	/**
	 * Retourne un automate complet a partir d'un automate quelconque.
	 * 
	 * @return automate complet
	 */
	public Automate complet() {
		Automate a = this;// .suppEpsilonInAuto();
		Etat bottom = new Etat(false, false, (-1));
		for (Etat e : this) {
			for (char c : this.alphabet()) {
				// e.supprimerTransitionEpsilon();
				if (e.succ(c) == null) {
					e.ajouterTransition(c, bottom);
				}
			}
		}
		return a;
	}

	/**
	 * Retourne un automate complet a partir d'un automate quelconque et d'un
	 * alphabet
	 * 
	 * @return automate complet
	 */
	public Automate complet(Set<Character> alpha) {
		Automate a = this;// .suppEpsilonInAuto();
		Etat bottom = new Etat(false, false, (-1));
		for (Etat e : this) {
			for (char c : alpha) {
				// e.supprimerTransitionEpsilon();
				if (e.succ(c) == null ){
					e.ajouterTransition(c, bottom);
				}
			}
		}
		return a;
	}

	/**
	 * Retourne un automate reconnaissant le complementaire du langage reconnu
	 * par l'automate courant.
	 * 
	 * @return automate du langage complementaire
	 */
	public Automate complementaire() {
		Automate a = this.copieAutomate();
		for (Etat e : a) {
			if (e.isTerm())
				e.setTerm(false);
			else
				e.setTerm(true);
		}
		return a;
	}

	/**
	 * renvoie la copie de l'automate courant 
	 * 
	 * @return automate copie
	 */
	public Automate copieAutomate() {
		Automate aCopie = new Automate();
		HashMap<Etat, Etat> h = new HashMap<Etat, Etat>();
		// creer tous les etats
		for (Etat e : this) {
			Etat n = new Etat(e.isInit(), e.isTerm(), e.id);
			h.put(e, n);
			aCopie.ajouteEtatSeul(n);

		}
		for (Etat f : this) {
			Etat n = h.get(f);
			for (char c : this.alphabet()) {
				EnsEtat s = f.succ(c);
				if (s != null) {
					for (Etat b : s) {
						n.ajouterTransition(c, h.get(b));
					}
				}
			}
		}
		return aCopie;
	}

	/**
	 * Renvoie un automate reconnaissant le langage miroir du langage de
	 * l'automate courant
	 * 
	 * @return miroir
	 */
	public Automate miroir() {
		Automate aMiroir = new Automate();
		HashMap<Etat, Etat> h = new HashMap<Etat, Etat>();
		// creer tous les etats
		for (Etat e : this) {
			if (e.isInit() || e.isTerm()) {
				Etat n = new Etat(!e.isInit(), !e.isTerm(), e.id);
				h.put(e, n);
			} else {
				Etat n = new Etat(e.isInit(), e.isTerm(), e.id);
				h.put(e, n);
			}
		}
		// inverser le sens des transistions
		for (Etat f : this) {
			Etat n = h.get(f);
			aMiroir.ajouteEtatSeul(n);
			for (char c : f.transitions.keySet()) {
				EnsEtat s = f.succ(c);
				for (Etat b : s) {
					h.get(b).ajouterTransition(c, n);
				}
			}
		}
		return aMiroir;
	}

	/**
	 * Renvoie un automate reconnaissant l'union des langages reconnus par a et
	 * par l'auto courant
	 * 
	 * @param a
	 * @return union
	 */
	public Automate union(Automate a) {
		Automate union = new Automate();
		HashMap<Etat[], Etat> h = new HashMap<Etat[], Etat>();
		Stack<Etat[]> pile = new Stack<Etat[]>();

		Automate A1 = this.suppEpsilonInAuto().complet().determinise();
		Automate A2 = a.suppEpsilonInAuto().complet().determinise();
		
		Set<Character> alpha = A1.alphabet();
		Set<Character> alpha2 = A2.alphabet();
		alpha.addAll(alpha2);
		System.out.println(alpha);
		
		Automate courant = A1.complet(alpha).determinise().minimisation();
		Automate argument = A2.complet(alpha).determinise().minimisation();
		System.out.println("courant : " + courant);
		System.out.println("          " + A1.complet(alpha).determinise());
		System.out.println("          " + A2.complet(alpha).determinise());
		System.out.println("argument: " + argument);
				
		Etat[] tab0 = new Etat[2];
		// on peut utiliser donne1 car les autos courant et argument sont déterministes et complets donc ils ont un unique etat initial
		tab0[0] = courant.getInitiaux().donne1();
		tab0[1] = argument.getInitiaux().donne1();
		boolean terminal = false;
		if (tab0[0].isTerm() == true || tab0[1].isTerm() == true)
			terminal = true;
		Etat n0 = new Etat(true, terminal, 0);
		union.ajouteEtatSeul(n0);
		h.put(tab0, n0);
		pile.add(tab0);

		int i = 1;
		while (!pile.isEmpty()) {
			Etat[] tableau = pile.pop();
			Etat t = h.get(tableau);
			Etat e1 = tableau[0];
			Etat e2 = tableau[1];
			
			for (char c : alpha) {
				Etat[] tab = new Etat[2];
				System.out.println("test: "+ c + ", succ e1: " + e1.succ(c));
				tab[0] = e1.succ(c).donne1();
				System.out.println("test: "+ c + ", succ e2: " + e2.succ(c));
				tab[1] = e2.succ(c).donne1();

				Etat dest = null;
				for (Etat[] cou : h.keySet()) {
					if (cou[0] == tab[0] && cou[1] == tab[1]) {
						dest = h.get(cou);
					} else {// System.out.println (cou[0]+ " est different de "
							// + tab[0] + " ou " + cou[1]+ " est different de "
							// + tab[1]);
					}
				}
				if (dest == null) {
					boolean term = false;
					if (tab[0].isTerm() == true || tab[1].isTerm() == true)
						term = true;
					Etat x = new Etat(false, term, i);
					i++;
					h.put(tab, x);
					pile.add(tab);
					dest = x;
					union.ajouteEtatSeul(x);
				}
				t.ajouterTransition(c, dest);
			}
		}

		return union;
	}

	/**
	 * Creer l'automate deterministe associe a l'automate courant
	 */
	public Automate determinise() {

		Automate aDet = new Automate();
		HashMap<EnsEtat, Etat> trans = new HashMap<EnsEtat, Etat>();
		int i = 0;
		boolean terminal = false;
		if (this.initiaux.contientTerminal())
			terminal = true;
		Etat n0 = new Etat(true, terminal, i);
		aDet.ajouteEtatSeul(n0);
		i++;
		trans.put(this.initiaux, n0);

		Stack<EnsEtat> pile = new Stack<EnsEtat>();
		pile.add(this.initiaux);

		while (!pile.isEmpty()) {
			EnsEtat a = pile.pop();
			Etat nomA = trans.get(a);

			for (char c : alphabet()) {
				EnsEtat Ens = new EnsEtat();
				Ens = a.succ(c);
				if (!trans.containsKey(Ens)) {
					boolean term = Ens.contientTerminal();
					Etat x = new Etat(false, term, i);
					aDet.ajouteEtatSeul(x);
					trans.put(Ens, x);
					pile.add(Ens);
					nomA.ajouterTransition(c, x);
					i++;
				} else {
					nomA.ajouterTransition(c, trans.get(Ens));
				}
			}
		}
		return aDet;
	}

	/**
	 * Renvoie un automate reconnaissant l'intersection des langages reconnus
	 * par a et par l'auto courant
	 * 
	 * @param a
	 * @return
	 */
	public Automate intersection(Automate a) {
		Automate inter = new Automate();
		HashMap<Etat[], Etat> h = new HashMap<Etat[], Etat>();
		Stack<Etat[]> pile = new Stack<Etat[]>();

		Automate courant = this.complet().determinise();
		Automate argument = a.complet().determinise();

		Etat[] tab0 = new Etat[2];
		tab0[0] = argument.getInitiaux().donne1();
		tab0[1] = courant.getInitiaux().donne1();
		boolean terminal = false;
		if (tab0[0].isTerm() == true && tab0[1].isTerm() == true)
			terminal = true;
		Etat n0 = new Etat(true, terminal, 0);
		inter.ajouteEtatSeul(n0);
		h.put(tab0, n0);
		pile.add(tab0);

		int i = 1;
		while (!pile.isEmpty()) {
			Etat[] tableau = pile.pop();
			Etat t = h.get(tableau);
			Etat e1 = tableau[0];
			Etat e2 = tableau[1];

			for (char c : alphabet()) {
				Etat[] tab = new Etat[2];
				tab[0] = e1.succ(c).donne1();
				tab[1] = e2.succ(c).donne1();

				Etat dest = null;
				for (Etat[] cou : h.keySet()) {
					if (cou[0] == tab[0] && cou[1] == tab[1]) {
						dest = h.get(cou);
					} else {// System.out.println (cou[0]+ " est different de "
							// + tab[0] + " ou " + cou[1]+ " est different de "
							// + tab[1]);
					}
				}
				if (dest == null) {
					boolean term = false;
					if (tab[0].isTerm() == true && tab[1].isTerm() == true)
						term = true;
					Etat x = new Etat(false, term, i);
					i++;
					h.put(tab, x);
					pile.add(tab);
					dest = x;
					inter.ajouteEtatSeul(x);
				}
				t.ajouterTransition(c, dest);
			}
		}
		return inter;
	}

	/**
	 * test l'egalite des langages de 2 Automates
	 * 
	 * @param a
	 * @return
	 */
	public boolean sontEgaux(Automate b) {

		Automate A = this.complet().determinise();
		Automate B = b.complet().determinise();

		// construction de l'auto C du produit cartÃ©sien de A et B
		Automate x = A.intersection(B.complementaire());
		Automate y = B.intersection(A.complementaire());
		Automate C = x.union(y); // rq on sait que les Ã©tat de C sont
									// accessibles, par construction
		// du coup il suffit d'analyser et de faire la recuperation des Etats
		// finaux de C dans un ensemble d'etats
		EnsEtat ens = new EnsEtat();
		for (Etat e : C) {
			if (e.isTerm()) {
				ens.add(e);
			}
		}
		// Verification que cet ens. d'etats est vide
		return (ens.isEmpty());
	}

	/**
	 * Minimise un automate
	 * 
	 * @return
	 */
	public Automate minimisation() {
		Automate mini = this.copieAutomate();
		return mini.miroir().determinise().miroir().determinise();
	}

	/**
	 * substitue une lettre "a" a chaque lettre "b" d'un automate b->a
	 * 
	 * @return
	 */
	public Automate substitue(char b, char a) {
		Automate A = this;
		for (Etat e : this) {
			if (!e.succ(b).isEmpty()) {
				EnsEtat s = e.succ(b);
				System.out.println("mes etat : " + s);
				EnsEtat x = e.transitions.remove(b);
				System.out.println("mes etat res: " + x);
				EnsEtat etat_de__trans_a = e.transitions.get(a);
				EnsEtat modif = s;
				for (Etat l : etat_de__trans_a) {
					modif.add(l);
				}
				EnsEtat y = e.transitions.put(a, modif);
				System.out.println("res y : " + e.transitions.get(a));
			}
		}
		return A;

	}

	/**
	 * genere un auto a une lettre de longeur u*i+v avec i>=0
	 * 
	 * @param u
	 * @param v
	 * @param a
	 * @return
	 */
	public static Automate genereAuto1Lettre(int u, int v, char c) {
		Automate res = new Automate();
		HashMap<String, Etat> h = new HashMap<String, Etat>();
		Etat n0 = new Etat(true, false, 0);
		h.put("n" + 0, n0);
		res.initiaux.add(n0);

		Etat n = n0;
		for (int i = 1; i <= v; i++) {
			n = new Etat(false, false, i);
			h.put("n" + i, n);
			h.get("n" + (i - 1)).ajouterTransition(c, n);
		}
		n.setTerm(true);

		if (u != 0) {
			Etat n1 = n;
			for (int i = v + 1; i < v + u; i++) {
				System.out.println("u");
				n = new Etat(false, false, i);
				h.put("n" + i, n);
				h.get("n" + (i - 1)).ajouterTransition(c, n);
			}
			n.ajouterTransition(c, n1);
		}

		res.ajoutEtatRecurssif(n0);
		return res;
	}

	/**
	 * Test si le langage reconnu pa l'auto courant est de longeur de la forme
	 * u*i+v avec i>=0
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	// TODO a tester
	public boolean testLongeurAutomate(int u, int v) {
		Automate A = this.complet().determinise();
		// substituer toutes les lettre de l'auto A en 'a'
		for (char c : alphabet()) {
			if (c != 'a')
				A.substitue(c, 'a');
		}
		Automate B = genereAuto1Lettre(u, v, 'a');
		Automate C = A.intersection(B);
		return A.sontEgaux(C);
	}

	/**
	 * return la cloture d'un ensemble d'etat ens de l'automate courant
	 * 
	 * @param ens
	 * @return
	 */
	public EnsEtat cloture(EnsEtat ens) {
		EnsEtat clot = new EnsEtat();
		HashMap<Etat, Etat> h = new HashMap<Etat, Etat>();
		Stack<Etat> pile = new Stack<Etat>();
		// ajouter les etats de ens dans la cloture
		for (Etat j : ens) {
			pile.add(j);
			clot.add(j);
			h.put(j, j);
		}

		while (!pile.isEmpty()) {
			Etat a = pile.pop();
			EnsEtat e = a.succ('E');
			if (e != null) {
				for (Etat f : e) {
					clot.add(f);
					if (h.get(f) == null) {
						pile.add(f);
						h.put(f, f);
					}
				}
			}
		}
		return clot;
	}

	/**
	 * return la cloture d'un etat e
	 * 
	 * @param e
	 * @return
	 */
	public EnsEtat clotureE(Etat e) {
		EnsEtat clot = new EnsEtat();
		HashMap<Etat, Etat> h = new HashMap<Etat, Etat>();
		Stack<Etat> pile = new Stack<Etat>();
		// ajouter les etats de ens dans la cloture
		pile.add(e);
		clot.add(e);
		h.put(e, e);

		while (!pile.isEmpty()) {
			Etat a = pile.pop();
			EnsEtat g = a.succ('E');
			if (!g.isEmpty()) {
				for (Etat f : g) {
					clot.add(f);
					if (h.get(a) == null)
						pile.add(f);

				}
			}
		}
		return clot;
	}

	// TODO a verifier avec un autre auto normalement ok (rÃ©solut normalement
	// :pb avec les terminaux)
	/**
	 * creer un automate sans E-transitions a partir d'un automates avec de
	 * E-transision
	 * 
	 * @return
	 */
	public Automate suppEpsilonInAuto() {
		Automate supp = new Automate();
		HashMap<EnsEtat, Etat> h = new HashMap<EnsEtat, Etat>();
		int i = 0;
		EnsEtat clot0 = cloture(this.initiaux);
		// System.out.println("clot0 : " + clot0);
		boolean terminal = clot0.contientTerminal();
		Etat n0 = new Etat(true, terminal, i);
		supp.ajouteEtatSeul(n0);
		i++;
		h.put(clot0, n0);

		Stack<EnsEtat> pile = new Stack<EnsEtat>();
		pile.add(clot0);

		while (!pile.isEmpty()) {
			EnsEtat a = pile.pop();
			Etat nomA = h.get(a);

			for (char c : alphabet()) {
				if (c != 'E') { // alphabet privee de 'E'
					EnsEtat Ens = new EnsEtat();
					Ens = cloture(a.succ(c));
					if (!Ens.isEmpty()) {
						if (!h.containsKey(Ens)) {
							boolean term = Ens.contientTerminal();
							Etat x = new Etat(false, term, i);
							supp.ajouteEtatSeul(x);
							h.put(Ens, x);
							pile.add(Ens);
							nomA.ajouterTransition(c, x);
							i++;
						} else {
							nomA.ajouterTransition(c, h.get(Ens));
						}
					}
				}
			}
		}
		return supp;
	}

	// TODO a tester avec un autre auto que a1 et retrecir
	/**
	 * return un automate faisant l'union de 2 automates en utilaisant des
	 * E-transitions
	 * 
	 * @param b
	 * @return
	 */
	public Automate unionEpsilon(Automate b) {
		Automate res = new Automate();
		Automate A = this.copieAutomate();
		Automate B = b.copieAutomate();
		// hashMap des auto A et B
		HashMap<Etat, Etat> h = new HashMap<Etat, Etat>();
		HashMap<Etat, Etat> h2 = new HashMap<Etat, Etat>();

		// creation nouvel etat initial
		int i = 0;
		Etat n0 = new Etat(true, false, i);
		i++;
		res.ajouteEtatSeul(n0);
		// ajout des etats de A et B dans res et renomage des Etats
		for (Etat e : A) {
			Boolean term = e.isTerm();
			Etat n = new Etat(false, term, i);
			h.put(e, n);
			res.ajouteEtatSeul(n);
			i++;
		}
		for (Etat e : A) {
			Etat n = h.get(e);
			for (char c : e.transitions.keySet()) {
				EnsEtat ens = e.succ(c);
				for (Etat f : ens)
					n.ajouterTransition(c, h.get(f));
			}
		}
		for (Etat e : B) {
			Boolean term = e.isTerm();
			Etat n = new Etat(false, term, i);
			h2.put(e, n);
			res.ajouteEtatSeul(n);
			i++;
		}
		for (Etat e : B) {
			Etat n = h2.get(e);
			for (char c : e.transitions.keySet()) {
				EnsEtat ens = e.succ(c);
				for (Etat f : ens)
					n.ajouterTransition(c, h2.get(f));
			}
		}
		// creation des E-transitions qui relient les 2 automates A et B
		for (Etat e : A.initiaux) {
			Etat x = h.get(e);
			n0.ajouterTransition('E', x);
		}
		for (Etat e : B.initiaux) {
			Etat x = h2.get(e);
			n0.ajouterTransition('E', x);
		}
		return res;
	}

	// TODO a essayer de retrecire
	public Automate concatenation(Automate b) {
		Automate res = new Automate();
		Automate A = this.copieAutomate();
		Automate B = b.copieAutomate();
		// hashMap des auto A et B
		HashMap<Etat, Etat> h = new HashMap<Etat, Etat>();
		HashMap<Etat, Etat> h2 = new HashMap<Etat, Etat>();

		int i = 0;
		// ajout des etats de A et B dans res et renomage des Etats
		for (Etat e : A) {
			Boolean init = e.isInit();
			Etat n1 = new Etat(init, false, i);
			h.put(e, n1);
			res.ajouteEtatSeul(n1);
			i++;
		}
		for (Etat e : A) {
			Etat n1 = h.get(e);
			for (char c : e.transitions.keySet()) {
				EnsEtat ens = e.succ(c);
				for (Etat f : ens)
					n1.ajouterTransition(c, h.get(f));
			}
		}

		// creation de l'etat intermediaire
		Etat n = new Etat(false, false, i);
		i++;
		res.ajouteEtatSeul(n);

		for (Etat e : B) {
			Boolean term = e.isTerm();
			Etat n2 = new Etat(false, term, i);
			h2.put(e, n2);
			res.ajouteEtatSeul(n2);
			i++;
		}
		for (Etat e : B) {
			Etat n2 = h2.get(e);
			for (char c : e.transitions.keySet()) {
				EnsEtat ens = e.succ(c);
				for (Etat f : ens)
					n2.ajouterTransition(c, h2.get(f));
			}
		}
		// creation des E-transitions qui relient l'automate A a l'etat
		// intermediaire puis l'etat intermediaire a l'automate B
		for (Etat e : A.finaux) {
			Etat x = h.get(e);
			x.ajouterTransition('E', n);
		}
		for (Etat e : B.initiaux) {
			Etat x = h2.get(e);
			n.ajouterTransition('E', x);
		}
		return res;
	}

	// TODO retrecir
	/**
	 * retourne un automate reconnaissant l'etoile du langage de l'automate
	 * courant avec E-transitions
	 * 
	 * @return
	 */
	public Automate etoile() {
		Automate res = new Automate();
		Automate A = this.copieAutomate();
		HashMap<Etat, Etat> h = new HashMap<Etat, Etat>();

		// creation nouvel etat initial
		int i = 0;
		Etat n0 = new Etat(true, false, i);
		i++;
		res.ajouteEtatSeul(n0);

		for (Etat e : A) {
			Etat n = new Etat(false, false, i);
			h.put(e, n);
			res.ajouteEtatSeul(n);
			i++;
		}
		for (Etat e : A) {
			Etat n = h.get(e);
			for (char c : e.transitions.keySet()) {
				EnsEtat ens = e.succ(c);
				for (Etat f : ens)
					n.ajouterTransition(c, h.get(f));
			}
		}

		// creation des E-transitions qui relie l'ï¿œtat initial
		// de res et A
		for (Etat e : A.initiaux) {
			Etat x = h.get(e);
			n0.ajouterTransition('E', x);
		}

		// creation nouvel etat final
		Etat nend = new Etat(false, true, i);
		i++;
		res.ajouteEtatSeul(nend);
		// creation des E-transitions qui relie l'ï¿œtat final de res et A
		for (Etat e : A.finaux) {
			Etat x = h.get(e);
			x.ajouterTransition('E', nend);
		}
		nend.ajouterTransition('E', n0);
		n0.ajouterTransition('E', nend);
		return res;
	}

	/**
	 * retourne l'automate avec des E-transition de l'expression (postfixe) expr
	 * en argument
	 * 
	 * @param expr
	 * @return
	 */
	public static Automate exprToAutoEpsilon(String expr) {
		Automate res = new Automate();
		Stack<Automate> pile = new Stack<Automate>();
		int i = 0;
		while (i != expr.length()) {
			char C = expr.charAt(i);

			if (C == '.') {
				Automate a1 = pile.pop();
				Automate a2 = pile.pop();
				pile.add(a2.concatenation(a1));
			} else if (C == '*') {
				Automate a = pile.pop();
				pile.add(a.etoile());
			} else if (C == '+') {
				Automate a1 = pile.pop();
				Automate a2 = pile.pop();
				pile.add(a2.unionEpsilon(a1));
			} else {
				for (char c : alphabet(expr)) {
					if (C == c) {
						pile.add(genereAuto1Lettre(0, 1, c));
					}
				}
			}
			i++;
		}
		res = pile.pop();
		if (!pile.isEmpty())
			return null;
		return res;
	}

	/**
	 * retourne l'alphabet d'une expresssion
	 * 
	 * @param expr
	 * @return
	 */
	public static Set<Character> alphabet(String expr) {
		Set<Character> alpha = new HashSet<Character>();
		for (int i = 0; i < expr.length(); i++) {
			char C = expr.charAt(i);
			if (C != '+' && C != '*' && C != '.' && C != '(' && C != ')') {
				alpha.add(C);
			}
		}
		return alpha;
	}

}