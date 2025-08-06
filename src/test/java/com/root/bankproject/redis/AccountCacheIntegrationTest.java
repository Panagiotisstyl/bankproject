package com.root.bankproject.redis;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.root.bankproject.entities.Account;
import com.root.bankproject.enums.TypeAccount;
import com.root.bankproject.factories.AccountFactory;
import com.root.bankproject.repositories.AccountsRepository;
import com.root.bankproject.services.AccountsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class AccountCacheIntegrationTest {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private AccountFactory accountFactory;

    @MockitoBean
    private AccountsRepository accountsRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void clearCache() {
        cacheManager.getCache("account").clear();
        cacheManager.getCache("accountList").clear();
        cacheManager.getCache("user").clear();
        cacheManager.getCache("accountUserList").clear();
        // Clear any other caches you use
    }

    @Test
    void testSaveAndCache() {

        Account acc = accountFactory.createAccount(TypeAccount.JOINT, "test", 500.00);

        //so it won't return a saved account with no id, mocks don't generate id
        when(accountsRepository.save(acc)).thenAnswer(invocation -> {
            Account original = invocation.getArgument(0);

            return Account.builder()
                    .id(123)
                    .typeAccount(original.getTypeAccount())
                    .description(original.getDescription())
                    .balance(original.getBalance())
                    .build();
        });

        //so it won't return a null list
        when(accountsRepository.findAll()).thenReturn(java.util.Collections.emptyList());


        Account saved = accountsService.save(acc);


        assertNotNull(saved);
        assertNotNull(saved.getId());


        accountsService.findAll();
        accountsService.findAll();

        verify(accountsRepository, times(1)).save(acc);
        verify(accountsRepository, times(1)).findAll();

        accountsService.save(accountFactory.createAccount(TypeAccount.SINGLE , "testzxc", 5020.00));
        accountsService.findAll();
        accountsService.findAll();

        verify(accountsRepository, times(2)).findAll();
    }

    @Test
    void testFindByIdIsCached() {
        Account account = Account.builder()
                .id(12)
                .typeAccount(TypeAccount.JOINT)
                .description("test")
                .balance(500.00)
                .build();

        when(accountsRepository.findById(12)).thenReturn(Optional.of(account));

        accountsService.findById(12);
        accountsService.findById(12);
        verify(accountsRepository, times(1)).findById(12);

    }


}
