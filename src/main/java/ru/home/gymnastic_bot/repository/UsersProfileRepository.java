package ru.home.gymnastic_bot.repository;

//Settings for mysql
/*import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;*/
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.home.gymnastic_bot.entity.UserProfileData;


@Repository
public interface UsersProfileRepository extends MongoRepository<UserProfileData, String> {
    UserProfileData findByChatId(String chatId);
}

//Settings for mysql
/*
@Repository
public interface UsersProfileRepository extends JpaRepository<UserProfileData, String> {
    UserProfileData findByChatId(String chatId);
}*/
