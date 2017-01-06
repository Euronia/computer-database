package com.excilys.formation.persistence.userdao.userdaoimpl;

import com.excilys.formation.entity.User;
import com.excilys.formation.persistence.userdao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by excilys on 04/01/17.
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository <User, Long> , UserDao {

     User getById (long id) ;
     User getByUsername (String name);
}
