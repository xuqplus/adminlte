package cn.xuqplus.adminlte.repository.user;

import cn.xuqplus.adminlte.domain.user.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByName(String name);

    User getById(Long id);

    User getByEmail(String email);

    boolean existsByName(String name);
}
