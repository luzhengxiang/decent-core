package com.dingsheng.decent.controller.admin.system;

import com.dingsheng.decent.constans.admin.system.CookieName;
import com.dingsheng.decent.dto.admin.System.DResult;
import com.dingsheng.decent.dto.admin.System.ModuleModel;
import com.dingsheng.decent.dto.admin.System.PagerRequest;
import com.dingsheng.decent.dto.admin.System.PagerResponse;
import com.dingsheng.decent.entity.SysAdmin;
import com.dingsheng.decent.service.admin.system.SysAdminService;
import com.dingsheng.decent.service.admin.system.SysModuleService;
import com.dingsheng.decent.util.old.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "admin/Admin")
public class AdminController {
//    @Autowired
//    AdminBusiness db;
    @Autowired
    SysAdminService sysAdminService;
    @Autowired
    SysModuleService sysModuleService;

    //<editor-fold desc="登陆">

    @PostMapping("/Login")
    @ResponseBody
    public DResult Login(HttpServletRequest request, HttpServletResponse response, String UserName, String Pwd, String Code) {

        DResult result = new DResult();
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                String code = session.getAttribute(CookieName.Admin_Login).toString();
                Verify.If(!code.equals(Code), "验证码不正确");
            }
            result = sysAdminService.login(UserName, Pwd);
            if (result.isSuccess()) {
                session.setAttribute("isLogin", true);
                session.setAttribute("UserName", UserName);
                session.setMaxInactiveInterval(30 * 60);

                session.removeAttribute(CookieName.Admin_Login);
                String sessionID = session.getId();
                Cookie cookie = new Cookie("JSESSIONID", sessionID);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }
        } catch (Exception e) {
            result.SetError(e.getMessage());
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="获取列表">

    @PostMapping("/list")
    @ResponseBody
    public PagerResponse<SysAdmin> AdminList(PagerRequest pager) {
        return sysAdminService.adminList(pager);
//        return db.adminList(pager);
    }
    //</editor-fold>

    //<editor-fold desc="编辑管理员">

    /**
     * 编辑管理员
     *
     * @return
     */
    @PostMapping("/Edit")
    @ResponseBody
    public DResult Edit(SysAdmin input,String Permission) {
        DResult res = sysAdminService.AdminEdit(input,Permission);
        return res;
    }
    //</editor-fold>

    @PostMapping("/updatePwd")
    @ResponseBody
    public DResult updatePwd(String pwd, String newPwd, String reNewPwd) {
        return sysAdminService.updatePwd(pwd, newPwd, reNewPwd);
    }

    //<editor-fold desc="删除">

    @PostMapping("/Del")
    @ResponseBody
    public DResult Del(Integer id) {
        return sysAdminService.Del(id);
    }
    //</editor-fold>

    //<editor-fold desc="获取权限列表">

    @GetMapping("/GetModule")
    @ResponseBody
    public List<ModuleModel> GetModule(Integer id) {
        return sysAdminService.getModule(id);
    }
    //</editor-fold>

    //<editor-fold desc="生成图片验证码">

    /**
     * @description 生成图片验证码
     */
    @RequestMapping(value = "/LoginCode", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void LoginCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //随机码
        String Code = EncryptUtil.getInstance().GetRandNum(4, 1);
        //实例生成验证码对象
        BufferedImage img = CaptchaUtils.getImageCode(Code, 70, 30);
        //将验证码存入session
        session.setAttribute(CookieName.Admin_Login, Code);

        //向页面输出验证码图片
        ImageIO.write(img, "JPEG", response.getOutputStream());
    }
    //</editor-fold>


    /**
     * 文件上传
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/UploadImg2", method = {RequestMethod.GET, RequestMethod.POST})
    public void UploadImg2(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;//上传第一张图
        List<MultipartFile> files1 = multipartRequest.getFiles("upfile");
        String res = new UploadingUnity().DitorFileUpload(files1.get(0), "", 1);
        ResponseUtils.renderJson(response, res);
    }
}
