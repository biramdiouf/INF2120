package tp1;

/**
 * Classe d'arcs
 * 
 * ****************   INF2120
 * ****************   Biram DIOUF
 * ****************   CODE PERMANENT : DIOB06127605
 * ****************   diouf.biram@courrier.uqam.ca
 */
// L'arc origine -> destination ( o -> d) avec la valeur v
public class Arc<T,V> {
	private T o, d;
	private V val;

	public Arc(T x, T y, V v) {
		this.o = x;
		this.d = y;
		this.val = v;
	}

	public T destination() {
		return d;
	}

	public T origine() {

		return o;
	}

	public V valeur() {
		return val;
	}

	public boolean equals(Object arc) {
		@SuppressWarnings("unchecked")
		Arc<T, V> a = (Arc<T, V>) arc;
		return o.equals(a.o) && d.equals(a.d) && (val == a.val);
	}
	
	public String toString() {
		return "(" + this.o + ", " + this.d + ")";
	}

	public int hashCode() {
		String str = "" + this;
		return str.hashCode();
	}
}