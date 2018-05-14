package cn.xuqplus.adminlte.repository;

import cn.xuqplus.adminlte.domain.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRegisterRepository extends JpaRepository<UserRegister, Long> {

    List<UserRegister> findByExpiresAtLessThan(Long timeMills);
}
