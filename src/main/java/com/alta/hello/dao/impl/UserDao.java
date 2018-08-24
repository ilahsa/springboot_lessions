package com.alta.hello.dao.impl;

import com.alta.hello.dao.BaseDao;
import org.springframework.stereotype.Component;
import com.alta.hello.domain.*;
import java.util.List;

/**
 * Created by baiba on 2018-08-23.
 */
@Component
public class UserDao extends BaseDao<User> {

    private static final String SELECT_ALL = "select `name`,`password` from user where id=?";

    public List<User> all() {
        return super.all(SELECT_ALL, 2);
    }
}