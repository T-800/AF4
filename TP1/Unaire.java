import java.util.*;

public class Unaire extends Arbre {

    Arbre fils;

    public Unaire(Arbre f, char s){
        this.symbole=s;
        this.fils=f;
        this.premiers = new HashSet<Feuille>();
        this.premiers.addAll(this.fils.premiers);
    }

    public String toString() {
        return ("("+fils.toString()+")"+this.symbole);
    }
}
