package uo.ri.cws.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.cws.domain.types.Money;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * A substitution is a relation between an intervention and a spare part. It
 * represents the fact that a spare part has been used in an intervention.
 * 
 * Internal aggregate class of WorkOrder. Only Intervention can create
 * Substitutions (through WorkOrder).
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "SPAREPART_ID", "INTERVENTION_ID" }) })
public class Substitution extends BaseEntity {
	// Natural attributes
	@AttributeOverride(name = "amount", column = @Column(name = "price", precision = 19, scale = 2))
	private Money price;
	private int quantity;

	// Accidental attributes
	@ManyToOne private SparePart sparePart;
	@ManyToOne private Intervention intervention;

	// ---------------------------- Constructors -----------------------------
	/**
	 * Package-private constructor to create a new Substitution with the given
	 * parameters. Only Intervention can create Substitutions.
	 */
	Substitution() {
		// For JPA
	}
	
	/* package */ Substitution(SparePart sparePart, Intervention intervention, int quantity) {
		// Validate input parameters
		// assignations
		// link the substitution with the intervention and spare part (atomiclink)

		ArgumentChecks.isNotNull(sparePart, "Spare part cannot be null");
		ArgumentChecks.isNotNull(intervention, "Intervention cannot be null");
		ArgumentChecks.isTrue(quantity > 0, "Quantity must be greater than zero");

		this.quantity = quantity;
		Associations.PerformsSubstitutes.atomicLink(intervention, this, sparePart);
		this.price = this.sparePart.getUnitPrice();

	}

	// ---------------------------- Association helpers
	// -----------------------------

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	// ---------------------------- Business methods -----------------------------
	public Money getTotalAmount() {
//		throw new UnsupportedOperationException("Not implemented yet");
		return Money.calc(sparePart.getUnitPrice()).times(quantity).toMoneyRoundedToCents();

	}

	public Money getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	// ---------------------------- Getters -----------------------------

}
