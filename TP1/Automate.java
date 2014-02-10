
public class Automate extends EnsEtat {

    private EnsEtat initiaux;

    public Automate() {
        super();
        initiaux = new EnsEtat();
    }

    public EnsEtat getInitiaux() {
        return initiaux;
    }

    /* Question 3*/
    boolean ajouteEtatSeul(Etat e){
        if (e.init){
            initiaux.add(e);
        }
        return this.add(e);
    }

    /* Question 4*/
    boolean ajouteEtatRecursif(Etat e){

        if(ajouteEtatSeul(e)){
            for(Etat ens : e.succ()){
                ajouteEtatRecursif(ens);

            }
            return true;
        }
        return false;
    }

    boolean estDeterministe(){
        if(this.initiaux.size()>1)return false;
         for( Etat e : this ){
            for( EnsEtat ensetat : e.transitions.values() ){
                if(ensetat.size()>1) return false;
            }
         }
         return true;
    }

    public String toString(){
        for()
    }
}

