package com.example.TimeTrackManager.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserListTable {
    private int id;
    private String username;
    private String password;
    private String location;
    private String phone_number;
    private String email_address;
    private String work_status;

}
