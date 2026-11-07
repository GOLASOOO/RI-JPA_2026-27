package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class Recommendation extends BaseEntity {

	// Natural attributes
	private boolean usedForVoucher;

	// Accidental attributes
	@ManyToOne private Client recommender;
	@ManyToOne private Client recommended;

	// ---- Constructors ----
	Recommendation() {
		// For JPA
	}

	public Recommendation(Client recommender, Client recommended) {
		ArgumentChecks.isNotNull(recommender, "Recommender cannot be null");
		ArgumentChecks.isNotNull(recommended, "Recommended client cannot be null");
		ArgumentChecks.isFalse(recommender.equals(recommended), "A client cannot recommend itself");

		Associations.Recommends.link(recommender, this, recommended);
	}

	// ---- Getters ----
	public Client getRecommender() {
		return recommender;
	}

	public Client getRecommended() {
		return recommended;
	}

	public boolean isUsedForVoucher() {
		return usedForVoucher;
	}

	// ---- Business methods ----
	public void markAsUsedForVoucher() {
		usedForVoucher = true;
	}

	// ---- Association helpers ----
	void _setRecommender(Client recommender) {
		this.recommender = recommender;
	}

	void _setRecommended(Client recommended) {
		this.recommended = recommended;
	}

	@Override
	public String toString() {
		return "Recommendation [recommender=" + recommender + ", recommended=" + recommended + ", usedForVoucher="
				+ usedForVoucher + "]";
	}

}
