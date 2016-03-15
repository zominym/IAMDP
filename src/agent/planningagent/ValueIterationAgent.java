package agent.planningagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
import environnement.gridworld.ActionGridworld;
import util.HashMapUtil;


/**
 * Cet agent met a jour sa fonction de valeur avec value iteration 
 * et choisit ses actions selon la politique calculee.
 * @author laetitiamatignon
 *
 */
public class ValueIterationAgent extends PlanningValueAgent{
	/**
	 * discount facteur
	 */
	protected double gamma;
	
	//*** VOTRE CODE
	HashMapUtil valueMap;

	
	/**
	 * 
	 * @param gamma
	 * @param mdp
	 */
	public ValueIterationAgent(double gamma,MDP mdp) {
		super(mdp);
		this.gamma = gamma;
		
		//*** VOTRE CODE
		valueMap = new HashMapUtil();
		for(Etat e : mdp.getEtatsAccessibles())
			valueMap.put(e, 0.);
	
	
	}
	
	
	public ValueIterationAgent(MDP mdp) {
		this(0.9,mdp);
	}
	
	/**
	 * 
	 * Mise a jour de V: effectue UNE iteration de value iteration 
	 */
	@Override
	public void updateV(){
		//delta est utilise pour detecter la convergence de l'algorithme
		//lorsque l'on planifie jusqu'a convergence, on arrete les iterations lorsque
		//delta < epsilon 
		this.delta=0.0;
		
		//*** VOTRE CODE
		HashMapUtil newValueMap = new HashMapUtil();
		
		for(Etat e : mdp.getEtatsAccessibles()) {
			double res1;
			double resMax = Double.MIN_VALUE;
			List<Double> lres = new ArrayList<Double>();
			for (Action aPoss : getMdp().getActionsPossibles(e)) {
				res1 = 0.;
				resMax = Double.MIN_VALUE;
				try {
					for (Entry<Etat, Double> entry : getMdp().getEtatTransitionProba(e, aPoss).entrySet())
						res1 += entry.getValue() * (getMdp().getRecompense(e, aPoss, entry.getKey()) + gamma * valueMap.get(e));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (res1 > resMax)
					resMax = res1;
			}
			newValueMap.put(e, resMax);
		}
		
	
		
		
		// mise a jour vmax et vmin pour affichage du gradient de couleur:
		//vmax est la valeur de max pour tout s de V
		//vmin est la valeur de min pour tout s de V
		// ...
		
		//******************* a laisser a la fin de la methode
		this.notifyObs();
	}
	
	
	/**
	 * renvoi l'action executee par l'agent dans l'etat e 
	 */
	@Override
	public Action getAction(Etat e) {
		
		//*** VOTRE CODE
		
		// NOTE UTILE -----------------------------------------------------------------------------------------------------
		// mdp.getEtatTransitionProba(_e, _a);
		// mdp.getRecompense(_e, _a, _es);
		
	
		return null;
	}
	@Override
	public double getValeur(Etat _e) {
		
		//*** VOTRE CODE
		return valueMap.get(_e);
	}
	/**
	 * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e 
	 * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si aucune action n'est possible)
	 */
	@Override
	public List<Action> getPolitique(Etat _e) {
		List<Action> l = new ArrayList<Action>();
		
		//*** VOTRE CODE
		
		
		return l;
		
	}
	
	@Override
	public void reset() {
		super.reset();
		
		//*** VOTRE CODE
		
		
		
		
		
		/*-----------------*/
		this.notifyObs();

	}


	@Override
	public void setGamma(double arg0) {
		this.gamma = arg0;
	}

	
}
