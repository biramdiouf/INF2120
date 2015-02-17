package tp1;

/**
 * ****************   INF2120
 * ****************   Biram DIOUF
 * ****************   CODE PERMANENT : DIOB06127605
 * ****************   diouf.biram@courrier.uqam.ca
 * ****************   TP1 sur les Graphes orientés
 * 
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import liaisons.Aeroport;
import liaisons.Liaisons;
import liaisons.Vol;

import org.junit.Before;

import tda.Graphe;

public class GrapheOriente<T, V> implements Graphe<T, V> {

	private ArrayList<T> sommets;
	protected ArrayList<ArrayList<V>> adjacence;
	private ArrayList<Arc<T, V>> listeDesArcs; // Liste pour sauvegarder l'origine et la destination des arcs

	public GrapheOriente(ArrayList<T> sommets) {
		this();
		this.sommets = sommets;
		for (int i = 0; i < sommets.size(); i++) {
			adjacence.add(new ArrayList<V>());
			for (int j = 0; j < sommets.size(); j++) {
				adjacence.get(i).add(null);
			}
		}
	}

	public GrapheOriente() {
		sommets = new ArrayList<T>();
		adjacence = new ArrayList<ArrayList<V>>();
		listeDesArcs = new ArrayList<Arc<T, V>>();
	}

	/* -- Teste si l'origine x de l'arc est non null et 
	 * la destination y de l'arc est non null
	 * et que le graphe contient bien x et y.
	 * 
	 * -- Recherche s'il y a un arc ayant comme origine x et 
	 * comme destination y 
	 * Si l'arc est trouvé on retourne true
	 *  */
	@Override
	public boolean sontAdjacents(T x, T y) {

		if (x != null && y != null && sommets.size() != 0
				&& sommets.contains(x) && sommets.contains(y)) {

			for (V arcs : adjacence.get(sommets.indexOf(x))) {
				for (Arc<T, V> vecteurs : listeDesArcs) {
					if (vecteurs.valeur().equals(arcs)
							&& vecteurs.destination().equals(y)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * Recherche des arcs qui ont x comme origine  ou destination 
	 * Si l'arc est retrouvé alors on teste:
	 * -Si x est l'origine de l'arc, le voisin est la destination de l'arc
	 * 	on ajoute dans la liste des voisins
	 * -Si x est la destination de l'arc, le voisin est l'origine de l'arc
	 * 	on ajoute dans la liste des voisins
	 */
	@Override
	public ArrayList<T> lesVoisins(T x) {
		ArrayList<T> list = new ArrayList<T>();
		if ( x !=null && sommets.size() != 0 && sommets.contains(x)) {
			ArrayList<V> listV = adjacence.get(sommets.indexOf(x));
			for (V arc : listV) {
				for (Arc<T, V> vecteurs : listeDesArcs) {
					if (vecteurs.valeur().equals(arc)) {
						if (vecteurs.origine().equals(x)) {
							list.add(vecteurs.destination());
						} else if (vecteurs.destination().equals(x)) {
							list.add(vecteurs.origine());
						}
					}
				}
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tda.Graphe#lesSommets()
	 */
	@Override
	public ArrayList<T> lesSommets() {
		return sommets;
	}

	/*
	 * Vérifier si les valeurs de x, y et v ne sont pas null
	 * Ajouter le sommet x s'il n'est pas dans le graphe
	 * Ajouter le sommet y s'il n'est pas dans le graphe
	 * Ajouter un nouvel arc à la listeDesArcs
	 * Ajouter la valeur v à la liste d'adjacence
	 */
	@Override
	public void ajouter(T x, T y, V v) {
		if (x != null && y != null && v != null) {
			ajouter(x);
			ajouter(y);
			int sommetX = sommets.indexOf(x);
			if(!adjacence.get(sommetX).contains(v)){
				Arc<T, V> arc = new Arc<T, V>(x, y, v);
				listeDesArcs.add(arc);
				int sommetY = sommets.indexOf(y);
				adjacence.get(sommetX).set(sommetY,arc.valeur());
			}
		}
	}

	/*
	 * Vérifier si x et y sont non null
	 * et que les sommets x et y sont bien présents dans le graphe.
	 * Recherche l'arc ayant comme origine x et comme destination y.
	 * Si l'arc est trouvé, récupéré l'objet arc et sa valeur
	 * les supprimer dans la liste des adjacences et celle des arcs 	 */
	@Override
	public void supprimer(T x, T y) {

		if (x != null && y != null && sommets.size() != 0
				&& sommets.contains(x) && sommets.contains(y)) {
			int si = sommets.indexOf(x);
			V arc = null;
			Arc<T, V> arcTV = null;
			ArrayList<V> listV = adjacence.get(si);
			boolean trouve = false;

			for (int i = 0; i < listV.size() && !trouve; i++) {
				for (Arc<T, V> vecteurs : listeDesArcs) {
					if (vecteurs.valeur().equals(listV.get(i))
							&& vecteurs.destination().equals(y)) {
						arc = vecteurs.valeur();
						arcTV = vecteurs;
						trouve = true;
						break;
					}
				}
			}
			if (arcTV != null && arc != null ) {
				listeDesArcs.remove(arcTV);
				adjacence.get(si).remove(arc);

			}
		}
	}

	/*
	 * Vérifier que le sommet x est non null et que 
	 * x est bien présent dans liste des sommets
	 * Recherche des arcs qui ont comme origine x ensuite les supprimer. 
	 * Recherche des arcs qui ont comme destination x ensuite les supprimer.
	 * Supprimer le sommet x de la liste d'adjacence.
	 * Supprimer le sommet x de la liste des sommets.
	 */
	@Override
	public void supprimer(T x) {

		if (x != null && sommets.size() != 0 && sommets.contains(x)) {
			int si = sommets.indexOf(x);
			for (T sommet : sommets) {
				if (sontAdjacents(x, sommet)) {
					supprimer(x, sommet);
				} else if (sontAdjacents(sommet, x)) {
					supprimer(sommet, x);
				}
			}
			adjacence.remove(si);
			sommets.remove(si);
		}
	}

	/*
	 */
	@Override
	public void ajouter(T x) {
		// Teste si le sommet n'est pas déjà dans le graphe
		// Sommet non présent dans le graphe -- ajouter
		if (x != null && !sommets.contains(x)) {
			sommets.add(x);
			adjacence.add(new ArrayList<V>());
			int sommetX = sommets.indexOf(x);
			for (int i = 0; i < sommets.size(); i++) {
				adjacence.get(sommetX).add(null);
			}
		}
	}

	/*
	 * Vérifier la valeur de x et y s'ils sont non null
	 * et qu'ils sont bien présents dans la liste des sommets
	 * Rechercher de l'arc ayant comme orogine x et comme destination y
	 * Si l'arc est trouvé, retourner la valeur de l'arc
	 */
	@Override
	public V laValeur(T x, T y) {

		if (x != null && y != null && sommets.size() != 0
				&& sommets.contains(x) && sommets.contains(y)) {

			ArrayList<V> listV = adjacence.get(sommets.indexOf(x));
			for (int i = 0; i < listV.size(); i++) {
				for (Arc<T, V> vecteurs : listeDesArcs) {
					if (vecteurs.valeur().equals(listV.get(i))
							&& vecteurs.destination().equals(y)) {
						return vecteurs.valeur();
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean estVide() {
		return (sommets.size() == 0);
	}

	/*
	 * Rechercher pour chaque sommet les sommets qui lui sont voisins
	 * Teste si la liste des voisins est null alors le graphe est non connecté
	 * Sinon si la liste des voisins est non null o retourne la valeur false
	 */
	@Override
	public boolean estNonConnecte() {
		ArrayList<T> voisins = new ArrayList<T>();
		if (estVide()) {
			return false;
		} else {
			for (T sommet : sommets) {
				voisins = lesVoisins(sommet);
				boolean connecte = (voisins.size() != 0);
				if (connecte)
					return false;
			}
		}
		return true;
	}

}
