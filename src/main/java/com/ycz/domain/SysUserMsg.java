package com.ycz.domain;/*
 @author ycz
 @date 2021-10-29-10:52
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserMsg {
    private Integer id;

    private Integer sendUserId;

    private Integer receiveUserId;

    private String sendMsg;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

}
