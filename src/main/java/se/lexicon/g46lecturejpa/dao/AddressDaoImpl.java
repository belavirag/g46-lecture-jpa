package se.lexicon.g46lecturejpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import se.lexicon.g46lecturejpa.entity.Address;

import java.util.Collection;
import java.util.Optional;

@Repository
public class AddressDaoImpl implements AddressDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Address persist(Address address) {
        entityManager.persist(address);
        return address;
    }

    @Override
    public Optional<Address> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Address.class, id));
    }

    @Override
    public Collection<Address> findAll() {
        return entityManager.createQuery("select a from Address a", Address.class).getResultList();
    }

    @Override
    @Transactional
    public void update(Address address) {
        entityManager.merge(address);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        var found = this.findById(id);
        if (found.isEmpty()) {
            // todo: throw exception
            return;
        }

        entityManager.remove(found.get());
    }
}
