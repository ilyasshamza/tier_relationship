package tier_relationship;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFrame;

import com.mxgraph.model.mxCell;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;

public class IG extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graph gr;
	private Map<String, Object> sommets;
	private mxGraph graph;
	private Object parent;
	private Sommet depart = null;
	private Sommet arrive = null;
	private JLabel lblSelectDepart;
	private String style;

	private void reremplissageSommet() {
		for (Map.Entry<String, Object> entry : sommets.entrySet()) {
			graph.addCell(entry.getValue());

		}

	}

	private void chemin(LinkedList<Sommet> path) {

		Sommet courant = path.getFirst();
		for (Sommet sommet : path) {
			if (courant != sommet) {
				for (Arete arete : gr.getArete()) {
					if (arete.getSource() == courant && arete.getDestination() == sommet) {
						graph.insertEdge(parent, null, arete.getPoids(), sommets.get(courant.getId()),
								sommets.get(sommet.getId()), style);
						graph.insertEdge(parent, null, arete.getPoids(), sommets.get(sommet.getId()),
								sommets.get(courant.getId()), style);
						courant = sommet;
					}
				}
			}
		}

	}

	private void initialisationArete() {
		for (Arete arete : gr.getArete()) {
			graph.insertEdge(parent, null, arete.getPoids(), sommets.get(arete.getSource().getId()),
					sommets.get(arete.getDestination().getId()), style);

		}

	}

	// morba3at
	private void initialisationSommet() {

		int i = 0;
		int j = 0;
		for (Sommet object : gr.getSommet()) {
			if (i < 8) {
				sommets.put(object.getId(), graph.insertVertex(parent, null, object.getId(), i * 400, j * 30, 30, 30));
				j++;
			}

			if (i >= 8 && i < 28) {
				sommets.put(object.getId(),
						graph.insertVertex(parent, null, object.getId(), i * 100 - 100 * 8, j * 40 + 150, 30, 30));
				j++;
			}

			if (i >= 28) {
				sommets.put(object.getId(),
						graph.insertVertex(parent, null, object.getId(), i * 100 - 100 * 28, j * 40 + 400, 30, 30));
				j++;
			}

			if (j == 4) {
				j = 0;
			}
			i++;
		}
	}

	public IG(Graph graphe) {
		super("tier relationship evec dijkstra");
		this.gr = graphe;
		graph = new mxGraph();
		sommets = new HashMap<String, Object>();
		parent = graph.getDefaultParent();
		style = mxConstants.STYLE_MOVABLE + "=0";
		graph.getModel().beginUpdate();
		try {

			// String style=null;//= mxConstants.STYLE_EDGE + "=" +
			// mxConstants.EDGESTYLE_ENTITY_RELATION;

			initialisationSommet();

			initialisationArete();
		} finally {
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// bach tgolih rak khtariti morba3 machi chihaja akhra

				mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());

				if (cell != null && sommets.containsValue(cell)) {
					if (depart == null) {
						depart = gr.getSommet().get(Integer.parseInt((String) cell.getValue()));
						lblSelectDepart.setText("votre depart est " + depart.getId() + " >>selectioner un arrive");

					} else {

						arrive = gr.getSommet().get(Integer.parseInt((String) cell.getValue()));
						lblSelectDepart
								.setText("votre depart est " + depart.getId() + "  votre arrive est " + arrive.getId());

					}

				}

				if (arrive != null) {
					DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(gr);
					dijkstra.execute(depart);
					LinkedList<Sommet> path = dijkstra.getPath(arrive);
					String chemin = "";
					int cont = 0;
					for (Sommet vertex : path) {
						if (cont < (path.size() - 1)) {
							chemin += vertex.getId() + "-->";
							cont++;
						} else {
							chemin += vertex.getId();
						}
					}
					graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
					reremplissageSommet();
					chemin(path);

					lblSelectDepart.setText(lblSelectDepart.getText() + "  :" + chemin + "   avec cout:"
							+ dijkstra.getShortestDistance(arrive));
				}

			}
		});

		getContentPane().add(graphComponent);

		JPanel panel = new JPanel();
		graphComponent.setColumnHeaderView(panel);

		JButton btnNewButton = new JButton("reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
				arrive = depart = null;
				reremplissageSommet();
				initialisationArete();
				lblSelectDepart.setText("select depart");
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel
				.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE).addGap(26)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
				.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE));

		lblSelectDepart = new JLabel("select depart");
		scrollPane.setViewportView(lblSelectDepart);
		panel.setLayout(gl_panel);

	}
}