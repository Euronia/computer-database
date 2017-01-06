package com.excilys.formation.mapper;

import com.excilys.formation.entity.Computer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertNotNull;

/**
 * Created by excilys on 06/01/17.
 */

@Component
public class ComputerAndDtoMapperTest {

    @Autowired
    ComputerAndDtoMapper computerAndDtoMapper;

    public static String testString = "2012-01-01";
    @Test
    public void stringToLocalDate_ShouldReturnALocalDate ()
    {
        assertNotNull(computerAndDtoMapper.stringToLocalDate(testString));
    }

}
