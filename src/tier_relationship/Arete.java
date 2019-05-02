package tier_relationship;

public class Arete {
	private final String id;
	private final Sommet source;
	private final Sommet destination;
	private final int poids;

	public Arete(String id, Sommet source, Sommet destination, int poids) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.poids = poids;
	}

	public String getId() {
		return id;
	}

	public Sommet getDestination() {
		return destination;
	}

	public Sommet getSource() {
		return source;
	}

	public int getPoids() {
		return poids;
	}

	@Override
	public String toString() {
		return id + "   :" + source + " " + destination + " " + "poids :" + poids;
	}

}
