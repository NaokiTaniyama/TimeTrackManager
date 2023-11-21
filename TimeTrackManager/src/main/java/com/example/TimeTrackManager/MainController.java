package com.example.TimeTrackManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {


    @GetMapping("/login")
    public String index(Model model){
        model.addAttribute("name", "変数の受け渡し成功(リモートブランチテスト)");
        return "login";
    }

    @PostMapping("/AttendanceInputForm")
    //後にログイン画面から値を受け取る処理を追加
    public String AttendanceForm(Model model){
        model.addAttribute("index", "勤怠入力画面への遷移成功");
        return "AttendanceInputForm";
    }

    @PostMapping("/LocationInputForm")
    public String LocationForm(Model model){
        model.addAttribute("index", "勤務場所登録画面への遷移成功");
        return "LocationInputForm";
    }

    @PostMapping("/ContactInputForm")
    public String ContactForm(Model model){
        model.addAttribute("index", "連絡先登録画面への遷移成功");
        return "ContactInputForm";
    }

    @PostMapping("/AttendanceStatusListView")
    public String AttendanceView(Model model){
        model.addAttribute("index", "勤怠状況一覧表示画面への遷移成功");
        return "AttendanceStatusListView";
    }

}
