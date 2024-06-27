package com.gabrielsenna.API_Rest_Web.services;

import com.gabrielsenna.API_Rest_Web.models.User;
import com.gabrielsenna.API_Rest_Web.repositories.TaskRepository;
import com.gabrielsenna.API_Rest_Web.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.RuntimeErrorException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found | ID: " + id));
    }

    @Transactional //Utilizar esta notação sempre que for realizar um insert ou update, para garantir a ATOMICIDADE
    public User createUser(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User updateUser(User obj) {
        User newObject = findById(obj.getId());
        newObject.setPassword(obj.getPassword());
        return this.userRepository.save(newObject);
    }

    public void deleteUser(Long id) {
        this.userRepository.findById(id);

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
           throw new RuntimeException("Não foi possível excluir o usuário, há entidades relacionadas! " + id);
        }
    }
}
