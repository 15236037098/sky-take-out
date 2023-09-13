package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    public static final String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatProperties weChatProperties;
    //微信登录

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String code = userLoginDTO.getCode();
        String openid = getOpenid(code);
        if (openid ==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //很具openid查询用户信息
        User user=userMapper.getByOpenid(openid);
        if (user ==null){
            user=new User();
            user.setOpenid(openid);
            user.setCreateTime(LocalDateTime.now());
            userMapper.insert(user);
        }
        return user;
    }


    private String getOpenid(String code){
        //请求参数封装
        Map map = new HashMap();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");

        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        log.info("微信登录返回结果：{}",json);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        log.info("微信用户的openid为：{}", openid);
        return openid;
    }
}
