package com.example.TimeTrackManager;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class StatusListViewTable {
    private String username;
    private String checkin_time;
    private String checkout_time;
    private String work_status;
    private String location;
}
