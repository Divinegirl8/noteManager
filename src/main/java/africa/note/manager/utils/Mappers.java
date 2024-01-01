package africa.note.manager.utils;

import africa.note.manager.data.models.User;

public class Mappers {
    public static User map(String username, String password,String userId){
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }
}
