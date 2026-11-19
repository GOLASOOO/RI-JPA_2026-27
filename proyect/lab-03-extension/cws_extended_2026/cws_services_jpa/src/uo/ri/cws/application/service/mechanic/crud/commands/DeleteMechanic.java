package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.query.MechanicQueries;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;
	private MechanicRepository rm = Factories.repository.forMechanic();
	private MechanicQueries mQueries = Factories.query.forMechanic();

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotBlank(mechanicId, "The mechanic id is empty");
		
		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Mechanic> om = rm.findById(mechanicId);
		
		BusinessChecks.exists(om, "The mechanic does not exist");
		BusinessChecks.isTrue(om.get().getAssigned().isEmpty(), "Cannot delete a mechanic that is assigned to a work order");
		boolean hasInterventions = mQueries.hasInterventions(mechanicId);
		BusinessChecks.isFalse(hasInterventions, "Cannot delete a mechanic that has interventions");
		
		rm.remove(om.get());
		return null;
	}

}
