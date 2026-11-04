package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Recommendation {
	private Client recommender;
	private Client recommended;

	public Recommendation(Client recommender, Client recommended) {
		ArgumentChecks.isNotNull(recommender, "Recommender cannot be null");
		ArgumentChecks.isNotNull(recommended, "Recommended client cannot be null");
	
	}

	public Client getRecommender() {
		return recommender;
	}

	public Client getRecommended() {
		return recommended;
	}
	
	

}
