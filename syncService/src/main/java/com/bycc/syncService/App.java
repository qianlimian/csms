package com.bycc.syncService;

import com.bycc.syncService.entity.Case;
import com.bycc.syncService.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by wanghaidong on 2017/5/26.
 */
@Component
public class App {
    @Autowired
    private CaseService caseService;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        App app = context.getBean(App.class);


        int count = 0;
//        app.test();
        while (true) {
            app.randomSave();
            Thread.sleep(2000);
            System.out.println(count++ + " records has been saved!!");
        }
    }
    public void test() {
        Case myCase = new Case();
        myCase.setInsertDate(new Date());
        myCase.setNote("note");
        myCase.setAcceptDate(new Date());
        myCase.setAcceptPoliceId(1);
        myCase.setCaseName("murderCase");
        caseService.save(myCase);



        final List<Case> cases = caseService.findAll();

        if (cases.size() == 0) {
            System.out.println("There are no cases.");
            return;
        }

        System.out.println("Persons:");
        for (Case caze : cases) {
            System.out.println(caze);
        }
    }

    /**
     * 向case_table中随机插入一条记录
     */
    private void randomSave() {
        Case myCase = new Case();
        myCase.setAlarmCode("JQ"+randomIntSix());//警情编号
        myCase.setCaseCode("AJ"+randomIntSix());//案件编号
        myCase.setCaseName(randomIntSix()+"号案件");
        myCase.setCaseSummary("案情简介...");//案情简介
        myCase.setSuspect(new ChineseName().getName());
        myCase.setAcceptPoliceId((int)(Math.random()*10));//受理人
        myCase.setMasterPoliceId((int)(Math.random()*10));//主办人
        myCase.setSlavePoliceId((int)(Math.random()*10));//协办人
        myCase.setOccurDate(new Date());
        myCase.setAcceptDate(new Date());
        myCase.setRegisterDate(new Date());
        myCase.setCloseDate(new Date());
        myCase.setNote("备注...");
        myCase.setInsertDate(new Date());
        myCase.setUpdateDate(new Date());
        caseService.save(myCase);

    }

    /**
     * 生成随机的6位整数
     * @return
     */
    private Integer randomIntSix(){
        int[] array = {0,1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for(int i = 0; i < 6; i++)
            result = result * 10 + array[i];
        return result;
    }
}
