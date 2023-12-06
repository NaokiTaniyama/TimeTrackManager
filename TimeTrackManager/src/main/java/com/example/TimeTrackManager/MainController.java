package com.example.TimeTrackManager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

import static java.sql.Types.NULL;

@Controller
public class MainController {

    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @GetMapping("/login")
    public String index(Model model){
        model.addAttribute("name", "変数の受け渡し成功(リモートブランチテスト)");
        return "login";
    }


    @PostMapping("/AttendanceInputForm")
    //後にログイン画面から値を受け取る処理を追加
    public String AttendanceForm(Model model, @RequestParam("username") String username,
                                 @RequestParam("password") String password) {
        try{
            String sql = "SELECT id FROM user_list WHERE username = '" + username + "' AND password = '" + password + "'";
            Map<String, Object> result = jdbcTemplate.queryForMap(sql);

            int id = (int)result.get("id");
            if (id != NULL){
                model.addAttribute("index", "勤怠入力画面への遷移成功");
                return "AttendanceInputForm";
            }else {
                throw new IncorrectResultSizeDataAccessException(0);
            }
        }catch (IncorrectResultSizeDataAccessException e){
            model.addAttribute("index", "ユーザー名かパスワードが間違っています");
            return "login";
        }

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
