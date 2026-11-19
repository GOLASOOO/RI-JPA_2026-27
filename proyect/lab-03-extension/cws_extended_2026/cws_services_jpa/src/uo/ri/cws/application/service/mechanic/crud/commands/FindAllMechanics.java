package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindAllMechanics implements Command<List<MechanicDto>> {

	private MechanicRepository repo = Factories.repository.forMechanic();
	
	@Override
	public List<MechanicDto> execute() {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("carworkshop"); // persistence unit name (carworkshop) can be found inside cws_services_jpa/.../persistence.xml
//		EntityManager mapper = emf.createEntityManager();
//		EntityTransaction tx = mapper.getTransaction();
//		tx.begin();

//		EntityManager mapper = Jpa.getManager(); // Fixed dependency to jakarta on repository
//		String q = "Select m from Mechanic m"; // NOT *
//		List<Mechanic> mechanics = mapper.createQuery(q, Mechanic.class)
//			.getResultList();
		
		
//		tx.commit(); // Resources closed by Command executor
//		mapper.close();
//		emf.close();
		
		List<Mechanic> mechanics =repo.findAll();
		
		return DtoAssembler.toMechanicDtoList(mechanics);
	}

}
