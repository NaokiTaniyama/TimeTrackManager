package com.example.TimeTrackManager.Repository;

import com.example.TimeTrackManager.Table.StatusListViewTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class StatusListViewRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<StatusListViewTable> findAll(){
        String sql = "SELECT * FROM attendance_list INNER JOIN user_list ON user_list.id = attendance_list.user_id";
        List<StatusListViewTable> list = new ArrayList<>();
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (int i = 0; i < mapList.size(); i++){
            Map<String, Object> map = mapList.get(i);
            StatusListViewTable statusListViewTable = new StatusListViewTable();
            statusListViewTable.setUsername((String)map.get("username"));
            Timestamp checkInTime = (Timestamp) map.get("checkin_time");
            Timestamp checkOutTime = (Timestamp) map.get("checkout_time");
            statusListViewTable.setCheckin_time(sdf.format(checkInTime));
            statusListViewTable.setCheckout_time(sdf.format(checkOutTime));
            statusListViewTable.setLocation((String)map.get("location"));
            int workStatus = (int)map.get("work_status");
            int breakStatus = (int)map.get("break_status");
            String workStatusStr;
            if (workStatus == 0){
                workStatusStr = "未出勤";
            }else if (breakStatus != 0){
                workStatusStr = "休憩中";
            }else{
                workStatusStr = "出勤中";
            }

            System.out.println(breakStatus);

            statusListViewTable.setWork_status(workStatusStr);
            list.add(statusListViewTable);
        }
        return list;
    }
}
