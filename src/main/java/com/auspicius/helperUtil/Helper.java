package com.auspicius.helperUtil;

import java.sql.Timestamp;
import java.util.Date;

public  class Helper {
    public static Timestamp getCurrentTimeStamp() {
        return  new Timestamp(new Date().getTime());
    }

}
