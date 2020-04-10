package com.dingsheng.decent.service.admin.system;

import com.dingsheng.decent.dto.admin.System.*;
import com.dingsheng.decent.entity.SysAdmin;
import com.dingsheng.decent.entity.SysModule;
import com.dingsheng.decent.mapper.SysAdminMapper;
import com.dingsheng.decent.mapper.SysModuleMapper;
import com.dingsheng.decent.util.db.DatabaseUtil;
import com.dingsheng.decent.util.old.AdminInfo;
import com.dingsheng.decent.util.old.EncryptUtil;
import com.dingsheng.decent.util.old.EnumUtil;
import com.dingsheng.decent.util.old.Verify;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @luzhengxiang
 * @create 2020-04-08 13:44
 **/
@Service
public class SysAdminService {


    @Autowired
    SysAdminMapper sysAdminMapper;
    @Autowired
    SysModuleMapper sysModuleMapper;

    public EncryptUtil util = EncryptUtil.getInstance();

//    public static void main(String[] args) {
//        System.out.println(util.encrypt("1"));
//    }

    /**
     * 登录
     * @param UserName
     * @param Pwd
     * @return
     */
    public DResult login(String UserName, String Pwd){
        DResult res = new DResult();


        try {
            SysAdmin admin = sysAdminMapper.selectByUserName(UserName);
            Verify.IfNull(admin, "账号不存在");
            Verify.If(!admin.getLoginPwd().equals(util.encrypt(Pwd)), "密码不正确");
            Verify.If(admin.getLocking() == 1, "该账号已被冻结，请联系管理员解冻..");
            AdminInfo.setPadmin(admin);
            res.SetSuccess("登录成功", admin.getId());
        }catch (Exception e){
            res.SetError(e.getMessage());
        }
//        try {
//            Admin admin = queryFactory.selectFrom(qAdmin).where(qAdmin.userName.eq(UserName)).fetchFirst();
//            Verify.IfNull(admin, "账号不存在");
//            Verify.If(!admin.loginPwd.equals(util.encrypt(Pwd)), "密码不正确");
//            Verify.If(admin.locking == 1, "该账号已被冻结，请联系管理员解冻..");
//            AdminInfo.setPadmin(admin);
//            res.SetSuccess("登录成功", admin.id);
//        } catch (Exception ex) {
//            res.SetError(ex.getMessage());
//        }
        return res;
    }


    /**
     * 获取管理员列表
     *
     * @param pager
     * @return
     */
    public PagerResponse<SysAdmin> adminList(PagerRequest pager) {
        PagerResponse<SysAdmin> res = new PagerResponse<>();


        PageInfo<SysAdmin> pageInfo = new PageInfo<>(sysAdminMapper.selectAll());
        res.setRows(pageInfo.getList());
        res.setTotal(pageInfo.getTotal());
//        try {
//            List<SysAdmin> list = queryFactory
//                    .selectFrom(qAdmin)
//                    .orderBy(qAdmin.addTime.desc())
//                    //分页
//                    .offset(pager.getSkip()).limit(pager.rows)
//                    .fetch();
//            Verify.If(list.size() <= 0, "暂无管理员");
//            long total = queryFactory.select(qAdmin.id.count())
//                    .from(qAdmin)
//                    .fetchOne();
//            res.setRows(list);
//            res.setTotal(total);
//        } catch (Exception e) {
//            String sda = e.getMessage();
//        }
        return res;
    }

    //</editor-fold>

    //<editor-fold desc="编辑管理员">

