package ru.home.gymnastic_bot.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.home.gymnastic_bot.entity.UserProfileData;
import ru.home.gymnastic_bot.service.UsersProfileDataService;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
@Data
public class UserDataCache implements DataCache, InitializingBean {

    private final UsersProfileDataService profileDataService;
    private final Map<Long, UserProfileData> users = new HashMap<>();

    public UserDataCache(UsersProfileDataService profileDataService) {
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
        if (profileDataService.getAllProfiles() != null){
            for (UserProfileData user: profileDataService.getAllProfiles()
            ) {
                users.put(user.getUserId(), user);
                System.out.println(user);
            }
        }
    }
}
