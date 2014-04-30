import java.util.*;

public class Binaire extends Arbre {

    Arbre gauche;
    Arbre droit;

    public Binaire(Arbre gauche,Arbre droit,char s){
        this.gauche=gauche;
        this.droit=droit;
        this.symbole=s;
        this.premiers = new HashSet<Feuille>();
        if (this.symbole == '+'){
             this.premiers.addAll(this.gauche.premiers);
             this.premiers.addAll(this.droit.premiers);
        }
        else{
            this.premiers.addAll(this.gauche.premiers);
        }
    }

    public String toString(){
        return ("("+gauche.toString()+" "+this.symbole+" "+droit.toString()+")");
    }

}
