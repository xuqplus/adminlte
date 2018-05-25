package cn.xuqplus.adminlte.repository.user;

import cn.xuqplus.adminlte.domain.user.UserReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserResetRepository extends JpaRepository<UserReset, Long> {

    List<UserReset> findByExpiresAtLessThan(Long timeMills);
}
