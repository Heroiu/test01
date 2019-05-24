package com.example.mymanager.util;

import com.example.mymanager.bean.Users;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static Object getRightTypeCell(Cell cell){

        Object object = null;
        switch(cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING :
            {
                object=cell.getStringCellValue();
                break;
            }
            case Cell.CELL_TYPE_NUMERIC :
            {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                object=cell.getNumericCellValue();
                break;
            }

            case Cell.CELL_TYPE_FORMULA :
            {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                object=cell.getNumericCellValue();
                break;
            }

            case Cell.CELL_TYPE_BLANK :
            {
                cell.setCellType(Cell.CELL_TYPE_BLANK);
                object=cell.getStringCellValue();
                break;
            }
        }
        return object;
    }
    public static Map<String,Object> getDataFromExcel2(String filePath)
    {
        Map<String,Object> returnData=new HashMap<>();
        List<Users> usersList=new ArrayList<>();
        List<Map<String,Integer>> list = new ArrayList<Map<String, Integer>>();
        //判断是否为excel类型文件
        if(!filePath.endsWith(".xls")&&!filePath.endsWith(".xlsx"))
        {
            System.out.println("文件不是excel类型");
            returnData.put("message","文件不是excel类型");
            returnData.put("code","-1");
            return returnData;
        }

        FileInputStream fis =null;
        Workbook wookbook = null;
        int flag = 0;

        try
        {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            //2003版本的excel，用.xls结尾
            wookbook = new HSSFWorkbook(fis);//得到工作簿

        }
        catch (Exception ex)
        {
            //ex.printStackTrace();
            try
            {
                //2007版本的excel，用.xlsx结尾

                wookbook = new XSSFWorkbook(filePath);//得到工作簿
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);

        //获得表头
        Row rowHead = sheet.getRow(0);

        //根据不同的data放置不同的表头
        Map<Object,Integer> headMap = new HashMap<Object, Integer>();


        //判断表头是否合格  ------------------------这里看你有多少列
        if(rowHead.getPhysicalNumberOfCells() != 3)
        {
            System.out.println("表头列数与要导入的数据库不对应");
            returnData.put("message","表头列数与要导入的数据库不对应");
            returnData.put("code","-2");
            return returnData;
        }

        try
        {
            //----------------这里根据你的表格有多少列
            while (flag < 3)
            {
                Cell cell = rowHead.getCell(flag);
                if (getRightTypeCell(cell).toString().equals("账号"))
                {
                    headMap.put("username", flag);
                }
                if (getRightTypeCell(cell).toString().equals("密码"))
                {
                    headMap.put("password", flag);
                }
                if (getRightTypeCell(cell).toString().equals("姓名"))
                {
                    headMap.put("name", flag);
                }
                flag++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("表头不合规范，请修改后重新导入");
            returnData.put("message","表头不合规范，请修改后重新导入");
            returnData.put("code","-3");
            return returnData;
        }


        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();



        //要获得属性
        String username = "";
        String password = "";
        String name="";

        if(0 == totalRowNum)
        {
            System.out.println("Excel内没有数据！");
            returnData.put("message","Excel内没有数据！");
            returnData.put("code","-4");
            return returnData;
        }

        Cell cell_1 = null,cell_2 = null,cell_3 = null;

        //获得所有数据
        for(int i = 1 ; i <= totalRowNum ; i++)
        {
            //获得第i行对象
            Row row = sheet.getRow(i);

            try
            {
                cell_1 = row.getCell(headMap.get("username"));
                cell_2 = row.getCell(headMap.get("password"));
                cell_3 = row.getCell(headMap.get("name"));
            } catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("获取单元格错误");
                returnData.put("message","获取单元格错误");
                returnData.put("code","-5");
                return returnData;
            }

            try
            {
                username = getRightTypeCell(cell_1).toString();
                password = getRightTypeCell(cell_2).toString();
                name= getRightTypeCell(cell_3).toString();
            } catch (ClassCastException e)
            {
                e.printStackTrace();
                System.out.println("数据不全");
                returnData.put("message","数据不全");
                returnData.put("code","-6");
                return returnData;
            }
            System.out.println("username:"+username+"\t password:"+password+"\t name:"+name);
            Users users=new Users();
            users.setUsername(username);
            users.setPassword(password);
            users.setName(name);
            users.setState(1);
            usersList.add(users);
        }
        returnData.put("message","导入成功!请刷新页面查看");
        returnData.put("code","1");
        returnData.put("data",usersList);
        return  returnData;
    }
}
