package com.excilys.formation.service.userservice.userserviceimpl;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.entity.User;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.persistence.userdao.userdaoimpl.UserRepository;
import com.excilys.formation.service.userservice.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by excilys on 04/01/17.
 */

@Service ("userService")
public class UserServiceImpl implements UserService, UserDetailsService{

    private static Logger logger;

    static {
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");
    }

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public User create(User user)
    {
        try {
            userRepository.create(user);
        } catch (PersistenceException e) {
            logger.error("UserServiceImpl : create(User user) catched a PersistenceException",e);
        }
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user;
        user = userRepository.getByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found for %s",name));
        }
        return user;
    }
}
