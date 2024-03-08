package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class User {
    private Integer id;
    private String uuid;
    private String name;
    private String email;
    private String password;
    private Boolean isDeleted;
    private Boolean isVerified;
}
