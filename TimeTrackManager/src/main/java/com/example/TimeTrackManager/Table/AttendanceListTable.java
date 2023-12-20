package com.example.TimeTrackManager.Table;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class AttendanceListTable {
    private int list_id;
    private int user_id;
    private String checkin_time;
    private String checkout_time;
}
