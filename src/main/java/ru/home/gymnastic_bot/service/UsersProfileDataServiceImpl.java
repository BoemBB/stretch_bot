package ru.home.gymnastic_bot.service;

import org.springframework.stereotype.Service;
import ru.home.gymnastic_bot.entity.UserProfileData;
import ru.home.gymnastic_bot.repository.UsersProfileRepository;

import java.util.List;

@Service
public class UsersProfileDataServiceImpl implements UsersProfileDataService{

    private final UsersProfileRepository profileRepository;

    public UsersProfileDataServiceImpl(UsersProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<UserProfileData> getAllProfiles() {
        return profileRepository.findAll();
    }

    public void saveUserProfileData(UserProfileData userProfileData) {
        profileRepository.save(userProfileData);
    }

}
