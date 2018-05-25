package cn.xuqplus.adminlte.context.schedule.user;

import cn.xuqplus.adminlte.domain.user.UserRegister;
import cn.xuqplus.adminlte.repository.user.UserRegisterRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRegisterCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterCleaner.class);

    @Autowired
    UserRegisterRepository userRegisterRepository;

    @Scheduled(initialDelay = 1000L * 60, fixedDelay = 1000L * 60 * 5)
    public void clean() {
        try {
            List<UserRegister> userRegisters = userRegisterRepository.findByExpiresAtLessThan(System.currentTimeMillis());
            if (CollectionUtils.isEmpty(userRegisters)) {
                return;
            }
            userRegisterRepository.deleteAll(userRegisters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}