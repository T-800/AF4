import java.util.*;
public class Feuille extends Arbre{

    public Feuille(char s){
        this.symbole=s;
        this.premiers = new HashSet<Feuille>();
        this.premiers.add(this);
    }

    public String toString(){
        return (""+this.symbole);
    }
}
