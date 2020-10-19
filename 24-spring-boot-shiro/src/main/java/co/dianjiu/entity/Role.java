package co.dianjiu.entity;

import lombok.Getter;
import lombok.Setter;


import java.util.Set;

@Getter
@Setter
public class Role {

    private String id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;
    //省略set、get方法等.....


    public Role() {
    }

    public Role(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role(String id, String roleName, Set<Permissions> permissions) {
        this.id = id;
        this.roleName = roleName;
        this.permissions = permissions;
    }

}
