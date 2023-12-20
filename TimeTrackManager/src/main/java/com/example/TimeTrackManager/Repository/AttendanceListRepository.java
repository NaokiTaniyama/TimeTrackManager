package com.example.TimeTrackManager.Repository;

import com.example.TimeTrackManager.Table.AttendanceListTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendanceListRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AttendanceListTable> findAll(){
        String sql = "SELECT * FROM attendance_list";
        List<AttendanceListTable> list = new ArrayList<>();
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < mapList.size(); i++){
            Map<String, Object> map = mapList.get(i);
            System.out.println(map);
            AttendanceListTable attendanceListTable = new AttendanceListTable();
            attendanceListTable.setList_id((int)map.get("list_id"));
            attendanceListTable.setUser_id((int)map.get("user_id"));
            attendanceListTable.setCheckin_time((String)map.get("checkin_time"));
            attendanceListTable.setCheckout_time((String)map.get("checkout_time"));
            list.add(attendanceListTable);
        }
        return list;
    }
}
