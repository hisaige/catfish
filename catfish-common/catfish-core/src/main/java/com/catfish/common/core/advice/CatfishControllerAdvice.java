package com.catfish.common.core.advice;

import com.hisaige.web.core.entity.domain.AjaxExceptionRes;
import com.hisaige.web.core.entity.domain.R;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.exception.InvalidException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
