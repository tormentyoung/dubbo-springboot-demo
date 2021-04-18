package com.xxb.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxb.springboot.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

}
