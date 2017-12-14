package mgrcontroller;
import dao.SafeDao;
import entity.SafeEntity;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mgr")
public class TestController {
    @Autowired
    SafeDao safeDao;

    /**
     * XY转换为中文地址
     */
    public static  String Location;
    public static  String EndLocation;
    public static  String X;
    public static  String Y;
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //�����������KEY
    public static final String APPKEY ="a2237fe3087f22d3d501cac7ca29715e";

    //1.��γ�ȵ�ַ����
    public static void getRequest(){
        String result =null;
        String url ="http://apis.juhe.cn/geo/";
        Map params = new HashMap();
        params.put("lng",X);
        params.put("lat",Y);
        params.put("key",APPKEY);
        params.put("type","1");
        params.put("dtype","json");

        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                Location = object.get("result").toString();
                EndLocation=Location.substring(58,Location.indexOf(",",58)-1);
                System.out.println(EndLocation);
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param strUrl
     * @param params
     * @param method
     * @return
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }

            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.writeBytes(urlencode(params));
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //��map��תΪ���������
    public static String urlencode(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * 获取数据接口
     * @return
     */

    @ResponseBody
    @RequestMapping("/one.do")
    public Object One() {
        System.out.println("test=====");
        List<SafeEntity> getData = safeDao.getList();
        System.out.println("====="+getData.size());
        return getData;
    }

    /**
     * 获取中文地址接口
     * @return
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping(value ="/two.do",produces = "application/json;charset=utf-8")
    public String Two() throws UnsupportedEncodingException {
        System.out.println("进入到获取中文地址接口");
        List<SafeEntity> getOne = safeDao.getOne();
        X=getOne.get(0).getLongitude();
        Y=getOne.get(0).getLatitude();
        getRequest();
        System.out.println(EndLocation);
        return EndLocation;
    }
}

