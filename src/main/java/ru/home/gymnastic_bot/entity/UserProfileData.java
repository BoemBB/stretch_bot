package ru.home.gymnastic_bot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.*;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "userProfileData")
public class UserProfileData {

    @Id
    String chatId;

    long userId;
    String sex;
    String name;
    String typeOfLive;
    String messages;
    int age;
    int changeNeck = 0;
    int changeBack = 0;

    public void changeRandomNeck(){
        if (changeNeck % 2 == 0){
            changeNeck = 0;
        }
        changeNeck++;
    }

    public void changeRandomBack(){
        if (changeBack % 2 == 0){
            changeBack = 0;
        }
        changeBack++;
    }

    public void addMessage(String message){
        if (message.length() > 220){
            messages = "";
        }
        messages = getMessages() + "\n" + message;
    }
}


//Settings for mysql
/*
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "user_profile_data")
public class UserProfileData {

    @Id
    @Column(name = "chat_id")
    String chatId;

    @Column(name = "user_id")
    long userId;

    @Column
    String sex;

    @Column
    String name;

    @Column(name = "type_of_life")
    String typeOfLive;

    @Column
    String messages;

    @Column
    int age;

    @Column(name = "change_neck")
    int changeNeck = 0;

    @Column(name = "change_back")
    int changeBack = 0;

    public void changeRandomNeck(){
        if (changeNeck % 2 == 0){
            changeNeck = 0;
        }
        changeNeck++;
    }

    public void changeRandomBack(){
        if (changeBack % 2 == 0){
            changeBack = 0;
        }
        changeBack++;
    }

    public void addMessage(String message){
        if (message.length() > 220){
            messages = "";
        }
        messages = getMessages() + "\n" + message;
    }
}
*/

