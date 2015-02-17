/**
 * 
 */
package liaisons;

import java.util.ArrayList;

import tp1.GrapheOriente;

/**
 * @author lefebvre_b
 *
 */
public class Liaisons extends GrapheOriente<Aeroport, Vol> {

	public Liaisons() {
		super();
	}

	public Liaisons(ArrayList<Aeroport> sommets) {
		super(sommets);
	}
	
	public ArrayList<ArrayList<Vol>> lAdjacence() {
		return adjacence;
	}

}
