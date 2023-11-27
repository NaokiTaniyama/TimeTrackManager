package com.example.TimeTrackManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SqlController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/startWork")
    public String startWork(Model model){
        String sql = "SELECT * from time_track_manager";
        model.addAttribute("start", "出勤成功");
        System.out.println(jdbcTemplate.queryForList(sql));
        return "AttendanceInputForm";
    }
}
