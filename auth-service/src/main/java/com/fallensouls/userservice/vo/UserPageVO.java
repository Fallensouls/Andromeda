package com.fallensouls.userservice.vo;

import com.fallensouls.userservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserPageVO {
    private long totalNumber;
    private long totalPageNumber;
    private List<User> userList;
}
