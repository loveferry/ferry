package cn.org.ferry.sys.mapper;

import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.sys.dto.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {
    /**
     * 查询用户信息
     *      - security 认证
     * @param userName 用户名称
     * @return 用户个人信息
     */
    SysUser queryByUserNameForSecurityAuthentication(@Param("userName") String userName);

    /**
     * 查询用户信息
     *  登录成功返回数据
     * @param userName 用户名称
     * @return 用户个人信息
     */
    SysUser queryForLoginSuccess(@Param("userName") String userName);

    /**
     * 查询用户具有的角色
     */
    List<String> queryRoleCodesByUserCode(@Param("userCode") String userCode);
}
