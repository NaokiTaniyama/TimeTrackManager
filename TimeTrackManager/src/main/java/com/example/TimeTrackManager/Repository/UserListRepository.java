package com.example.TimeTrackManager.Repository;

import com.example.TimeTrackManager.Table.UserListTable;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.sql.Types.NULL;
import static javax.swing.UIManager.get;
import static javax.swing.text.html.CSS.getAttribute;

@Repository
public class UserListRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    HttpSession session;
    public List<UserListTable> findAll(){
        String sql = "SELECT * FROM user_list";
        List<UserListTable> list = new ArrayList<>();
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < mapList.size(); i++){
            Map<String, Object> map = mapList.get(i);
            System.out.println(map);
            UserListTable userListTable = new UserListTable();
            userListTable.setId((int)map.get("id"));
            userListTable.setUsername((String)map.get("username"));
            userListTable.setPassword((String)map.get("password"));
            userListTable.setLocation((String)map.get("location"));
            userListTable.setPhone_number((String)map.get("phone_number"));
            userListTable.setEmail_address((String)map.get("email_address"));
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
            userListTable.setWork_status(workStatusStr);
            list.add(userListTable);
        }
        return list;
    }

    public UserListTable findById(){
        int id = (int)session.getAttribute("id");
        String sql = "SELECT * FROM user_list WHERE id = " + id;
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        UserListTable userListTable = new UserListTable();
        userListTable.setId((int)map.get("id"));
        userListTable.setUsername((String)map.get("username"));
        userListTable.setPassword((String)map.get("password"));
        userListTable.setLocation((String)map.get("location"));
        userListTable.setPhone_number((String)map.get("phone_number"));
        userListTable.setEmail_address((String)map.get("email_address"));
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
        userListTable.setWork_status(workStatusStr);

        return userListTable;
    }

    public String locationTranceJP(String location){
        String locationJP = "";
        if (location.equals("home")){
            locationJP = "自宅";
        }else if (location.equals("office")){
            locationJP = "会社";
        }else {
            locationJP = "未入力";
        }
        return locationJP;
    }

    public List<UserListTable> findByDepartment(){
        String department = (String)session.getAttribute("department");
        String sql = "SELECT * FROM user_list WHERE department = '" + department + "'";
        List<UserListTable> list = new ArrayList<>();
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < mapList.size(); i++){
            Map<String, Object> map = mapList.get(i);
            System.out.println(map);
            UserListTable userListTable = new UserListTable();
            userListTable.setId((int)map.get("id"));
            userListTable.setUsername((String)map.get("username"));
            userListTable.setPassword((String)map.get("password"));
            userListTable.setLocation((String)map.get("location"));
            userListTable.setPhone_number((String)map.get("phone_number"));
            userListTable.setEmail_address((String)map.get("email_address"));
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
            userListTable.setWork_status(workStatusStr);
            list.add(userListTable);
        }
        return list;
    }

    public boolean loginCheck(String password, String username){
        System.out.println(password);
        System.out.println(username);
        try {
            String sql = "SELECT id FROM user_list WHERE username = '" + username + "' AND password = '" + password + "'";
            Map<String, Object> result = jdbcTemplate.queryForMap(sql);
            int id = (int)result.get("id");
            if (id != NULL){
                session.setAttribute("id",id);
                session.setAttribute("username",username);
                session.setAttribute("password",password);
                return true;
            }else {
                throw new IncorrectResultSizeDataAccessException(0);
            }
        }catch (IncorrectResultSizeDataAccessException e){
            return false;
        }

    }
}
