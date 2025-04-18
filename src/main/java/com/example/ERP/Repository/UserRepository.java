package com.example.ERP.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ERP.Models.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.ERP.Models.Role;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByEmail(String email);
    List<User> findByRole(Role role);
}
