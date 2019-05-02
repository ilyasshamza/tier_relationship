package tier_relationship;

import java.util.List;

public class Graph {
	private final List<Sommet> sommets;
	private final List<Arete> aretes;

	public Graph(List<Sommet> vertexes, List<Arete> edges) {
		this.sommets = vertexes;
		this.aretes = edges;
	}

	public List<Sommet> getSommet() {
		return sommets;
	}

	public List<Arete> getArete() {
		return aretes;
	}

}