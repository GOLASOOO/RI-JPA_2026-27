package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.cws.domain.types.LicensePlate;
import uo.ri.cws.domain.types.Money;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class Vehicle extends BaseEntity {

	// Natural attributes
	@AttributeOverride(name = "licensePlate", column=@Column(unique = true)) private LicensePlate licensePlate;
	private String make;
	private String model;
	
	// Accidental attributes
	@ManyToOne private VehicleType vehicleType;
	@ManyToOne private Client client;
	@OneToMany(mappedBy="vehicle") private Set<WorkOrder> workOrders = new HashSet<>();

	// ---- Constructors ----
	Vehicle() {
		// For JPA
	}
	
	public Vehicle(LicensePlate plateNumber) {
		// Delegate to the other constructor with meaningful default values for make and model
		this(plateNumber, "Default Make", "Default Model");
		
	}

	public Vehicle(LicensePlate plateNumber, String make, String model) {
		ArgumentChecks.isNotNull(plateNumber);
		ArgumentChecks.isNotBlank(make);
		ArgumentChecks.isNotBlank(model);
		
		this.licensePlate = plateNumber;
		this.make = make;
		this.model = model;
		
	}
	
	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public LicensePlate getLicensePlate() {
		return licensePlate;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}
	
	public Client getClient() {
		return client;
	}
	
	/* Package */ void _setClient(Client client) {
		this.client = client;
	}
	
 
	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}
	
	public Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Money getPricePerHour() {
		return vehicleType.getPricePerHour(); // VehicleType must return Money
	}

	@Override
	public int hashCode() {
		return Objects.hash(licensePlate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(licensePlate, other.licensePlate);
	}

	@Override
	public String toString() {
		return "Vehicle [licensePlate=" + licensePlate + ", make=" + make + ", model=" + model + "]";
	}
	
	
}
