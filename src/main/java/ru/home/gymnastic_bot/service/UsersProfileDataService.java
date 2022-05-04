package ru.home.gymnastic_bot.service;

import ru.home.gymnastic_bot.entity.UserProfileData;

import java.util.List;

public interface UsersProfileDataService {
    List<UserProfileData> getAllProfiles();

    void saveUserProfileData(UserProfileData userProfileData);
}
