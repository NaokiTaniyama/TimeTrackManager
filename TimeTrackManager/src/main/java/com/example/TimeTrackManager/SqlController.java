package com.example.TimeTrackManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class SqlController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/startWork")
    public String startWork(Model model){
        //本来はログイン画面から持ってくる
        int user_id = 1;
        String view_1 = "SELECT * from time_track_manager";
        String view_2 = "SELECT * from attendance_list";
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * from time_track_manager WHERE id = " + user_id);
        int work_status = (int)result.get("work_status");
        //リストに追加
        String attendanceSql = "INSERT INTO attendance_list (list_id, checkin_time) VALUES ((SELECT count(*) + 1 FROM attendance_list), CURRENT_TIMESTAMP)";
        //新規作成したリストのidをそのまま変数へ挿入
        String listIdSql = "SELECT * FROM attendance_list WHERE list_id = (SELECT count(*) FROM attendance_list)";
        if (work_status == 0){
            jdbcTemplate.update(attendanceSql);
            Map<String, Object> attendance = jdbcTemplate.queryForMap(listIdSql);
            int list_id = (int)attendance.get("list_id");
            //どのattendance_listに対応しているかを保存
            jdbcTemplate.update("UPDATE time_track_manager SET work_status = " + list_id + " WHERE id = " + user_id);
            model.addAttribute("start", "出勤成功");
        }else{
            model.addAttribute("start", "すでに出勤済みです");
        }

        System.out.println(jdbcTemplate.queryForList(view_1));
        System.out.println(jdbcTemplate.queryForList(view_2));
        return "AttendanceInputForm";
    }

    @PostMapping("/endWork")
    public String endWork(Model model){
        //本来はログイン画面から持ってくる
        int user_id = 1;
        String view_1 = "SELECT * from time_track_manager";
        String view_2 = "SELECT * from attendance_list";
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * from time_track_manager WHERE id = " + user_id);
        int work_status = (int)result.get("work_status");
        if (work_status != 0){
            //退勤処理
            String attendanceSql = "UPDATE attendance_list SET checkout_time = CURRENT_TIMESTAMP WHERE list_id = " + work_status;
            jdbcTemplate.update(attendanceSql);
            jdbcTemplate.update("UPDATE time_track_manager SET work_status = 0 WHERE id = " + user_id);
            model.addAttribute("start", "退勤成功");
        }else{
            model.addAttribute("start", "まずは出勤してください");
        }
        return "AttendanceInputForm";
    }
}
