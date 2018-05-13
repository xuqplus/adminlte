package cn.xuqplus.adminlte.repository;

import cn.xuqplus.adminlte.domain.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegisterRepository extends JpaRepository<UserRegister, Long> {
    UserRegister findByExpiresAtLessThan(Long timeMills);
}
