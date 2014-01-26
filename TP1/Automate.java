
public class Automate extends EnsEtat {

    private EnsEtat initiaux;

    public Automate() {
        super();
        initiaux = new EnsEtat();
    }

    public EnsEtat getInitiaux() {
        return initiaux;
    }
}