    /**
     * 编辑管理员
     *
     * @param inputAdmin
     * @return
     */
    public DResult AdminEdit(SysAdmin inputAdmin,String Permission) {
        DResult res = new DResult();
        try {
            Verify.IfNull(inputAdmin, "参数错误");
            Verify.IfNull(inputAdmin.getUserName(), "请输入用户名");
            Verify.IfNull(inputAdmin.getNickName(), "请输入昵称");
            SysAdmin admin = new SysAdmin();

            if (inputAdmin.getId() != null && inputAdmin.getId() > 0) {
                admin = sysAdminMapper.selectByPrimaryKey(admin.getId());
                Verify.IfNull(admin, "修改失败,管理员不存在");

                if (!StringUtils.isBlank(admin.getUserName()) && admin.getUserName().equals(inputAdmin.getUserName())) {
                    Verify.If(true, "修改失败,该账号已存在");
                }

                sysAdminMapper.updateByPrimaryKeySelective(admin);
                AdminInfo.setPadmin(inputAdmin);
                res.SetSuccess("修改成功");
            } else {
                Verify.IfNull(inputAdmin.getLoginPwd(), "请输入密码");
                SysAdmin existAdmin = sysAdminMapper.selectByPrimaryKey(admin.getId());
//                SysAdmin existAdmin = queryFactory.selectFrom(qAdmin).where(qAdmin.userName.eq(input.userName)).fetchFirst();
                Verify.If(existAdmin != null, "修改失败,该账号已存在");
                admin.setUserName(inputAdmin.getUserName());
                admin.setNickName(inputAdmin.getNickName());
                admin.setId(DatabaseUtil.getDatabasePriykey().intValue());
                admin.setLocking(EnumUtil.EType.No.getValue());
                admin.setLoginPwd(util.encrypt(inputAdmin.getLoginPwd()));
                admin.setAddTime(new Date());
                sysAdminMapper.insert(admin);
                res.SetSuccess("添加成功");
            }


//            if (admin.getId() != null && admin.getId() > 0) {
//                admin = queryFactory.selectFrom(qAdmin).where(qAdmin.id.eq(input.id)).fetchFirst();
//                Verify.IfNull(admin, "修改失败,管理员不存在");
//                if (!StringUtils.isBlank(input.userName) && !input.userName.equals(admin.userName)) {
//                    Admin existAdmin = queryFactory.selectFrom(qAdmin).where(qAdmin.userName.eq(input.userName)).fetchFirst();
//                    Verify.If(existAdmin != null, "修改失败,该账号已存在");
//                }
//                //<editor-fold desc="修改数据">
//                queryFactory.update(qAdmin)
//                        .set(qAdmin.userName, input.userName)
//                        .set(qAdmin.nickName, input.nickName)
//                        .set(qAdmin.permission, Permission)
//                        .where(qAdmin.id.eq(input.id))
//                        .execute();
//                AdminInfo.setPadmin(input);
//                //</editor-fold>
//                res.SetSuccess("修改成功");
//            } else {
//                Verify.IfNull(input.loginPwd, "请输入密码");
//                Admin existAdmin = queryFactory.selectFrom(qAdmin).where(qAdmin.userName.eq(input.userName)).fetchFirst();
//                Verify.If(existAdmin != null, "修改失败,该账号已存在");
//
//                admin.userName = input.userName;
//                admin.nickName = input.nickName;
//                admin.permission = Permission;
//                admin.locking = EnumUtil.EType.No.getValue();
//                admin.loginPwd = util.encrypt(input.loginPwd);
//                admin.addTime = new Date(new Date().getTime());
//                adminJpa.save(admin);
//                res.SetSuccess("添加成功");
//            }

        } catch (Exception e) {
            res.SetError(e.getMessage());
        }

        return res;
    }
    //</editor-fold>

    //<editor-fold desc="修改密码">

    /**
     * 修改密码
     *
     * @param pwd      旧密码
     * @param newPwd   新密码
     * @param reNewPwd 确认密码
     * @return
     */
    public DResult updatePwd(String pwd, String newPwd, String reNewPwd) {
        DResult res = new DResult();
        try {
            SysAdmin u = AdminInfo.getPAdmin();
            if (u == null) throw new Exception("登录失效，请重新登录");

            pwd = util.encrypt(pwd);
            newPwd = util.encrypt(newPwd);
            reNewPwd = util.encrypt(reNewPwd);

            if (!u.getLoginPwd().equals(pwd)) throw new Exception("旧密码不正确..");
            if (!newPwd.equals(reNewPwd)) throw new Exception("两次密码输入不一致");

            u.setLoginPwd(newPwd);
            sysAdminMapper.updatePwd(pwd,u.getId());
            res.SetSuccess("修改成功", null);
            AdminInfo.setPadmin(null);
        } catch (Exception ex) {
            res.SetError(ex.getMessage());
        }
        return res;
    }


