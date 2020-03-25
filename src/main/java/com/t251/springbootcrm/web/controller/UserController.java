package com.t251.springbootcrm.web.controller;

import com.t251.springbootcrm.entity.Role;
import com.t251.springbootcrm.entity.User;
import com.t251.springbootcrm.service.IRoleService;
import com.t251.springbootcrm.service.IUserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author world
 */
@Controller
public class UserController  {
    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;
    @RequestMapping(value = "/dologin")
    public String dologin(String usrName, String usrPassword, HttpServletRequest request,Map<String,Object>map,HttpSession session){
       /* User user=userService.login(usrName,usrPassword);
        if (user!=null){
            request.getSession().setAttribute("loginUser",user);
            return "main";
        }else {
            request.setAttribute("message","登陆失败，用户名或密码错误！");
            return "login";
        }*/
        User user=null;
        try{
            if (usrName==null||usrPassword==null){
                throw new Exception("请输入账号和密码");
            }
            //此处不再处理登录，由shiro进行处理
            AuthenticationToken token=new UsernamePasswordToken(usrName,usrPassword);
            SecurityUtils.getSubject().login(token);
            user=(User)SecurityUtils.getSubject().getPrincipal();
            //注意此处session.setAttribute中key的值
            //需要和AuthorizationInterceptor拦截器session的key值一致
        session.setAttribute("loginUser",user);
        }catch (IncorrectCredentialsException i){
        i.printStackTrace();
        map.put("message",i.getMessage());
        return "login";
        }
        catch (Exception e){
        map.put("message",e.getMessage());   //MyshiroRealm中抛出的异常信息
        return "login";
        }
        return "redirect:/main";
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
    session.removeAttribute("loginUser");
    //调用shiro登出方法
        SecurityUtils.getSubject().logout();
    return "login";
    }
   /* @RequestMapping(value = "/user/list")
    public String findUser(String usrName, Long roleId, Model model){
        List<User>list=userService.findAllUsers();
        model.addAttribute("users",list);
        return "user/list";
    }*/
    @RequestMapping(value = "/user/list")
    public String list(Model model, @RequestParam(required = false)String usrName, @RequestParam(required = false,defaultValue = "0")Long roleId,
                       @RequestParam(required = false,defaultValue = "1")Integer pageIndex){
            //springboot 2.2.1  以上已经不能用new Sort了
        // Sort sort=  new Sort(Sort.Direction.ASC,"usrId");
        Sort.Order order=new Sort.Order(Sort.Direction.DESC, "usrId");
        //控制分页的辅助类，可以设置页码(从0开始！！！)、每页的数据条数、排序等
        Pageable pageable= PageRequest.of(pageIndex-1,5,Sort.by(order));
        Page<User>userPager=userService.findUsers(usrName,roleId,pageable);
        model.addAttribute("userPager",userPager);
        model.addAttribute("usrName",usrName);
        model.addAttribute("roleId",roleId);
       // List<Role>roles=roleService.findAllRoles();
        //model.addAttribute("roles",roles);
        return "user/list";
    }
    @RequestMapping("/user/add")
    public String userAdd(Model model){
        List<Role>roles=roleService.findAllRoles();
        model.addAttribute("roles",roles);
        return  "user/add";
    }

    private String  url;


    @RequestMapping("/user/save")
    public String addSave(User user,Model model,@RequestParam(value="a_imagePicPath",required= false) MultipartFile file,HttpServletRequest request){
        /*String imagePicPath =  null;
        String imageLocPath =  null;
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("static"+java.io.File.separator+"uploadfile");
            //logger.info("uploadFile path: " + path);
            //原文件名
            String oldfileName = attach.getOriginalFilename();
            //原文件后缀
            String prefix = FilenameUtils.getExtension(oldfileName);
            int filesize = 500000;
            //上传大小不得超过 50k
            if(attach.getSize() > filesize){
                request.setAttribute("fileUploadError", "000");
                return "user/add";
                //上传图片格式
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                    ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")|| prefix.equalsIgnoreCase("gif")){
                String fileName = user.getUsrName() + ".jpg";

                //File targetFile = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploadfile",fileName);
                File targetFile = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploadfile",fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError", "123");
                    return "user/add";
                }

                imagePicPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploadfile\\"+fileName;
                imageLocPath = path+ File.separator+fileName;
            }else{
                request.setAttribute("fileUploadError", "456");
                return "user/add";
            }
        }
        user.setImagePicPath(imagePicPath);
        user.setImageLocPath(imageLocPath);*/



        System.out.print("上传文件==="+"\n");
        //判断文件是否为空
        if (file.isEmpty()) {
            return "上传文件不可为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //System.out.print("上传的文件名为: "+fileName+"\n");

        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+fileName+"\n");

        //加个时间戳，尽量避免文件名称重复
        String path = "E:/fileUpload/" +fileName;
        //String path = "E:/fileUpload/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        //文件绝对路径
        System.out.print("保存文件绝对路径"+path+"\n");
        //创建文件路径
        File dest = new File(path);
        //判断文件是否已经存在
        if (dest.exists()) {
            return "文件已经存在";
        }
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //上传文件
            file.transferTo(dest); //保存文件
            System.out.print("保存文件路径"+path+"\n");
            //url="http://你自己的域名/项目名/images/"+fileName;//正式项目
            url="http://localhost:8080/images/"+fileName;//本地运行项目 

        } catch (IOException e) {
            return "上传失败";
        }

        user.setImagePicPath(path);
        user.setImageLocPath(url);
        userService.saveUser(user);
        return  "redirect:/user/list";
        //return  "user/list";
    }
    @RequestMapping("/user/edit")
    public String userEdit(Model model,Long usrId){
        User user=userService.getUser(usrId);
        List<Role>roles=roleService.findAllRoles();
        model.addAttribute("user",user);
        model.addAttribute("roles",roles);
        return  "user/edit";
    }
    @RequestMapping("/user/editSave")
    public String editSave(User user){
        userService.saveUser(user);
        return  "redirect:/user/list";
    }
    @RequestMapping("/user/del")
    @ResponseBody
    public Map deleteUser(Long usrId){
        userService.deleteUser(usrId);
        Map map=new HashMap();
        map.put("flag","true");
        return map;
    }
    @RequestMapping("/role/json")
    @ResponseBody
    public List<Role>findAllRoles(){
        List<Role>list=roleService.findAllRoles();
       return list;
        //return null;
    }
}
