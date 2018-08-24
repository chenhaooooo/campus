
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class crawlSouth {
    //static String url = "http://letki.free.ngrok.cc/";
        static String url="http://172.18.2.42:8000/";
    public static void main(String[] args) throws IOException {
        CloseableHttpResponse response = get(url, null);
        String cookie = response.getHeaders(
                "Set-Cookie"
        )[0].getValue();
        Pattern pattern = Pattern.compile("ASP.NET_SessionId=.*?;");
        Matcher m = pattern.matcher(cookie);
        if (m.find()) {
            cookie = m.group(0);
        }
        String content=getContent(response);
        Document doc = Jsoup.parse(content);
        String __EVENTTARGET =doc.select("#__EVENTTARGET").val().trim();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        String __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        String Radio1 = "1";
        String ScriptManager1 = "UpdatePanel1|Radio1$1";
        // 构建消息实体
        HashMap<String, String> map = new HashMap();
        map.put("__EVENTTARGET", __EVENTTARGET);
        map.put("__EVENTARGUMENT", __EVENTARGUMENT);
        map.put("__LASTFOCUS", __LASTFOCUS);
        map.put("__VIEWSTATE", __VIEWSTATE);
        map.put("__EVENTVALIDATION", __EVENTVALIDATION);
        map.put("Radio1", Radio1);
        map.put("ScriptManager1", ScriptManager1);
        HashMap header = new HashMap();
        header.put("Cookie", cookie);
        response = post(url, header, map);
        content=getContent(response);
        //#################################################宿舍选择
        String txtjz2="001001006";
        String txtname2="001001001001001";
        String txtpwd2="";
        //验证码
        String txtyzm2="";
        doc = Jsoup.parse(content);
        __EVENTTARGET =doc.select("#__EVENTTARGET").val().trim();
        __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
       __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
         __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        Radio1 = "1";
        ScriptManager1 = "UpdatePanel1|Radio1$1";
        // 构建消息实体
        map = new HashMap();

        map.put("__EVENTTARGET", __EVENTTARGET);
        map.put("__EVENTARGUMENT", __EVENTARGUMENT);
        map.put("__LASTFOCUS", __LASTFOCUS);
        map.put("__VIEWSTATE", __VIEWSTATE);
        map.put("__EVENTVALIDATION", __EVENTVALIDATION);
        map.put("Radio1", Radio1);
        map.put("ScriptManager1", ScriptManager1);
        map.put("txtjz2", txtjz2);
        map.put("txtname2",txtname2);
        map.put("txtyzm2",txtyzm2);
        header = new HashMap();
        header.put("Cookie", cookie);
        response = post(url, header, map);
        content=getContent(response);
        //##################################################################
        //登录
        doc = Jsoup.parse(content);
        __EVENTTARGET =doc.select("#__EVENTTARGET").val().trim();
        __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
        __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
        __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        Radio1 = "1";
        // 构建消息实体
        map = new HashMap();
        txtjz2="001001006";//宿舍楼
        txtname2="001001006001005";//宿舍层
        txtpwd2="";
        //验证码
        txtyzm2="2367";
        map.put("Radio1", Radio1);
        map.put("txtjz2", txtjz2);
        map.put("txtname2",txtname2);
        map.put("txtpwd2",txtpwd2);
        map.put("txtyzm2",txtyzm2);
        map.put("Button1","");
        map.put("__EVENTTARGET", __EVENTTARGET);
        map.put("__EVENTARGUMENT", __EVENTARGUMENT);
        map.put("__LASTFOCUS", __LASTFOCUS);
        map.put("__VIEWSTATE", __VIEWSTATE);
        map.put("__EVENTVALIDATION", __EVENTVALIDATION);
        map.put("hidtime","2018-08-19 17:46:01");

        header = new HashMap();
        header.put("Cookie", cookie);
        response = post(url, header, map);
        content=getContent(response);
        String temp="";
        for(int i=0;i<response.getHeaders("Set-Cookie").length;i++)
        {
            temp+=response.getHeaders("Set-Cookie")[i].getValue();
        }
        pattern = Pattern.compile("loginschool=.*?;");
        m = pattern.matcher(temp);
        if(m.find()) {
            cookie+= m.group(0);
        }
        pattern = Pattern.compile(".ASPXAUTH=.*?;");
        m = pattern.matcher(temp);
        if(m.find()) {
            cookie+= m.group(0);
        }
        System.out.println(cookie);
        //##################################################################
        //####################进入详细南苑页面################################
        header=new HashMap();
        header.put("Cookie",cookie);
        response=get(url+"PowerMonitoring/ssjkSSSJCX2.aspx?id=73",header);
        content=getContent(response);
        System.out.println(content);

    }

    public static void obtainUseHistoryData(HashMap headers,String build)
    {

    }

    public static void obtainUseNowData(HashMap headers)
    {

    }

    public static CloseableHttpResponse get(String url, HashMap<String, String> headers) {
        HttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            if(headers!=null)
            {
                Set<Map.Entry<String, String>> entrySet = headers.entrySet();
                Iterator<Map.Entry<String, String>> it=entrySet.iterator();
                while(it.hasNext())
                {
                    Map.Entry entry=it.next();
                    httpGet.setHeader(entry.getKey().toString(),entry.getValue().toString());
                }
            }

            response = (CloseableHttpResponse) client.execute(httpGet);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public static CloseableHttpResponse post(String url, HashMap<String, String> headers, HashMap<String, String> params) {
        HttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
            }
        }

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            Iterator<Map.Entry<String, String>> it=entrySet.iterator();
            while(it.hasNext())
            {
                Map.Entry entry=it.next();
                httpPost.setHeader(entry.getKey().toString(),entry.getValue().toString());
            }
            response = (CloseableHttpResponse) client.execute(httpPost);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public static String getContent(CloseableHttpResponse response) {
        //4.获取响应的实体内容，就是我们所要抓取得网页内容
        HttpEntity entity = response.getEntity();

        //5.将其打印到控制台上面
        //方法一：使用EntityUtils
        String content = "";
        if (entity != null) {
            try {
                content += EntityUtils.toString(entity, "utf-8");
                //释放实体
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

}
