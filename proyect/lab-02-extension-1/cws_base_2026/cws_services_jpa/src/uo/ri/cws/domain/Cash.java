package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import uo.ri.cws.domain.types.Money;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class Cash extends PaymentMean {

	// ---- Constructors ----
	protected Cash() {
		
	}
	
	public Cash(Client client) {
		ArgumentChecks.isNotNull(client, "Client cannot be null");
		
		Associations.Holds.link(client, this);
		
//		throw new UnsupportedOperationException("Not implemented yet");
	}

	// ---- Business methods ----
	@Override
	public boolean canBeCharged(Money amount) {
		checkPositive(amount);
		return true;
	}

	// ---- toString ----
	@Override
	public String toString() {
		return "Cash [accumulated=" + getAccumulated() + ", client=" + getClient() + "]";
	}

	/**
	 * As we have no additional attributes, we cannto implement the #
	 * we can use "extend" equals and hashCode methods from Object class.
	 * or manually add it
	 * */
	
	public int hashCode() {
		return getClient().hashCode();
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cash other = (Cash) obj;
		return getClient().equals(other.getClient());
	}
	
	
	
}
