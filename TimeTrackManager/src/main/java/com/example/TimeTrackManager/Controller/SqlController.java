package com.example.TimeTrackManager.Controller;

import jakarta.servlet.http.HttpSession;
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

    @Autowired
    HttpSession session;

    @PostMapping("/startWork")
    public String startWork(Model model){
        int user_id = (int)session.getAttribute("id");
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * from user_list WHERE id = " + user_id);
        int work_status = (int)result.get("work_status");
        //リストに追加
        String attendanceSql = "INSERT INTO attendance_list (list_id, user_id, checkin_time) VALUES ((SELECT count(*) + 1 FROM attendance_list), " + user_id + ", CURRENT_TIMESTAMP)";
        //新規作成したリストのuser_idをそのまま変数へ代入
        String listIdSql = "SELECT * FROM attendance_list WHERE list_id = (SELECT count(*) FROM attendance_list)";
        if (work_status == 0){
            jdbcTemplate.update(attendanceSql);
            Map<String, Object> attendance = jdbcTemplate.queryForMap(listIdSql);
            int list_id = (int)attendance.get("list_id");
            //どのattendance_listに対応しているかを保存
            jdbcTemplate.update("UPDATE user_list SET work_status = " + list_id + " WHERE id = " + user_id);
            model.addAttribute("start", "出勤成功");
        }else{
            model.addAttribute("start", "すでに出勤済みです");
        }
        return "AttendanceInputForm";
    }

    @PostMapping("/endWork")
    public String endWork(Model model){
        int user_id = (int)session.getAttribute("id");
        String view_1 = "SELECT * from user_list";
        String view_2 = "SELECT * from attendance_list";
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * from user_list WHERE id = " + user_id);
        int work_status = (int)result.get("work_status");
        if (work_status != 0){
            //退勤処理
            String attendanceSql = "UPDATE attendance_list SET checkout_time = CURRENT_TIMESTAMP WHERE list_id = " + work_status;
            jdbcTemplate.update(attendanceSql);
            jdbcTemplate.update("UPDATE user_list SET work_status = 0 WHERE id = " + user_id);
            model.addAttribute("start", "退勤成功");
        }else{
            model.addAttribute("start", "まずは出勤してください");
        }
        return "AttendanceInputForm";
    }
}
