package com.example.TimeTrackManager.Controller;

import com.example.TimeTrackManager.Repository.StatusListViewRepository;
import com.example.TimeTrackManager.Repository.UserListRepository;
import com.example.TimeTrackManager.Table.UserListTable;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static java.sql.Types.NULL;

@Controller
public class MainController {


    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    UserListRepository userListRepository;

    @Autowired
    StatusListViewRepository statusListViewRepository;
    @GetMapping("/login")
    public String index(Model model){
        return "login";
    }

    @PostMapping("/logout")
    public String logout(Model model){
        session.invalidate();
        return "login";
    }

    @PostMapping("/AttendanceInputFormNoParam")
    public String AttendanceFormNoParam(Model model) {
        String username = (String)session.getAttribute("username");
        String password = (String)session.getAttribute("password");
        try{
            String sql = "SELECT id FROM user_list WHERE username = '" + username + "' AND password = '" + password + "'";
            Map<String, Object> result = jdbcTemplate.queryForMap(sql);

            int id = (int)result.get("id");
            if (id != NULL){
                UserListTable userListTable = userListRepository.findById();
                String workStatus = userListTable.getWork_status();
                model.addAttribute("status", workStatus);
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
        return "LocationInputForm";
    }

    @PostMapping("/ContactInputForm")
    public String ContactForm(Model model){
        return "ContactInputForm";
    }

    @PostMapping("/AttendanceStatusListView")
    public String AttendanceView(Model model){
        model.addAttribute("view", userListRepository.findByDepartment());
        return "AttendanceStatusListView";
    }

    @PostMapping("/AttendanceInputForm")
    public String AttendanceForm(Model model, @RequestParam("username") String username,
                                 @RequestParam("password") String password) {
        try{
            String sql = "SELECT * FROM user_list WHERE username = '" + username + "' AND password = '" + password + "'";
            Map<String, Object> result = jdbcTemplate.queryForMap(sql);

            int id = (int)result.get("id");
            if (id != NULL){
                String department = (String)result.get("department");
                model.addAttribute("name", "こんにちは、" +  username + "さん。");
                session.setAttribute("username",username);
                session.setAttribute("password",password);
                session.setAttribute("id",id);
                session.setAttribute("department",department);
                UserListTable userListTable = userListRepository.findById();
                String workStatus = userListTable.getWork_status();
                model.addAttribute("status", workStatus);
                return "AttendanceInputForm";
            }else {
                throw new IncorrectResultSizeDataAccessException(0);
            }
        }catch (IncorrectResultSizeDataAccessException e){
            model.addAttribute("index", "ユーザー名かパスワードが間違っています");
            return "login";
        }
    }

    @PostMapping("/AttendanceInputForm/LocationUpdate")
    public String locationUpdate(Model model, @RequestParam("location") String location){
        int id = (int)session.getAttribute("id");
        String locationStr = userListRepository.locationTranceJP(location);
        String sql = "UPDATE user_list SET location = '" + locationStr + "' WHERE id = " + id;
        jdbcTemplate.update(sql);
        model.addAttribute("index", "出勤場所を登録しました");
        UserListTable userListTable = userListRepository.findById();
        String workStatus = userListTable.getWork_status();
        model.addAttribute("status", workStatus);
        return "AttendanceInputForm";

    }

    @PostMapping("/ContactInputForm/ContactUpdate")
    public String contactUpdate(Model model, @RequestParam("phone_number") String phone_number,
                                @RequestParam("email_address") String email_address){
        int id = (int)session.getAttribute("id");
        String sql = "UPDATE user_list SET phone_number = '" + phone_number + "', email_address = '" + email_address + "' WHERE id = " + id;
        jdbcTemplate.update(sql);
        UserListTable userListTable = userListRepository.findById();
        String workStatus = userListTable.getWork_status();
        model.addAttribute("status", workStatus);
        model.addAttribute("index", "連絡先を登録しました");
        return "AttendanceInputForm";
    }

}
