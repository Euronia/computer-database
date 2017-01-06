package com.excilys.formation.service.computerservice.ComputerServiceImpl;

import com.excilys.formation.config.SpringServiceConfig;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.service.computerservice.computerserviceimpl.ComputerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by excilys on 06/01/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringServiceConfig.class})
public class ComputerServiceImplTest {

    @Autowired
    ComputerService cs;

    @Test
    public void findById_ShouldReturnOneComputerEntry() {

        //assert correct type/impl
        //assertThat(cs, instanceOf(ComputerServiceImpl.class));

        //assert
        assertNull(cs.getById(1));
    }
}
