package ru.home.gymnastic_bot.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.home.gymnastic_bot.entity.UserProfileData;
import ru.home.gymnastic_bot.service.UsersProfileDataServiceImpl;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Data
public class UserDataCache implements DataCache, InitializingBean {

    private final UsersProfileDataServiceImpl profileDataService;
    private final Map<Long, UserProfileData> users = new HashMap<>();

    public UserDataCache(UsersProfileDataServiceImpl profileDataService) {
        this.profileDataService = profileDataService;
    }

    @Override
    public UserProfileData getOrCreateUserProfileData(long userId) {

        UserProfileData userProfileData = users.get(userId);
        if (userProfileData == null) {
            userProfileData = new UserProfileData();
            users.put(userId, userProfileData);
        }

        return userProfileData;
    }

    public void afterPropertiesSet() {
        fillTheMapFromDb();
    }

    public void fillTheMapFromDb(){

        log.info("Try to fill the map");

        if (profileDataService.getAllProfiles() != null){
            for (UserProfileData user: profileDataService.getAllProfiles()
            ) {
                users.put(user.getUserId(), user);
            }
        }

        log.info("Map is filled successfully");
    }
}
