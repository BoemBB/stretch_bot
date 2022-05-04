package ru.home.gymnastic_bot.cache;

import ru.home.gymnastic_bot.entity.UserProfileData;

public interface DataCache {
    UserProfileData getOrCreateUserProfileData(long userId);

    //void saveUserProfileData(long userId, UserProfileData userProfileData);
}
