package com.demo.mybatis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mybatis.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

}
