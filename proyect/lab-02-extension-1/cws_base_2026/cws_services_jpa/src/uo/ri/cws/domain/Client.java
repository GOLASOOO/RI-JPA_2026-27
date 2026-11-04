package uo.ri.cws.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.cws.domain.types.Address;
import uo.ri.cws.domain.types.Email;
import uo.ri.cws.domain.types.FullName;
import uo.ri.cws.domain.types.Nif;
import uo.ri.cws.domain.types.PhoneNumber;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;


@Entity
public class Client extends BaseEntity {

	
	// Natural attributes
	@AttributeOverride(name = "nif", column=@Column(unique = true)) private Nif nif;
	private FullName fullName;
	private Email email;
	private PhoneNumber phone;
	private Address address;
	
	// Accidental attributes
	@OneToMany(mappedBy="client") private Set<Vehicle> vehicles = new HashSet<>();
	@OneToMany(mappedBy="client") private Set<PaymentMean> paymentMeans = new HashSet<>();
	@OneToMany(mappedBy="recommender") private Set<Recommendation> recommendations = new HashSet<>();
	@OneToOne(mappedBy="recommended") private Recommendation recommendation;

	// ---- Constructors ----
	public Client() {
	}
	
	public Client(Nif nif) {
		this(nif, new FullName("No name", "No surname"), new Email("default@example.com"),
				new PhoneNumber("+34600000000"), new Address("No street", "No city", "No zip", "No country"));
	}

	public Client(Nif nif, FullName fullName) {
		this(nif, fullName, new Email("default@example.com"), new PhoneNumber("+34600000000"),
				new Address("No street", "No city", "No zip", "No country"));
	}

	public Client(Nif nif, FullName fullName, Email email, PhoneNumber phone, Address address) {
		ArgumentChecks.isNotNull(nif);
		ArgumentChecks.isNotNull(fullName);
		ArgumentChecks.isNotNull(email);
		ArgumentChecks.isNotNull(phone);
		ArgumentChecks.isNotNull(address);
		
		this.nif = nif;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		
	
	}

	@Override
	public int hashCode() {
		return Objects.hash(nif);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(nif, other.nif);
	}

	@Override
	public String toString() {
		return "Client [nif=" + nif + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", address="
				+ address + "]";
	}

	public Nif getNif() {
		return nif;
	}

	public FullName getFullName() {
		return fullName;
	}

	public Email getEmail() {
		return email;
	}

	public PhoneNumber getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	} 
	
	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles); // Copyconstructor to avoid exposing the internal set
	}
	
	
	/* package */ Set<Vehicle> _getVehicles() {
		return vehicles;
	}
	
	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans); // Copyconstructor to avoid exposing the internal set
	}
	
	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}

	public Set<Recommendation> getRecommendations() {
		return new HashSet<>(recommendations); // Copyconstructor to avoid exposing the internal set
	}

	/* package */ Set<Recommendation> _getRecommendations() {
		return recommendations;
	}

	public Recommendation getRecommendation() {
		return recommendation;
	}

	/* package */ void _setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}

	// ---- Voucher 20 (work orders) ----

	public List<WorkOrder> getWorkOrdersAvailableForVoucher20() {
		List<WorkOrder> available = new ArrayList<>();
		for (Vehicle vehicle : vehicles) {
			for (WorkOrder workOrder : vehicle.getWorkOrders()) {
				if (workOrder.getInvoice() != null && workOrder.getInvoice().isSettled()
						&& !workOrder.isUsedForVoucher()) {
					available.add(workOrder);
				}
			}
		}
		return available;
	}

	public void markThreeWorkOrdersAsUsedForVoucher20() {
		List<WorkOrder> available = getWorkOrdersAvailableForVoucher20();
		StateChecks.isTrue(available.size() >= 3, "Not enough available work orders for a voucher");

		for (int i = 0; i < 3; i++) {
			available.get(i).markAsUsedForVoucher();
		}
	}

}