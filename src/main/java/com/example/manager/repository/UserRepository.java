package com.example.manager.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;
import com.example.manager.domain.User;
import org.springframework.stereotype.Service;


//@Service
//@EnableMongoRepositories
//@EnableMongoRepositories
@Repository
public interface UserRepository extends MongoRepository<User, String> {


    //boolean checkIfUsernameTaken(String username);
	
}
