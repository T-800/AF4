
public class couple {
	
	Etat eE1;
	Etat eE2;
	int id;
	
	public couple() {
	}

	public couple(int id) {
		this.id = id;
	}

	public couple(Etat e1, Etat e2, int id) {
		this.eE1 = eE1;
		this.eE2 = eE2;
		this.id = id;
	}

	public couple(Etat e1, Etat e2) {
		this.eE1 = eE1;
		this.eE2 = eE2;
	}
	
	public Etat getE1(){
		return eE1;
	}
	
	public Etat getE2(){
		return eE2;
	}

	public void setE1(Etat e) {
		this.eE1 = e;
	}
	public void setE2(Etat e) {
		this.eE2 = e;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			final Etat other = (Etat) obj;
			return (id == other.id);
		}
	}
}