package com.dingsheng.decent.controller.admin.system;

import com.dingsheng.decent.entity.SysModule;
import com.dingsheng.decent.service.admin.system.SysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "admin/home")
public class HomeController {
//    @Autowired
//    ModuleBusiness db;
    @Autowired
    SysModuleService moduleService;

    //<editor-fold desc="获取功能列表">

    /**
     * 获取功能列表
     *
     * @return
     */
    @GetMapping("GetModule")
    @ResponseBody
    public List<SysModule> GetModule(@RequestParam(value = "parentId",defaultValue = "0",required = false)Integer parentID) {
//     return db.getModule(ParentID);
        return moduleService.getCurrentUserModules(parentID);
    }

    @GetMapping("GetModules")
    @ResponseBody
    public List<SysModule> GetModules(@RequestParam(value = "parentId",defaultValue = "0",required = false)Integer parentID) {
//     return db.getModule(ParentID);
        return moduleService.getListByParentId(parentID);
    }

    //</editor-fold>


//    public static void main(String[] args) {
//        System.out.println(EncryptUtil.decrypt("cc03e747a6afbbcbf8be7668acfebee5"));
//    }
}
