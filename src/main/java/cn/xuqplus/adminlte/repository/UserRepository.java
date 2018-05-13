package cn.xuqplus.adminlte.repository;

import cn.xuqplus.adminlte.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByName(String name);

    User getById(Long id);

    User getByEmail(String email);

    boolean existsByName(String name);
}
