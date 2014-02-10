
import java.util.HashSet;

public class EnsEtat extends HashSet<Etat> {

	public EnsEtat() {
	}

      public String toString(){
         String s = "";
         for(Etat e : this){
            s += e.id+" ";
         }
         return s;
      }
}
