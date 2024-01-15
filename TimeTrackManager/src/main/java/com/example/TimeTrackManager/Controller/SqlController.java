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
        int break_status = (int)result.get("break_status");
        //リストに追加
        String attendanceSql = "INSERT INTO attendance_list (list_id, user_id, checkin_time) VALUES ((SELECT count(*) + 1 FROM attendance_list), " + user_id + ", CURRENT_TIMESTAMP)";
        //新規作成したリストのuser_idをそのまま変数へ代入
        String listIdSql = "SELECT * FROM attendance_list WHERE list_id = (SELECT count(*) FROM attendance_list)";
        if (break_status == 0 && work_status == 0 ){
            jdbcTemplate.update(attendanceSql);
            Map<String, Object> attendance = jdbcTemplate.queryForMap(listIdSql);
            int list_id = (int)attendance.get("list_id");
            //どのattendance_listに対応しているかを保存
            jdbcTemplate.update("UPDATE user_list SET work_status = " + list_id + " WHERE id = " + user_id);
            model.addAttribute("start", "出勤中。＊無理はせずに、適度に休憩してください");
        }else if (break_status != 0){
            model.addAttribute("start", "すでに休憩中です。まずは、「休憩終了」ボタンを押してください");
        }else{
            model.addAttribute("start", "すでに出勤しています。退勤するには、「退勤」ボタンを押してください");
        }
        return "AttendanceInputForm";
    }

    @PostMapping("/endWork")
    public String endWork(Model model){
        int user_id = (int)session.getAttribute("id");
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * from user_list WHERE id = " + user_id);
        int work_status = (int)result.get("work_status");
        int break_status = (int)result.get("break_status");
        if (break_status == 0 && work_status != 0){
            //退勤処理
            String attendanceSql = "UPDATE attendance_list SET checkout_time = CURRENT_TIMESTAMP WHERE list_id = " + work_status;
            jdbcTemplate.update(attendanceSql);
            jdbcTemplate.update("UPDATE user_list SET work_status = 0 WHERE id = " + user_id);
            model.addAttribute("start", "退勤済み。お疲れさまでした！");
        }else if (break_status != 0){
            model.addAttribute("start", "すでに休憩中です。まずは、「休憩終了」ボタンを押してください");
        }else{
            model.addAttribute("start", "まずは出勤してください");
        }
        return "AttendanceInputForm";
    }

    @PostMapping("/startBreak")
    public String startBreak(Model model){
        int user_id = (int)session.getAttribute("id");
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * from user_list WHERE id = " + user_id);
        int break_status = (int)result.get("break_status");
        int work_status = (int)result.get("work_status");
        if (break_status == 0 && work_status != 0){
            String attendanceSql = "UPDATE attendance_list SET start_break_time = CURRENT_TIMESTAMP WHERE list_id = " + work_status;
            jdbcTemplate.update(attendanceSql);
            jdbcTemplate.update("UPDATE user_list SET break_status = 1 WHERE id = " + user_id);
            model.addAttribute("start", "休憩中");
        }else if (break_status != 0){
            model.addAttribute("start", "すでに休憩中です");
        }else {
            model.addAttribute("start", "この機能は、出勤状態でのみ使用できます");
        }
        return "AttendanceInputForm";
    }

    @PostMapping("/endBreak")
    public String endBreak(Model model){
        int user_id = (int)session.getAttribute("id");
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * from user_list WHERE id = " + user_id);
        int break_status = (int)result.get("break_status");
        int work_status = (int)result.get("work_status");
        if (break_status != 0 && work_status != 0){
            String attendanceSql = "UPDATE attendance_list SET end_break_time = CURRENT_TIMESTAMP WHERE list_id = " + work_status;
            jdbcTemplate.update(attendanceSql);
            jdbcTemplate.update("UPDATE user_list SET break_status = 0 WHERE id = " + user_id);
            model.addAttribute("start", "出勤中。＊無理をせず、適度に休憩してください");
        }else if (break_status == 0){
            model.addAttribute("start", "先に「休憩開始」ボタンを押してください");
        }else {
            model.addAttribute("start", "この機能は、出勤状態でのみ使用できます");
        }
        return "AttendanceInputForm";
    }
}
