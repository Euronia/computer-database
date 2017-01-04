package com.excilys.formation.persistence.userdao.userdaoimpl;

import com.excilys.formation.entity.User;
import com.excilys.formation.persistence.userdao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by excilys on 04/01/17.
 */
public interface UserRepository extends JpaRepository <User, Long> , UserDao {

     User getById (long id) ;
     User getByUsername (String name);
}
