package co.dianjiu.jwt.mapper;

import co.dianjiu.jwt.model.PermissionDto;
import co.dianjiu.jwt.model.RoleDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * PermissionMapper
 * @author dolyw.com
 * @date 2018/8/31 14:42
 */
public interface PermissionMapper extends Mapper<PermissionDto> {
    /**
     * 根据Role查询Permission
     * @param roleDto
     * @return java.util.List<com.wang.model.PermissionDto>
     * @author dolyw.com
     * @date 2018/8/31 11:30
     */
    List<PermissionDto> findPermissionByRole(RoleDto roleDto);
}