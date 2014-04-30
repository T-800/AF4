import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class Lecteur {




	String fichier;

	Lecteur(String f){
		this.fichier = f;
	}

	// c'est le toString() du fichier
	public String lecture() {

		String chaine = "";

		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(this.fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				chaine += ligne + "\n";
			}
			br.close();
		} catch (Exception e) {
			System.out.println("erreur : " +e.toString());
		}
		return chaine;
	}
	public  Automate fichierToAutomate(String chaine){
		Automate B = new Automate();
		HashMap<Integer, Etat> h = new HashMap<Integer, Etat>();
		String ligne;
		BufferedReader br;
		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(this.fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			// la premiere ligne contient le type
			ligne = br.readLine();
			if (ligne.equals("automate")){
				ligne = br.readLine();
				String[] etats = ligne.split(",");
				for(int i =0; i<(etats.length); i+=3){
					Etat nouv = new Etat(Integer.parseInt(etats[i]));
					Boolean init = etats[i+1].equals("y");
					nouv.setInit(init);
					Boolean term = etats[i+2].equals("y");
					nouv.setTerm(term);
					h.put(Integer.parseInt(etats[i]), nouv);
					B.ajouteEtatSeul(nouv);
				}
				while ((ligne = br.readLine()) != null) {
					for(int i = 0; i<(etats.length)	; i++){
						String[] valeurs = ligne.split(",");
						int etatdep = Integer.parseInt(valeurs[0]);
						int etatfin = Integer.parseInt(valeurs[2]);
						String alpha = valeurs[1];
						char trans = alpha.charAt(0);
						h.get(etatdep).ajouterTransition(trans , h.get(etatfin));
	 			 	}
	 			}
					// chaine += ligne + "\n";
			}else {// type rationnel
				ligne = br.readLine();
				B = Automate.exprToAutoEpsilon(ligne);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("erreur : " +e.toString());
		}

	return B;

	}

	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrer le fichier a charger : ");
		String fichier = sc.nextLine();
		Lecteur l = new Lecteur("/home/netbook/workspace/projetAF4/src/" + fichier);
		String chaine = l.lecture();
		System.out.println(chaine);
		System.out.println("         "  +l.fichierToAutomate(chaine));


	}
}
