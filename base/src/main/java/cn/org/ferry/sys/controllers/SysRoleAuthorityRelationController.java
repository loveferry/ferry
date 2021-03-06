package cn.org.ferry.sys.controllers;

import cn.org.ferry.sys.dto.SysRoleAuthorityRelation;
import cn.org.ferry.sys.service.SysRoleAuthorityRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Generate by code generator
 * 系统角色权限关系表 控制器
 */

@Api(tags = "系统角色权限关系表控制器", position = 700)
@RestController
@RequestMapping("/api")
public class SysRoleAuthorityRelationController {
	@Autowired
	private SysRoleAuthorityRelationService sysRoleAuthorityRelationService;

	/**
	 * 查询
	 */
	@ApiOperation(value = "查询系统角色权限关系表", position = 710)
	@RequestMapping(value = "/sys/role/authority/relation/query", method = RequestMethod.GET)
	public List<SysRoleAuthorityRelation> query(SysRoleAuthorityRelation sysRoleAuthorityRelation,
								@ApiParam(name = "page", value = "当前页") @RequestParam(defaultValue = "1") int page,
								@ApiParam(name = "pageSize", value = "页面大小") @RequestParam(defaultValue = "10") int pageSize){
		return sysRoleAuthorityRelationService.select(sysRoleAuthorityRelation, page, pageSize);
	}

}
