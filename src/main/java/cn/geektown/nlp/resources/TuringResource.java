package cn.geektown.nlp.resources;


import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.turing.util.Aes;
import com.turing.util.Md5;
import com.turing.util.PostServer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/turing")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class TuringResource {
    @GET
    @Timed
    public String test(@QueryParam("userSay") Optional<String> userSay, @QueryParam("userId") Optional<String> userId) {
        //图灵网站上的secret
        String secret = "8d956cd5a7ad246c";
        //图灵网站上的apiKey
        String apiKey = "a8df9d9cb013ac3b75650f48b2479402";
        String cmd = "讲一个笑话吧";//测试用例
        //待加密的json数据
        String data = "{\"key\":\"" + apiKey + "\",\"info\":\"" + userSay.or("good") + "\",\"userid\":\"" + userId.or("geektown") + "\"}";
        //获取时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //生成密钥
        String keyParam = secret + timestamp + apiKey;
        String key = Md5.MD5(keyParam);

        //加密
        Aes mc = new Aes(key);
        data = mc.encrypt(data);

        //封装请求参数
        JSONObject json = new JSONObject();
        json.put("key", apiKey);
        json.put("timestamp", timestamp);
        json.put("data", data);
        //请求图灵api
        String result = PostServer.SendPost(json.toString(), "http://www.tuling123.com/openapi/api");
        return result;
    }
}