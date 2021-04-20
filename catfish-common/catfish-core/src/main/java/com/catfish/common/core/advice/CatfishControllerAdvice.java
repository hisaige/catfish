package com.catfish.common.core.advice;

import com.hisaige.web.core.entity.domain.AjaxExceptionRes;
import com.hisaige.web.core.entity.domain.R;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.exception.InvalidException;
import com.hisaige.web.core.util.RegexUtils;
import com.hisaige.web.core.util.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author chenyj
 * @version 1.0
 * @date 2020/12/29$ - 21:46$
 */
@ControllerAdvice
public class CatfishControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public AjaxExceptionRes handleRepeatLimitException(BadCredentialsException e) {
        return R.fail(new InvalidException(ReturnCodeEnum.LOGIN_FAILED));
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public AjaxExceptionRes handleRepeatLimitException(Exception e) {
        if(e instanceof InvalidException) {
            return R.fail(e);
        } else {
            //如果不是校验异常则返回系统异常提示
            return R.fail(new InvalidException(ReturnCodeEnum.SERVER_EXCEPTION));
        }
    }

    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public AjaxExceptionRes handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        if(!StringUtils.isEmpty(message)){
            String desc = RegexUtils.getMatcher("Duplicate entry (.*) for", message, 0);
            if(!StringUtils.isEmpty(desc)) {
                desc = desc.substring("Duplicate entry".length(), desc.lastIndexOf("for"));
                return R.fail(new InvalidException(ReturnCodeEnum.ILLEGALARGUMENT_EXCEPTION, "数据已存在:" + desc));
            }
        }
        return R.fail(new InvalidException(ReturnCodeEnum.LOGIN_FAILED));
    }
}
