package tier_relationship;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Init {
	private static List<Sommet> sommets;
	private static List<Arete> Aretes;

	// permet dajouter des sommets avec des poids a notre liste
	private static void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
		Arete lane = new Arete(laneId, sommets.get(sourceLocNo), sommets.get(destLocNo), duration);
		Aretes.add(lane);
	}

	private static boolean exist(int sourceLocNo, int destLocNo) {
		for (Arete Arete : Aretes) {
			if (Arete.getDestination() == sommets.get(destLocNo) && Arete.getSource() == sommets.get(sourceLocNo))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {

		sommets = new ArrayList<Sommet>();
		Aretes = new ArrayList<Arete>();

		for (int i = 0; i < 100; i++) {
			Sommet sommet = new Sommet("" + i);
			sommets.add(sommet);
		}
		int valeur;
		double proba;
		// niveau 1
		for (int i = 0; i < 8; i++) {
			proba = Math.random();
			// 75 pourcent de chance quils soit relié entre eux
			// math.rand retourne une valeur entre 0 et 1
			if (proba > 0.25) {
				int b;
				boolean v;
				do {
					v = true;
					// on genere des sommets a relier
					// a = (int) (Math.random() * 8);
					b = (int) (Math.random() * 8);
					if (i != b && !exist(i, b)) {
						// peering asi ayoub c quand c le mm niveau
						// transit c quand on apsse d'un niveau a un autre exemple
						// du niveau 1 au niveau 2 regarde la suite
						// et regarde le shcéma sur lenoncé
						// enfin bref c juste un affichage
						valeur = 5 + (int) (Math.random() * 6);
						addLane("Peering nv1", i, b, valeur);
						addLane("Peering nv1", b, i, valeur);
						v = false;
					}
				} while (v);
				// si c le mm sommet on rentre dans la boucle ca evite de refaire deux fois
				// le mm lien
			}

		}

		// niveau 2
		for (int i = 8; i < 28; i++) {

			proba = 1 + (int) (Math.random() * 2);

			for (int j = 0; j < proba; j++) {
				int b;
				boolean v;

				do {
					v = true;
					b = (int) (Math.random() * 8);
					if (!exist(i, b)) {
						valeur = 10 + (int) (Math.random() * 11);
						addLane("transit nv2-nv1", i, b, valeur);
						addLane("transit nv2-nv1", b, i, valeur);
						v = false;
					}
				} while (v);
			}

////////////////////////////////////////////////////////////
			proba = 2 + (int) (Math.random() * 2);

			for (int j = 0; j < proba; j++) {
				int b;
				boolean v;

				do {
					v = true;
					b = 8 + (int) (Math.random() * 20);
					if (i != b && !exist(i, b)) {
						valeur = 10 + (int) (Math.random() * 11);
						addLane("Peering nv2", i, b, valeur);
						addLane("Peering nv2", b, i, valeur);
						v = false;
					}
				} while (v);
			}

		}

// niveau 3
		for (int i = 28; i < 100; i++) {

			proba = 2;
			for (int j = 0; j < proba; j++) {
				int b;
				boolean v;

				do {
					v = true;
					b = 8 + (int) (Math.random() * 20);
					if (!exist(i, b)) {
						valeur = 15 + (int) (Math.random() * 36);
						addLane("transit nv3-nv2", i, b, valeur);
						addLane("transit nv3-nv2", b, i, valeur);
						v = false;
					}
				} while (v);
			}

////////////////////////////////////////////////////////////
			proba = 1;
			int b;
			boolean v;

			do {
				v = true;
				b = 28 + (int) (Math.random() * 72);
				if (i != b && !exist(i, b)) {
					valeur = 15 + (int) (Math.random() * 36);
					addLane("Peering nv3", i, b, valeur);
					addLane("Peering nv3", b, i, valeur);
					v = false;
				}
			} while (v);

		}

		for (Arete Arete : Aretes) {
			System.out.println(Arete);
		}

		Graph graph = new Graph(sommets, Aretes);

		IG frame = new IG(graph);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1600, 700);
		frame.setVisible(true);
	}

}
