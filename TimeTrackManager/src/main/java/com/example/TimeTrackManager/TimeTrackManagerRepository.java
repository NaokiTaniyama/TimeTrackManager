package com.example.TimeTrackManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeTrackManagerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserListTable> findAll(){
        String sql = "SELECT * FROM user_list";
        List<UserListTable> list = new ArrayList<>();
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < mapList.size(); i++){
            Map<String, Object> map = mapList.get(i);
            System.out.println(map);
            UserListTable userListTable = new UserListTable();
            userListTable.setId((int)map.get("id"));
            userListTable.setUsername((String)map.get("username"));
            userListTable.setPassword((String)map.get("password"));
            userListTable.setLocation((String)map.get("location"));
            userListTable.setPhone_number((String)map.get("phone_number"));
            userListTable.setEmail_address((String)map.get("email_address"));
            userListTable.setWork_status((int)map.get("work_status"));
            list.add(userListTable);
        }
        return list;
    }
}
