package com.nice;

import com.nice.validate.constant.ValidatorConditionType;
import com.nice.validate.entity.LotteryRecord;
import com.nice.validate.validator.AttributeValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@MapperScan("com.nice.mapper*")
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(
                Application.class);
        application.run(args);
        validateEntity();
        testFor();
    }
    /**
     * 验证用 实体类 中 @NotEmpty 注解是否生效
     */
    public static void  validateEntity(){
        LotteryRecord request=new LotteryRecord();
        String name=null;
        String s = null == name ? "" : name;
        String valueOf = String.valueOf(s);
        log.info("String.valueOf  " +valueOf);
        try {
            request.validate(ValidatorConditionType.READ);
        } catch (AttributeValidatorException e) {
            log.info("error: {}", e);
            log.info("Message: {}", e.getMessage());
        }
    }

    /**
     * 测试 两个循环 大数据在外 和 小循环在外效率
     */
    public static void testFor(){
        List<String> max = new ArrayList<>();
        for (int i = 0; i <= 10000000; i++) {
            max.add("Max");
        }
        List<String> mix = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            mix.add("mix");
        }
        long starTime = System.currentTimeMillis();
        for (String ma : max) {
            for (String mi : mix) {
            }
        }

        log.info("外层大循环所耗时间 : " + (System.currentTimeMillis() - starTime));
        starTime = System.currentTimeMillis();
        for (String mi : mix) {
            for (String ma : max) {
            }
        }
        log.info("外层小循环所耗时间 : " + (System.currentTimeMillis() - starTime));
    }

}
