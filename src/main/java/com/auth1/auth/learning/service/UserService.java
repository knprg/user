package com.auth1.auth.learning.service;

import com.auth1.auth.learning.model.Token;
import com.auth1.auth.learning.model.User;
import com.auth1.auth.learning.repository.TokenRepository;
import com.auth1.auth.learning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
   @Autowired
    private UserRepository userRepository;
   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @Autowired
   private TokenRepository tokenRepository;
    public User signUp(String email, String password, String name){
     User user=new User();
     user.setEmail(email);
     user.setName(name);
     user.setPassword(bCryptPasswordEncoder.encode(password));
     return userRepository.save(user);
    }

    public Token login(String email, String password) {
             Optional<User> userOptional=userRepository.findByEmail(email);
             if(userOptional.isEmpty()){
                 throw new RuntimeException("Inalid user or password");
             }
             User user=userOptional.get();
             if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
                 throw new RuntimeException("Inalid user or password");
             }
             Token token=new Token();
             token.setUser(user);
             token.setValue(UUID.randomUUID().toString());

        Date expiredDate=getExpiredDate();
        token.setExpireAt(expiredDate);
        return tokenRepository.save(token);
    }
    //expiration date will be 30 days after today
    private Date getExpiredDate() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(new Date());


        //add(Calendar.DAY_OF_MONTH, -5).
        calendarDate.add(Calendar.DAY_OF_MONTH, 30);

        Date expiredDate = calendarDate.getTime();
        return expiredDate;

    }

    public void logout(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(token, false);
        if(tokenOptional.isEmpty()){
            throw new RuntimeException("token is invalid");
        }
        Token tokenObject=tokenOptional.get();
        tokenObject.setDeleted(true);
        tokenRepository.save(tokenObject);
    }

    public boolean validateToken(String token) {

        // check if token value is present
        //check if token is not deleted
        // check if token is not expired

        Optional<Token>tokenOptional=tokenRepository.findByValueAndDeletedEqualsAndExpireAtGreaterThan(token,false,new Date());
       if(tokenOptional.isEmpty()){
           return false;
       }
       return true;
    }
}
