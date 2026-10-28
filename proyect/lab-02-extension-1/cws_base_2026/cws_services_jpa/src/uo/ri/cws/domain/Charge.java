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
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "INVOICE_ID", "PAYMENTMEAN_ID" }) })
public class Charge extends BaseEntity {

	// Natural attributes
	@AttributeOverride(name = "amount", column = @Column(precision = 19, scale = 2)) //Same name so we dont override the column name
    private Money amount; // cents

    // Accidental attributes
    @ManyToOne private Invoice invoice;
    @ManyToOne private PaymentMean paymentMean;

    // ------ Constructors -----
    Charge() {
		
	}
    
    /* package */ Charge(Invoice invoice, PaymentMean paymentMean, Money amount) {
    	// validates the arguments
		// store the amount rounded to cents
		// increments the paymentMean accumulated -> paymentMean.charge( amount )
		// links invoice, this and paymentMean
    	ArgumentChecks.isNotNull(invoice, "Invoice cannot be null");
    	ArgumentChecks.isNotNull(paymentMean, "Payment mean cannot be null");
    	ArgumentChecks.isNotNull(amount, "Amount cannot be null");
    	ArgumentChecks.isTrue(amount.isNonNegative(), "Amount must be equal or greater than zero");
    	
    	paymentMean.charge(amount);
    	this.amount = amount.roundToCents();
    	Associations.SettlesWithdraws.atomicLink(invoice, this, paymentMean);
//    	throw new UnsupportedOperationException("Not yet implemented");
    	
    }

    // ---- Getters ----

    public Money getAmount() {
        return amount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public PaymentMean getPaymentMean() {
        return paymentMean;
    }

    // ---- Bussiness methods  ----

    /**
     * Restores and the value to the payment mean and unlinks the charge from the invoice and the payment mean.
     */
	/* package */ void rewind() {
		// decrements the payment mean accumulated ( paymentMean.rewind( amount) )
    	StateChecks.isTrue(invoice != null, "Charge is not linked to an invoice");
    	StateChecks.isTrue(paymentMean != null, "Charge is not linked to a payment mean");

    	paymentMean.rewind(amount);
    	Associations.SettlesWithdraws.atomicUnlink(this);
    }

    // ---- Association helpers ----

    void _setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    void _setPaymentMean(PaymentMean paymentMean) {
        this.paymentMean = paymentMean;
    }

    // ---- toString ----
    @Override
    public String toString() {
		return "Charge [invoice=" + invoice + ", paymentMean=" + paymentMean + ", amount=" + amount + "]";
    }
}
