package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.types.FullName;
import uo.ri.cws.domain.types.Nif;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	private MechanicRepository rm = Factories.repository.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto, "The mechanic dto is null");
		ArgumentChecks.isNotBlank(dto.nif, "The mechanic nif is empty");
		ArgumentChecks.isNotBlank(dto.name, "The mechanic name is empty");
		ArgumentChecks.isNotBlank(dto.surname, "The mechanic surname is empty");
		
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Mechanic> om = rm.findById(dto.id); // Getting persistent object
		if (om.isEmpty()) {
			throw new BusinessException("The mechanic does not exist");
		}
		
		Mechanic m = om.get();
		Nif newNif = new Nif(dto.nif);
		if (!m.getNif().equals(newNif) && rm.findByNif(newNif).isPresent()) {
			throw new BusinessException("There is another mechanic with the same nif");
		}
		m.setFullName(new FullName(dto.name, dto.surname));
		m.updatedNow();
		BusinessChecks.isTrue(dto.version == m.getVersion(), "The mechanic has been modified by another transaction");
		m.setNif(newNif);
		
		// All objects are persistent, so no need to call merge/persist on the repository
		// The changes will be automatically saved when the transaction is committed.
		
		return null;
	}

}
