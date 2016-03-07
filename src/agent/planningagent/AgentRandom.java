package agent.planningagent;


import java.util.List;
import java.util.Random;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
/**
 * Cet agent choisit une action aleatoire parmi toutes les autorisees dans chaque etat
 * @author lmatignon
 *
 */
public class AgentRandom extends PlanningValueAgent{
	Random rand;
	
	public AgentRandom(MDP _m) {
		super(_m);
		rand = new Random();
	}

	@Override
	public Action getAction(Etat e) {
		
		//*** VOTRE CODE
		List<Action> lAction = getMdp().getActionsPossibles(e);
		
		if (lAction.size() == 0)
		{
			return null;
		}
		return lAction.get(rand.nextInt(lAction.size()));
	}

	
	
	@Override
	public double getValeur(Etat _e) {
		return 0.0;
	}

	

	@Override
	public List<Action> getPolitique(Etat _e) {
		
		//*** VOTRE CODE
		return getMdp().getActionsPossibles(_e);
	}

	@Override
	public void updateV() {
		System.out.println("l'agent random ne planifie pas");
	}

	@Override
	public void setGamma(double parseDouble) {
		// TODO Auto-generated method stub
		
	}




}
