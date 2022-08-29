package com.deom.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JWTUtil {

    private static String secret = "secret_guanhao"; // 加盐字段，随意
    private static String subject = "gh_token"; // 主题
    private static String issuer = "service"; // 签名者

    public static String createTokenWithClaim(String str) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // 加盐
            Map<String, Object> map = new HashMap<String, Object>();
            Date nowDate = new Date();
            Date expireDate = getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);// 2小过期
            // Header部分信息
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            String token = JWT.create()
                // 设置头部信息 Header
                .withHeader(map)
                // 设置 载荷 Payload
                .withClaim("content", str) // 自定义存储内容
                .withIssuer(issuer)// 签名是有谁生成 例如 服务器
                .withSubject(subject)// 签名的主题
                .withIssuedAt(nowDate) // 生成签名的时间
                .withExpiresAt(expireDate)// 签名过期的时间
                .sign(algorithm); // 签名
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    public static String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("content");
            return claim.asString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回一定时间后的日期
     * 
     * @param date 开始计时的时间
     * @param year 增加的年
     * @param month 增加的月
     * @param day 增加的日
     * @param hour 增加的小时
     * @param minute 增加的分钟
     * @param second 增加的秒
     * @return
     */
    public static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

    public static void main(String[] args) throws InterruptedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", 10);
        jsonObject.put("name", "张三");
        jsonObject.put("age", 25);
        String tokenWithClaim = createTokenWithClaim(jsonObject.toJSONString());
        System.out.println(tokenWithClaim);
    }

}
