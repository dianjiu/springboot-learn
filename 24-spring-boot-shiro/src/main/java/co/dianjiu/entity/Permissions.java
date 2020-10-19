package co.dianjiu.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permissions {
    private String id;
    private String permissionsName;

    public Permissions() {
    }

    public Permissions(String id, String permissionsName) {
        this.id = id;
        this.permissionsName = permissionsName;
    }
}
