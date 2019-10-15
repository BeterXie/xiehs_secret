package com.secret.bussiness.base;


import com.secret.bussiness.util.JSONUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiehs
 * @package com.secret.bussiness.base
 * @date 2019/9/19  22:38
 */
public class BaseController  {

        protected final Logger logger = LoggerFactory.getLogger(getClass());


        protected void renderHTML(HttpServletResponse res, String text) throws IOException {
                res.setCharacterEncoding("UTF-8");
                res.setHeader("Content-type","text/html");
                this.logger.info(text);
                res.getWriter().print(text);
        }


        /**
         * 打出json结果
         *
         * @param response http response
         * @param data json结果
         * @throws IOException 异常信息
         */
        protected void renderJson(HttpServletResponse response , Object data) throws IOException {
                try {
                        String jsonResponse = "";
                        if(data instanceof String){
                                jsonResponse = (String)data;
                        } else {
                                jsonResponse = JSONUtil.toJSONString(data);
                        }
                        logger.info("Json Result:" + jsonResponse);
                        response.setContentType("application/json;charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().print(jsonResponse);

                }
                catch (IOException ex) {
                        logger.error(ex.getMessage(), ex);
                        throw ex;
                }
        }

        protected JSONObject getRequestBody(HttpServletRequest request) {
                try {
                        byte[] data = IOUtils.toByteArray(request.getInputStream());

                        String reqData = new String(data, "UTF-8");
                        logger.debug("reqData >>>>>>>>>>>>>>>" + reqData);
                        JSONObject json = JSONObject.fromObject(reqData);
                        return json;
                } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                }
        }
}
