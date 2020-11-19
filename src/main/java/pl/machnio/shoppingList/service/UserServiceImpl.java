package pl.machnio.shoppingList.service;

import org.springframework.stereotype.Service;
import pl.machnio.shoppingList.entity.User;
import pl.machnio.shoppingList.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUserById(long id) {

    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public boolean isUserInDatabase(Integer id, String email) {
        return false;
    }
}
