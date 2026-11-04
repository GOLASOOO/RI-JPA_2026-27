package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.types.FullName;
import uo.ri.cws.domain.types.Nif;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;
	private MechanicRepository repo = Factories.repository.forMechanic();

	public AddMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto, "The mechanic dto is null");
		ArgumentChecks.isNotBlank(dto.nif, "The mechanic nif is empty");
		ArgumentChecks.isNotBlank(dto.name, "The mechanic name is empty");
		ArgumentChecks.isNotBlank(dto.surname, "The mechanic surname is empty");
		
		this.dto = dto;
	}

	@Override
	public MechanicDto execute() throws BusinessException {
		// Create a new mewchanic
		Nif nif = new Nif(dto.nif);
		FullName name = new FullName(dto.name, dto.surname);
		Mechanic m = new Mechanic(nif, name);
		
		
		// PErsist the new mechanic in the mapper (Transient -> Persistent)
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("carworkshop"); // persistence unit name (carworkshop) can be found inside cws_services_jpa/.../persistence.xml
//		EntityManager mapper = emf.createEntityManager();
//		EntityManager mapper = Jpa.getManager();
////		EntityTransaction tx = mapper.getTransaction();
//		mapper.persist(m);
		
//		tx.commit();
//		mapper.close();
//		emf.close();
		
		repo.add(m);
		return DtoAssembler.toDto(m);
		
		
	}

}
