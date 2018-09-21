package com.nice.web;

import com.nice.validate.constant.ValidatorConditionType;
import com.nice.validate.entity.LotteryRecord;
import com.nice.validate.validator.AttributeValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nice on 2018/9/21.
 */
@RestController
@Scope("prototype")
@RequestMapping("/lotteryrecordcontroller")
@Slf4j
public class LotteryRecordController {
    @RequestMapping(value = "/addLotteryRecord")
    public LotteryRecord  addLotteryRecord(@RequestBody LotteryRecord request) throws AttributeValidatorException {
        try {
            request.validate(ValidatorConditionType.READ);
        } catch (AttributeValidatorException e) {
            log.info("error: {}", e);
            log.info("Message: {}", e.getMessage());
            throw e;
        }
        return request;
    }
    @RequestMapping(value = "/deleteLotteryRecord")
    public LotteryRecord  deleteLotteryRecord(@RequestBody LotteryRecord request) throws AttributeValidatorException {
        try {
            request.validate(ValidatorConditionType.READ);
        } catch (AttributeValidatorException e) {
            log.info("error: {}", e);
            log.info("Message: {}", e.getMessage());
            throw e;
        }
        return request;
    }
}
