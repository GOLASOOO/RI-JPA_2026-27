package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.types.Nif;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository 
			extends BaseJpaRepository<Mechanic> 
			implements MechanicRepository {

	public Optional<Mechanic> findByNif(Nif nif) {
//		String q = "SELECT m FROM Mechanic m WHERE m.nif = :nif"; // Externalized from orm.xml file to here. The query is now in the orm.xml file, so we can use the named query instead of the JPQL query.

		// Could return null if not found. We prefer using an optional as we must think
		// about the possibility of getting a null value.
		Optional<Mechanic> m = Jpa.getManager()
				.createNamedQuery("Mechanic.findByNif", Mechanic.class)
				.setParameter(1, nif) // Starts in 1, not 0. The first parameter is 1, the second is 2, etc.
				.getResultStream()
				.findFirst(); // Returns an optional, which is what we want. If there is no result, it will return an empty optional.;
		return m;
//		

	}

	// Not needed as it is implemented in the BaseJpaRepository<T>. Only required to
	// implement specific queries, like findByNif(Nif nif) in this case.

//	@Override
//	public void add(Mechanic t) {
////		throw new UnsupportedOperationException("Not yet implemented");
//		Jpa.getManager().persist(t);
//		
//	}
//
//	@Override
//	public void remove(Mechanic t) {
////		throw new UnsupportedOperationException("Not yet implemented");
//		Jpa.getManager().remove(t);
//		
//	}
//
//	@Override
//	public Optional<Mechanic> findById(String id) {
////		throw new UnsupportedOperationException("Not yet implemented");
//		Mechanic m = Jpa.getManager().find(Mechanic.class, id); // Could return null if not found. We prefer using an optional as we must think about the possibility of getting a null value. 
//		return Optional.ofNullable(m); // If m is null, it will return an empty optional. If m is not null, it will return an optional containing m.
//		
//	}
//
//	@Override
//	public List<Mechanic> findAll() {
//		throw new UnsupportedOperationException("Not yet implemented");
//	}

}
