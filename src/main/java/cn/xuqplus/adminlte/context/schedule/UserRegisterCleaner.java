package cn.xuqplus.adminlte.context.schedule;

import cn.xuqplus.adminlte.repository.UserRegisterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterCleaner.class);

    @Autowired
    UserRegisterRepository userRegisterRepository;

    @Scheduled(initialDelay = 1000L * 60, fixedDelay = 1000L * 60 * 5)
    public void clean() {
        userRegisterRepository.delete(userRegisterRepository.findByExpiresAtLessThan(System.currentTimeMillis()));
    }
}