package se.lexicon.g46lecturejpa.dao;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import se.lexicon.g46lecturejpa.entity.Address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class AddressDaoImplTest {
    @Autowired
    AddressDao addressDao;

    Address getTestAddr() {
        return new Address("Test St", "SomeCity", "34354");
    }

    @BeforeEach
    void setUp() {
        addressDao.persist(getTestAddr());
    }

    @Test
    void testPersistAddress() {
        Address addr = getTestAddr();
        Address insertedAddr = addressDao.persist(addr);

        assertNotNull(insertedAddr);
    }

    @Test
    void testFindById() {
        Optional<Address> toFind = addressDao.findAll().stream().findFirst();
        assertTrue(toFind.isPresent());
        Optional<Address> addr = addressDao.findById(toFind.get().getId());
        assertTrue(addr.isPresent());
        assertEquals(addr.get(), toFind.get());
    }

    @Test
    void testFindAll() {
        List<Address> addresses = new ArrayList<>(addressDao.findAll());
        assertEquals(addresses.size(), 1);
    }

    @Test
    void testUpdate() {
        Optional<Address> toUpdate = addressDao.findAll().stream().findFirst();
        assertTrue(toUpdate.isPresent());
        Address addr = toUpdate.get();

        addr.setCity("Another City");
        addressDao.update(addr);
        Optional<Address> afterUpdate = addressDao.findById(addr.getId());
        assertTrue(afterUpdate.isPresent());
        assertEquals(afterUpdate.get().getCity(), "Another City");
    }

    @Test
    void testRemove() {
        Optional<Address> toRemove = addressDao.findAll().stream().findFirst();
        assertTrue(toRemove.isPresent());
        Address addr = toRemove.get();

        addressDao.remove(addr.getId());
        assertEquals(0, addressDao.findAll().size());
    }
}
