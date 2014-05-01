import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Binaire extends Arbre{ 
	
	public String toString() {
        if (this.symbole == '+')return ('('+gauche.toString() + this.symbole + droit.toString()+')');
        return (gauche.toString()+droit.toString());

    }
	
	public Binaire(Arbre gauche, Arbre droit, char s) {
        this.gauche = gauche;
        this.droit = droit;
        this.symbole = s;
        this.contientMotVide = ((this.gauche.contientMotVide||this.droit.contientMotVide||this.droit.epsilon ||this.gauche.epsilon)&&s=='+') || (((this.gauche.contientMotVide||this.gauche.epsilon)&&(this.droit.contientMotVide||this.droit.epsilon))&&s=='.');
        if(s=='+'){
        	this.premiers.addAll(this.gauche.premiers);
        	this.premiers.addAll(this.droit.premiers);
        	this.derniers.addAll(this.droit.derniers);
        	this.derniers.addAll(this.gauche.derniers);
        }
        else if(s=='.'){
        	this.premiers.addAll(this.gauche.premiers);
        	this.derniers.addAll(this.droit.derniers);
        	if(this.gauche.contientMotVide)this.premiers.addAll(this.droit.premiers);
        	if(this.droit.contientMotVide)this.derniers.addAll(this.gauche.derniers);
        }
	}
	
	 public Map<Feuille, Set<Feuille>> succ(){
		Map<Feuille, Set<Feuille>> succ = new HashMap<Feuille, Set<Feuille>>();
		succ.putAll(gauche.succ());
		succ.putAll(droit.succ());
			if (symbole == '.'){
				for (Feuille f : gauche.derniers) {
		            ((HashSet<Feuille>)succ.get(f)).addAll(droit.premiers);
		        }
			}
		return succ;
		 
	 }

	@Override
	Arbre residuel(char c) {
		Arbre arbre = new Feuille('0');
		if(this.symbole=='.'){
			Arbre arbre1 = new Binaire(gauche.residuel(c),droit,'.');
			if(gauche.contientMotVide||gauche.epsilon){
				arbre1= new Binaire(arbre1.simplification(),droit.residuel(c),'+');
			}
			return arbre1.simplification();
		}
		else if(this.symbole=='+'){
			return new Binaire(gauche.residuel(c),droit.residuel(c),'+').simplification();
		}
		return arbre;
	}
	

	@Override
	public HashSet<String> contientArbre() {
		HashSet<String> set = new HashSet<String>();
		if(this.symbole == '+'){
			set.addAll(gauche.contientArbre());
			set.addAll(droit.contientArbre());
			return set;
		}
		set.add(this.toString());
		return set;
	}

	@Override
	Arbre simplification() {
		if(this.symbole=='+'){
			if(gauche.symbole == '0' && droit.symbole == '0') return new Feuille('0');
			else if(gauche.symbole == '0')return droit.simplification(); // si gauche est vide on revoi juste la simplification de droit
			else if(droit.symbole == '0')return gauche.simplification(); // si droit est vide on revoi juste la simplification de gauche
			else if(gauche.symbole == 'Ɛ'){
				droit.epsilon =true;
				return droit.simplification();
			}
			else if(droit.symbole == 'Ɛ'){
				gauche.epsilon =true;
				return gauche.simplification();
			}
			for(String S:gauche.contientArbre()){
				if(droit.toString().matches(S))return gauche.simplification();
			}
			for(String S:droit.contientArbre()){
				if(gauche.toString().matches(S))return droit.simplification();
			}
		}
		else if(this.symbole=='.'){
			if(gauche.symbole == '0' || droit.symbole == '0')return new Feuille('0');
			else if(gauche.symbole == 'Ɛ') return droit.simplification();
			else if(droit.symbole == 'Ɛ') return gauche.simplification();
			else if(gauche.epsilon){
				gauche.epsilon =false;
                if(!gauche.contientMotVide)return new Binaire(this,droit,'+').simplification();
			}
			else if(droit.epsilon){
				droit.epsilon =false;
                if(!droit.contientMotVide)return new Binaire(this,gauche,'+').simplification();
			}
		}
		return this;
	}
	
}