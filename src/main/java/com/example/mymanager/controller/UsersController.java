package com.example.mymanager.controller;

import com.alibaba.fastjson.JSON;
import com.example.mymanager.bean.*;
import com.example.mymanager.service.*;
import com.example.mymanager.util.CapacityUtil;
import com.example.mymanager.util.ExcelConstant;
import com.example.mymanager.util.ExcelUtil;
import com.example.mymanager.util.ExcelUtil3;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CapacityService capacityService;
    @Autowired
    private UsersRoleService usersRoleService;
    @Autowired
    private LogService logService;
    @GetMapping("/list")
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.addObject("usersList",usersService.getUsersAll());
        modelAndView.addObject("roleList",roleService.selectRoleAll());
        modelAndView.setViewName("usersIndex");
        return modelAndView;
    }
    @RequestMapping("/listajax")
    @ResponseBody
    public Object listajax(Page page,@RequestParam("limit") int limit){
        System.out.println("backContent========================"+limit);
        page.setRows(limit);
        System.out.println("page:"+page.toString());
        List<Users>contentList=usersService.selectPageUsers(page);
        int totals=usersService.selectCountUsers(page);
        page.setTotalRecord(totals);
        return new ResultMap<List<Users>>("",contentList,0,totals);
    }
    @GetMapping("/toLogin")
    public ModelAndView toLogin(ModelAndView modelAndView){
        modelAndView.setViewName("login");
        return  modelAndView;
    }
    @ResponseBody
    @PostMapping("/login")
    public Object login(String username, String password, HttpSession session){
        Map<String,Object> returnData=usersService.selectUsers(username,password);
        if((Integer)returnData.get("code")==1){
            Users users=(Users)returnData.get("data");
            session.setAttribute("myusers",users);
            logService.insertLog(new Log(null,"登录",users.getId(),new Timestamp(System.currentTimeMillis())));
            Set<Integer> myCapacity=new HashSet<>();
            if(users!=null&&users.getUsersRoleList()!=null){
                for (UsersRole usersRole:users.getUsersRoleList()) {
                    for (RoleCapacity roleCapacity:usersRole.getRole().getRoleCapacityList()){
                        myCapacity.add(roleCapacity.getCapacity().getId());
                    }
                }
            }
            CapacityUtil.init(capacityService,session);
            session.setAttribute("myCapacity",myCapacity);
        }
        return returnData;
    }
    @RequestMapping("/exit")
    public ModelAndView exit(ModelAndView modelAndView,HttpSession session){
        session.removeAttribute("myusers");
        modelAndView.setViewName("redirect:/users/toLogin");
        return  modelAndView;
    }
    @ResponseBody
    @PostMapping("/delete")
    public Object delete(Integer id,HttpSession session){
        if(!CapacityUtil.checkCapacity(capacityService,session,"删除用户")){
            logService.insertLog(new Log(capacityService.selectCapacityByName("删除用户").getId(),"删除用户时"+id+"没有权限",
                    ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
            return 0;
        }
        logService.insertLog(new Log(capacityService.selectCapacityByName("删除用户").getId(),"删除用户"+id,
                ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
        return usersService.deleteUsers(id);
    }

    @ResponseBody
    @PostMapping("/add")
    public Object add(Users users,@RequestParam(value = "roleArray[]")Integer [] roleArray,HttpSession session){
        Map<String,Object> returnData=new HashMap<>();
        if(!CapacityUtil.checkCapacity(capacityService,session,"添加用户")){
            returnData.put("code",-8);
            returnData.put("message","没有权限");
            logService.insertLog(new Log(capacityService.selectCapacityByName("添加用户").getId(),"添加用户时"+users.getName()+"没有权限",
                    ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
        }else{
            users.setState(1);
            users.setCode("USERS"+new Random().nextInt(10000));
            if(usersService.insertUsers(users)==1){
                if(roleArray!=null&&roleArray.length>0){
                    int i=0;
                    UsersRole usersRole=new UsersRole();
                    usersRole.setUsersId(users.getId());
                    for (Integer roleId:roleArray) {
                        usersRole.setRoleId(roleId);
                        if(usersRoleService.insertUsersRole(usersRole)!=1){
                            i++;
                        }
                    }
                    if(i==0){
                        returnData.put("code",1);
                        returnData.put("message","添加完成");
                        logService.insertLog(new Log(capacityService.selectCapacityByName("添加用户").getId(),"添加用户"+users.getName()+"完成",
                                ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
                    }else{
                        logService.insertLog(new Log(capacityService.selectCapacityByName("添加用户").getId(),"添加用户"+users.getName()+"时发生意外错误",
                                ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
                        returnData.put("code",-2);
                        returnData.put("message","发生意外错误");
                    }
                }
            }else{
                logService.insertLog(new Log(capacityService.selectCapacityByName("添加用户").getId(),"添加用户"+users.getName()+"时添加失败",
                        ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
                returnData.put("code",-1);
                returnData.put("message","添加失败");
            }
        }
        return returnData;
    }
    @ResponseBody
    @RequestMapping("/addList")
    public Object addList(MultipartFile  file,HttpSession session){
        Map<String,Object> returnData=new HashMap<>();
        if(!CapacityUtil.checkCapacity(capacityService,session,"添加用户")){
            returnData.put("code",-8);
            returnData.put("message","没有权限");
            logService.insertLog(new Log(capacityService.selectCapacityByName("添加用户").getId(),"批量添加用户时没有权限",
                    ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
            return returnData;
        }
        String filePath="";
        OutputStream os = null;
        InputStream inputStream = null;
        String fileName = null;
        try {
            inputStream = file.getInputStream();
            fileName = file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = "F:\\test\\";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            filePath=tempFile.getPath() + File.separator + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        returnData=ExcelUtil.getDataFromExcel2(filePath);
        if(Integer.parseInt(returnData.get("code").toString())==1){
            List<Users> usersList=(List<Users>)returnData.get("data");
            Integer flag=0;
            for (Users users : usersList) {
                users.setCode("USERS"+new Random().nextInt(10000));
                if(usersService.insertUsers(users)==-1){
                    flag=-1;
                }
            }
            if(flag==-1){
                returnData.put("code",-7);
                returnData.put("message","某些账号已存在导致部分未导入");
                logService.insertLog(new Log(capacityService.selectCapacityByName("添加用户").getId(),"批量添加时某些账号已存在导致部分未导入",
                        ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
            }
        }

        return JSON.toJSONString(returnData);
    }
    @RequestMapping("/writeExcel")
    public void writeExcel(){
        int rowIndex = 0;
        List<Users> usersList = usersService.getUsersAll();
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("ID");
        titles.add("userName");
        titles.add("password");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for (Users users : usersList) {
            List<Object> row = new ArrayList();
            row.add(users.getUsername());
            row.add(users.getPassword());
            row.add(users.getName());
            rows.add(row);
        }
        data.setRows(rows);
        try{
            rowIndex = ExcelUtil3.generateExcel(data, ExcelConstant.FILE_PATH + ExcelConstant.FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ;
    }
    @RequestMapping(value = "UserExcelDownloads", method = RequestMethod.GET)
    public void UserExcelDownloads(HttpServletResponse response,@RequestParam("usersIdList")Integer [] usersIdList) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出用户列表");
        List<Users> usersList =new ArrayList<>();
        for (Integer usersId : usersIdList) {
            usersList.add(usersService.selectUsersById(usersId));
        }
        String fileName = "userinf"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "账号", "密码", "姓名"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (Users users : usersList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(users.getUsername());
            row1.createCell(1).setCellValue(users.getPassword());
            row1.createCell(2).setCellValue(users.getName());
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @ResponseBody
    @RequestMapping("/updateIcon")
    public Object updateIcon(MultipartFile  file,HttpSession session){
        Map<String,Object> returnData=new HashMap<>();
        String filename = file.getOriginalFilename();
        InputStream is = null;
        try {
            //读取文件流
            is = file.getInputStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(is);
            Users users=usersService.selectUsersById(((Users)session.getAttribute("myusers")).getId());
            if(users==null){
                returnData.put("code","-1");
                returnData.put("message","未登录");
                return returnData;
            }
            users.setIconName(filename);
            users.setIconValue(bytes);
            //保存blob字段
            usersService.updateUsers(users);
            users=usersService.selectUsersById(users.getId());
            session.setAttribute("myusers",users);
            returnData.put("code","1");
            returnData.put("message","成功!");
            return returnData;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        returnData.put("code","-2");
        returnData.put("message","失败");
        return returnData;
    }
    @RequestMapping("/showIcon")
    public void showIcon(HttpServletRequest request,
                         HttpServletResponse response,HttpSession session){
        Users users=(Users)session.getAttribute("myusers");
        byte [] imageData=users.getIconValue();
        try{
            response.getOutputStream().write(imageData, 0, imageData.length);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }catch(Exception e){

        }
    }
    @RequestMapping("/update")
    @ResponseBody
    public Object update(Users users){
        Map<String,Object> returnData=new HashMap<>();
        usersService.updateUsers(users);
        returnData.put("code",1);
        returnData.put("message","修改成功!");
        return returnData;
    }


}
