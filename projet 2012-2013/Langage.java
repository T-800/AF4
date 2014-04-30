import java.util.Set;
import java.util.HashSet;

public class Langage {

	Set<String> mots;

	Langage() {
		this.mots = new HashSet<String>();
	}

	Langage(String chaines[]) {
		this();
		for (int i = 0; i < chaines.length; i++) {
			ajoute(chaines[i]);
		}
	}

	public void ajoute(String u) {
		boolean b = mots.add(u);
		// if(b == true) System.out.println("Succesfull");
		// else System.out.println("Echec");
	}

	public Langage inter(Langage L) {
		Langage Res = new Langage();
		for (String e : this.mots) {
			// if (L.mots.contains(e)) Res.ajoute(e);
			for (String f : L.mots) {
				if (e.equals(f))
					Res.ajoute(e);
			}
		}
		return Res;
	}

	public Langage union(Langage L) {
		Langage Res = new Langage();

		/*
		 * for(String e: this.mots){ Res.ajoute(e); }
		 */
		Res.mots.addAll(this.mots);
		/*
		 * for(String f: L.mots){ if(!Res.mots.contains(f)) Res.ajoute(f); }
		 */
		/*
		 * for(String f: L.mots){ Res.ajoute(f); }
		 */
		Res.mots.addAll(L.mots);

		return Res;
	}

	public Langage concat(Langage L) {
		Langage Res = new Langage();
		for (String e : this.mots) {
			for (String f : L.mots) {
				Res.ajoute(e + f);
			}
		}
		return Res;

	}

	public Langage difference(Langage L) {
		Langage Res = new Langage();
		for (String e : this.mots) {
			// if( !L.mots.contains(e)) Res.ajoute(e);

			for (String f : L.mots) {
				if (e.equals(f))
					Res.mots.remove(e);
			}
		}
		return Res;
	}

	public static String miroirMot(String u) {
		String res = "";

		for (int i = u.length(); i > 0; i--) {
			res += u.substring(i - 1, i); // ou charAt
		}
		return res;
	}

	public Langage miroir() {
		Langage Res = new Langage();
		for (String e : this.mots) {
			String temp = miroirMot(e);
			Res.ajoute(temp);
		}
		return Res;
	}

	public String toString() {
		String res = "{ ";
		for (String u : mots) {
			if (u.length() == 0) {
				res += "mot_vide ";
			} else {
				res += u.toString() + " ";
			}
		}
		return res + "}";
	}

	public static void main(String args[]) {
		// tester chacune des methodes au fur et a mesure que vous les ecrivez
		Langage L = new Langage(args);
		L.ajoute("aline");
		L.ajoute("marie");
		L.ajoute("camille");
		System.out.println(L);

		Langage M = new Langage(args);
		M.ajoute("a");
		M.ajoute("b");
		System.out.println(M);

		System.out.println("Nouveau langage :  " + L.concat(M));

		System.out.println(miroirMot("aline"));
		L = L.miroir();
	}

}