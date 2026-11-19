package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

	private String id;
	private MechanicRepository rm = Factories.repository.forMechanic();

	public FindMechanicById(String id) {
		ArgumentChecks.isNotBlank(id, "The mechanic id is empty");

		this.id = id;
	}

	@Override
	public Optional<MechanicDto> execute() throws BusinessException {
		Optional<Mechanic> om = rm.findById(id);
		return om.map(m -> DtoAssembler.toDto(m) // Convert the mechanic to a dto using the DtoAssembler::toDto method
													// reference
		);
	}

}
