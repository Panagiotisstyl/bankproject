    package com.root.bankproject.services;

    import com.root.bankproject.entities.User;
    import com.root.bankproject.repositories.UsersRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.cache.annotation.CacheEvict;
    import org.springframework.cache.annotation.CachePut;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @RequiredArgsConstructor
    @Service
    public class UsersService {

        private final UsersRepository usersRepository;

        @Cacheable(value="userList")
        public List<User> findALl(){
            return usersRepository.findAll();
        }

        @Cacheable(value="user",key="#id")
        public User findById(Integer id){
            return usersRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        }

        @CachePut(value="user",key="#result.id",condition = "#result != null")
        @CacheEvict(value = "userList", allEntries = true)
        public User save(User user){
            return usersRepository.save(user);
        }


        @Cacheable(value="user",key="#email")
        public User findByEmail(String email) {
            return usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        }

        @Cacheable(value="accountUserList",key="#accountId")
        public List<User> findByAccountId(int accountId) {
            return usersRepository.findAllByAccounts_Id(accountId);
        }
    }
