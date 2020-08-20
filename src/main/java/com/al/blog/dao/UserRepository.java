package com.al.blog.dao;

import com.al.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
// <Dao操作的对象，主键类型>
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameandAndPassword(String username,String password);
}
