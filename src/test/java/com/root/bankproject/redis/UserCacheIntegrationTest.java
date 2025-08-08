package com.root.bankproject.redis;

import com.root.bankproject.entities.User;
import com.root.bankproject.factories.UserFactory;
import com.root.bankproject.repositories.UsersRepository;
import com.root.bankproject.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class UserCacheIntegrationTest {

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void clearCache() {
        cacheManager.getCache("account").clear();
        cacheManager.getCache("accountList").clear();
        cacheManager.getCache("user").clear();
        cacheManager.getCache("accountUserList").clear();

    }


    @Autowired
    private UsersService usersService;

    @MockitoBean
    private UsersRepository usersRepository;

    @Test
    void testSaveAndCache(){
        User user= UserFactory.createUser("test","test@email","as");

        when(usersRepository.save(user)).thenAnswer(invocation -> {
            User original = invocation.getArgument(0);

            return User.builder()
                    .id(1)
                    .email(original.getEmail())
                    .username(original.getUsername())
                    .password(original.getPassword())
                    .build();
        });

        when(usersRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        User saved = usersService.save(user);

        assertNotNull(saved);
        assertNotNull(saved.getId());

       usersService.findALl();
       usersService.findALl();

        verify(usersRepository, times(1)).save(user);
        verify(usersRepository, times(1)).findAll();

        usersService.save(UserFactory.createUser("jsad","sadasd","dasdsad"));
        usersService.findALl();
        usersService.findALl();

        verify(usersRepository, times(2)).findAll();


    }

    @Test
    void testFindByIdIsCached() {
        User user=User.builder()
                .id(12)
                .email("test@email")
                .username("test")
                .password("test")
                .build();

        when(usersRepository.findById(12)).thenReturn(Optional.of(user));

        usersService.findById(12);
        usersService.findById(12);
        verify(usersRepository, times(1)).findById(12);

    }
}
