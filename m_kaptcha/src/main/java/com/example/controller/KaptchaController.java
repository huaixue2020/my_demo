package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;

/**
 * kaptcha控制器
 *
 * @author 1
 * @date 2022/08/25
 */
@RestController
@RequestMapping("/kaptcha")
public class KaptchaController {

    @Autowired
    private Kaptcha kaptcha;

    /**
     * 生成验证码
     */
    @GetMapping("/render")
    public void render() {
        kaptcha.render();
    }

    /**
     * 校验验证码
     *
     * @param code 验证吗
     * @return {@link Object}
     */
    @PostMapping("/valid")
    public Object validDefaultTime(@RequestParam String code) {
        // default timeout 900 seconds
        boolean validate;
        try {
            // 没有过期时间
            // validate = kaptcha.validate(code);

            // 3分钟过期
            validate = kaptcha.validate(code, 180);
        } catch (Exception e) {
            if (e instanceof KaptchaIncorrectException) {
                return "验证码不正确";
            } else if (e instanceof KaptchaNotFoundException) {
                return "验证码未找到";
            } else if (e instanceof KaptchaTimeoutException) {
                return "验证码过期";
            } else if (e instanceof NullPointerException) {
                return "验证码用过后已销毁";
            } else {
                return "验证码渲染失败";
            }
        }
        return validate;
    }

}