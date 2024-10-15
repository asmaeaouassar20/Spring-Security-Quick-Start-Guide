package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.service;

import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.model.MyUser;
import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserService {
    @Autowired
    private MyUserRepository myUserRepository;


    public List<MyUser> getAllUsers(){
        return myUserRepository.findAll();
    }
}
