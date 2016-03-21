package agent.planningagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
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
		vmax = - Double.MAX_VALUE;
		vmin = Double.MAX_VALUE;
		
		for(Etat e : mdp.getEtatsAccessibles()) {
		//for(Etat e : valueMap.keySet()) {
			if(!mdp.estAbsorbant(e))
			{	
				//System.out.println(e);
				
				double res1;
				double resMax = Double.MIN_VALUE;
				resMax = -Double.MAX_VALUE;
				for (Action aPoss : getMdp().getActionsPossibles(e)) {
					res1 = 0.;
					try {
						for (Entry<Etat, Double> entry : getMdp().getEtatTransitionProba(e, aPoss).entrySet()) {
							double temp;
							temp = entry.getValue() * (getMdp().getRecompense(e, aPoss, entry.getKey()) + gamma * valueMap.get(entry.getKey()));
							res1 += temp;
							//System.out.println(entry.getValue() + "*" + getMdp().getRecompense(e, aPoss, entry.getKey()) + "+" + gamma + "*" + valueMap.get(entry.getKey()) + "=" + temp);
						}
						//System.out.println("RES1 = " + res1);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (res1 > resMax)
					{
						resMax = res1;
					}
				}
				if (resMax > vmax)
					vmax = resMax;
				if (resMax < vmin)
					vmin = resMax;
				newValueMap.put(e, resMax);
				double deltatemp = Math.abs(valueMap.get(e) - newValueMap.get(e));
				//System.out.println(deltatemp + "=" + valueMap.get(e) + "-" + newValueMap.get(e));
				//System.out.println(delta);
				if (deltatemp > delta)
					delta = deltatemp;
				//System.out.println(delta);
			}
		}
		//System.out.println(valueMap);
		//System.out.println(newValueMap);
		valueMap = newValueMap;
		
	
		
		
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
		if (getMdp().estAbsorbant(e))
			return null;
		Random rand = new Random();
		List<Action> l = getPolitique(e);
		//System.out.println(l);
		return l.get(rand.nextInt(l.size()));
		
		
		// NOTE UTILE -----------------------------------------------------------------------------------------------------
		// mdp.getEtatTransitionProba(_e, _a);
		// mdp.getRecompense(_e, _a, _es);
	}
	@Override
	public double getValeur(Etat _e) {
		
		if (valueMap.containsKey(_e))
			return valueMap.get(_e);
		else
			return 0;
	}
	/**
	 * renvoie la (les) action(s) de plus forte(s) valeur(s) dans l'etat e 
	 * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si aucune action n'est possible)
	 */
	@Override
	public List<Action> getPolitique(Etat e) {
		List<Action> l = new ArrayList<Action>();
		//System.out.println(e);
		
		//*** VOTRE CODE
		if(!mdp.estAbsorbant(e))
		{	
			//System.out.println(e);
			
			double res1;
			double resMax = Double.MIN_VALUE;
			resMax = -Double.MAX_VALUE;
			//System.out.println(getMdp().getActionsPossibles(e));
			for (Action aPoss : getMdp().getActionsPossibles(e)) {
				res1 = 0.;
				try {
					for (Entry<Etat, Double> entry : getMdp().getEtatTransitionProba(e, aPoss).entrySet()) {
						double temp;
						temp = entry.getValue() * (getMdp().getRecompense(e, aPoss, entry.getKey()) + gamma * valueMap.get(entry.getKey()));
						res1 += temp;
						//System.out.println(entry.getValue() + "*" + getMdp().getRecompense(e, aPoss, entry.getKey()) + "+" + gamma + "*" + valueMap.get(entry.getKey()) + "=" + temp);
					}
					//System.out.println("RES1 = " + res1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (res1 > resMax)
				{
					//System.out.println(res1 + ">" + resMax);
					//System.out.println(aPoss);
					l = new ArrayList<Action>();
					l.add(aPoss);
					resMax = res1;
				}
				else if (res1 == resMax)
				{
					//System.out.println(res1 + "==" + resMax);
					//System.out.println(aPoss);
					l.add(aPoss);
				}
			}
		}
		//System.out.println(l);
		return l;
		
	}
	
	@Override
	public void reset() {
		super.reset();
		
		//*** VOTRE CODE
		for(Etat e : mdp.getEtatsAccessibles())
			valueMap.put(e, 0.);
		
		
		
		
		/*-----------------*/
		this.notifyObs();

	}


	@Override
	public void setGamma(double arg0) {
		this.gamma = arg0;
	}

	
}
