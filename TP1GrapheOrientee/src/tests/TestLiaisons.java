package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import liaisons.Aeroport;
import liaisons.Liaisons;
import liaisons.Vol;

import org.junit.Before;
import org.junit.Test;

public class TestLiaisons {
	
	private Liaisons liens;
	private Aeroport montreal;
	private Aeroport quebec;
	private Aeroport toronto;
	private Aeroport winnipeg;
	private Aeroport calgary;
	private Aeroport vancouver;
	private ArrayList<Aeroport> aeroports;
	private Vol vol0;
	private Vol vol1;
	private Vol vol2;

	@Before
	public void setUp() throws Exception {
		montreal = new Aeroport("YUL","Trudeau","Montreal");
		quebec = new Aeroport("YQB","Jean-Lesage","Quebec");
		toronto = new Aeroport("YYZ","Pearson","Toronto");
		winnipeg = new Aeroport("YWG","james Armstrong Richardson","Winnipeg");
		calgary = new Aeroport("YYC","Calgary International Airport","Calgary");
		vancouver = new Aeroport("YVR","Vancouver International Airport","Vancouver");
		aeroports = new ArrayList<Aeroport>();
		aeroports.add(montreal);
		aeroports.add(quebec);
		aeroports.add(toronto);
		aeroports.add(winnipeg);
		aeroports.add(calgary);
		aeroports.add(vancouver);
		liens = new Liaisons(aeroports);
		vol0 = new Vol("v0","c1",new Date(), new Date());
		vol1 = new Vol("v1","c1",new Date(), new Date());
		vol2 = new Vol("v2","c2",new Date(), new Date());
	}

	@Test
	public void testLiaisons() {
		assertTrue((new Liaisons()).estVide());
	}

	@Test
	public void testLiaisonsArrayListOfAeroport() {
		assertFalse(liens.estVide());
		assertTrue(liens.estNonConnecte());
	}

	@Test
	public void testSontAdjacents() {
		liens.ajouter(montreal, quebec, vol0);
		assertFalse(liens.estNonConnecte());
		assertTrue(liens.sontAdjacents(montreal, quebec));
		assertFalse(liens.sontAdjacents(quebec, montreal));
	}

	@Test
	public void testLesVoisins() {
		liens.ajouter(montreal, quebec, vol0);
		liens.ajouter(montreal, toronto, vol1);
		ArrayList<Aeroport> v = liens.lesVoisins(montreal);
		assertEquals(2, v.size());
		assertTrue(v.contains(quebec));
		assertTrue(v.contains(toronto));
		assertFalse(v.contains(winnipeg));
		assertFalse(v.contains(calgary));
		assertFalse(v.contains(vancouver));
	}

	@Test
	public void testLesSommets() {
		assertTrue(aeroports.containsAll((new Liaisons(aeroports)).lesSommets()));
		assertTrue((new Liaisons(aeroports)).lesSommets().containsAll(aeroports));
	}

	@Test
	public void testAjouterTTV() {
		liens.ajouter(vancouver, toronto, vol2);
		assertFalse(liens.estNonConnecte());
		assertEquals(vol2, liens.laValeur(vancouver, toronto));
	}

	@Test
	public void testSupprimerTT() {
		liens.ajouter(vancouver, toronto, vol2);
		liens.supprimer(vancouver, toronto);
		assertTrue(liens.estNonConnecte());
	}

	@Test
	public void testSupprimerT() {
		liens.supprimer(vancouver);
		liens.supprimer(winnipeg);
		liens.supprimer(calgary);
		liens.supprimer(quebec);
		liens.supprimer(toronto);
		assertFalse(liens.estVide());
		liens.supprimer(montreal);
		assertTrue(liens.estVide());
		assertTrue(liens.lAdjacence().isEmpty());
	}

	@Test
	public void testAjouterT() {
		Aeroport bidon = new Aeroport("ZZZ","Bidon","UneVille");
		liens.ajouter(bidon);
		assertEquals(7, liens.lesSommets().size());
		liens.ajouter(bidon, bidon, new Vol("","",null,null));
		assertNotNull(liens.lAdjacence().get(6).get(6));
	}

	@Test
	public void testLaValeur() {
		Aeroport bidon = new Aeroport("ZZZ","Bidon","UneVille");
		liens.ajouter(bidon);
		Vol v = new Vol("","",null,null);
		liens.ajouter(bidon, bidon, v);
		liens.ajouter(vancouver, toronto, vol2);
		liens.ajouter(montreal, quebec, vol0);
		liens.ajouter(montreal, toronto, vol1);
		assertEquals(vol2, liens.laValeur(vancouver, toronto));
		assertEquals(vol1, liens.laValeur(montreal, toronto));
		assertEquals(vol0, liens.laValeur(montreal, quebec));
		//assertEquals(v, liens.laValeur(bidon, bidon));
		assertNull(liens.laValeur(montreal, winnipeg));
	}
	
	@Test
	public void testEstVide() {
		assertTrue((new Liaisons()).estVide());
		assertFalse((new Liaisons(aeroports)).estVide());
		assertFalse(liens.estVide());
		liens.ajouter(montreal, quebec, vol0);
		assertFalse(liens.estVide());
	}

	@Test
	public void testEstNonConnecte() {
		assertFalse((new Liaisons()).estNonConnecte());
		assertTrue((new Liaisons(aeroports)).estNonConnecte());
		assertTrue(liens.estNonConnecte());
		liens.ajouter(montreal, quebec, vol0);
		assertFalse(liens.estNonConnecte());
	}

}
