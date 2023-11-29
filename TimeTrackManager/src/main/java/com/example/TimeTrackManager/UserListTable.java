package com.example.TimeTrackManager;

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
    private int work_status;

}
