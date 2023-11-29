package com.example.TimeTrackManager;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeTrackManagerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TimeTrackManagerTable> findAll(){
        String sql = "SELECT * FROM time_track_manager";
        List<TimeTrackManagerTable> list = new ArrayList<>();
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < mapList.size(); i++){
            Map<String, Object> map = mapList.get(i);
            System.out.println(map);
            TimeTrackManagerTable timeTrackManagerTable = new TimeTrackManagerTable();
            timeTrackManagerTable.setId((int)map.get("id"));
            timeTrackManagerTable.setUsername((String)map.get("username"));
            timeTrackManagerTable.setPassword((String)map.get("password"));
            timeTrackManagerTable.setLocation((String)map.get("location"));
            timeTrackManagerTable.setPhone_number((String)map.get("phone_number"));
            timeTrackManagerTable.setEmail_address((String)map.get("email_address"));
            timeTrackManagerTable.setWork_status((int)map.get("work_status"));
            list.add(timeTrackManagerTable);
        }
        return list;
    }
}
