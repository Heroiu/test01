package com.example.mymanager;

import com.example.mymanager.bean.Capacity;
import com.example.mymanager.bean.Users;
import com.example.mymanager.service.CapacityService;
import com.example.mymanager.service.UsersService;
import com.example.mymanager.util.ExcelUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MymanagerApplicationTests {

    @Autowired
    private UsersService usersService;
    @Autowired
    private CapacityService capacityService;
    @Test
    public void contextLoads() {
        Capacity capacity=new Capacity();
        capacity.setState(1);
        capacity.setDetail("22");
        capacity.setName("tianjia1");
        capacityService.insertCapacity(capacity);
    }
    @Test
    public void importExcelText(){
        ExcelUtil.getDataFromExcel2("C:\\Users\\Heroiu\\Desktop\\用户列表.xlsx");
    }
    @Test
    public void text2(){
       List<Capacity> capacityList= capacityService.selectCapacityAll();

       System.out.println("s");
    }

}
