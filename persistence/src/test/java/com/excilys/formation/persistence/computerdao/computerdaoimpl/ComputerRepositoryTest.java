package com.excilys.formation.persistence.computerdao.computerdaoimpl;

import com.excilys.formation.config.SpringPersistenceConfig;
import com.excilys.formation.entity.Computer;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by excilys on 06/01/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringPersistenceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:computer-entries.xml")
public class ComputerRepositoryTest {

    @Autowired
    private ComputerRepository repository;

    @Test
    public void findById_ShouldReturnOneComputerEntry() {
        Computer searchResult = repository.findById(1);
        assertNotNull(searchResult);
    }


}
