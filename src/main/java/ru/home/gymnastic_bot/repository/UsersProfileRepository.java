package ru.home.gymnastic_bot.repository;

//Settings for mysql
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.home.gymnastic_bot.entity.UserProfileData;


@Repository
public interface UsersProfileRepository extends MongoRepository<UserProfileData, String> {
}

//Settings for mysql
/*
@Repository
public interface UsersProfileRepository extends JpaRepository<UserProfileData, String> {
}*/
