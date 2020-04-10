package com.dingsheng.decent.service.admin.system;

import com.dingsheng.decent.entity.SysModule;
import com.dingsheng.decent.mapper.SysModuleMapper;
import com.dingsheng.decent.util.old.AdminInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @luzhengxiang
 * @create 2020-04-08 13:22
 **/
@Service
public class SysModuleService {

    @Autowired
    SysModuleMapper sysModuleMapper;

    /**
     * 通过父节点id获取菜单
     * @param parentId 父节点Id
     * @return
     */
    public List<SysModule> getListByParentId(int parentId){
        List<SysModule> moduleList = sysModuleMapper.getModuleByParentId(parentId);
        return moduleList;
    }

    public List<SysModule> getCurrentUserModules(int parentId){
        List<SysModule> moduleList = sysModuleMapper.getCurrentUserPermissionByParentId(parentId, AdminInfo.getPAdmin().getPermission());
        return moduleList;
    }

    /**
     * 通过id获取模块
     * @param id
     * @return
     */
    public SysModule getById(int id){
        return sysModuleMapper.selectByPrimaryKey(id);
    }
}