    /**
     * 获取权限列表
     *
     * @param id
     * @return
     */
    public List<ModuleModel> getModule(Integer id) {
        List<ModuleModel> res = new ArrayList<>();
        try {
            SysAdmin admin = null;
            if (id != 0) {
                admin = sysAdminMapper.selectByPrimaryKey(id);
            }

            ModuleModel vModule = new ModuleModel();
            vModule.id = 0;
            vModule.text = "管理权限";
            vModule.children = new ArrayList<>();
            List<SysModule> module = sysModuleMapper.selectAll();
            List<SysModule> m1 = module.stream().filter((SysModule m) -> m.getParentID().equals(0)).collect(Collectors.toList());
            for (SysModule item : m1) {
                ModuleParentModel view1 = new ModuleParentModel();
                view1.id = item.getId();
                view1.text = item.getName();
                view1.children = new ArrayList<>();
                List<SysModule> m2 = module.stream().filter((SysModule m) -> m.getParentID().equals(item.getId())).collect(Collectors.toList());
                if (m2.size() <= 0)
                    continue;
                for (SysModule item2 : m2) {
                    ModuleListModel view2 = new ModuleListModel();
                    view2.id = item2.getId();
                    view2.text = item2.getName();
                    view2.checked = false;
                    if (admin != null && admin.getPermission() != null) {
                        view2.checked = Arrays.asList(admin.getPermission().split(",")).contains(item2.getId().toString());
                    }
                    view1.children.add(view2);
                }
                vModule.children.add(view1);
            }
            res.add(vModule);
        } catch (Exception ex) {

        }

//        try {
//            Admin admin = null;
//            if (ID != 0) {
//                admin = queryFactory.selectFrom(qAdmin).where(qAdmin.id.eq(ID)).fetchFirst();
//            }
//
//            ModuleModel vModule = new ModuleModel();
//            vModule.id = 0;
//            vModule.text = "管理权限";
//            vModule.children = new ArrayList<>();
//            List<Module> module = queryFactory.selectFrom(qModule).fetch();
//            List<Module> m1 = module.stream().filter((Module m) -> m.ParentID.equals(0)).collect(Collectors.toList());
//            for (Module item : m1) {
//                ModuleParentModel view1 = new ModuleParentModel();
//                view1.id = item.ID;
//                view1.text = item.Name;
//                view1.children = new ArrayList<>();
//                List<Module> m2 = module.stream().filter((Module m) -> m.ParentID.equals(item.ID)).collect(Collectors.toList());
//                if (m2.size() <= 0)
//                    continue;
//                for (Module item2 : m2) {
//                    ModuleListModel view2 = new ModuleListModel();
//                    view2.id = item2.ID;
//                    view2.text = item2.Name;
//                    view2.checked = false;
//                    if (admin != null && admin.permission != null) {
//                        view2.checked = Arrays.asList(admin.permission.split(",")).contains(item2.ID.toString());
//                    }
//                    view1.children.add(view2);
//                }
//                vModule.children.add(view1);
//            }
//            res.add(vModule);
//        } catch (Exception ex) {
//
//        }
        return res;
    }
    //</editor-fold>

    //<editor-fold desc="删除">

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public DResult Del(Integer id) {
        DResult res = new DResult();
        try {
            SysAdmin admin = sysAdminMapper.selectByPrimaryKey(id);
            Verify.IfNull(admin, "账号不存在");
            sysAdminMapper.deleteByPrimaryKey(id);
            res.SetSuccess("删除成功");
        } catch (Exception e) {
            res.SetError(e.getMessage());
        }


//        try {
//            SysAdmin admin = queryFactory.selectFrom(qAdmin).where(qAdmin.id.eq(ID)).fetchFirst();
//            Verify.IfNull(admin, "账号不存在");
//            adminJpa.delete(admin);
//            res.SetSuccess("删除成功");
//        } catch (Exception e) {
//            res.SetError(e.getMessage());
//        }
        return res;
    }
    //</editor-fold>

    //<editor-fold desc="退出登录">

    /**
     * 退出登录
     *
     * @return
     */
    public DResult OutLogin(HttpServletRequest request) {
        HttpSession sessoin = request.getSession();
        sessoin.setAttribute("isLogin", false);
        sessoin.setAttribute("userName", "");
        AdminInfo.setPadmin(new SysAdmin());
        return new DResult();
    }
    //</editor-fold>

    /**
     *
     * @param id
     * @return
     */
    public SysAdmin selectById(Integer id){
        return sysAdminMapper.selectByPrimaryKey(id);
    }


}
