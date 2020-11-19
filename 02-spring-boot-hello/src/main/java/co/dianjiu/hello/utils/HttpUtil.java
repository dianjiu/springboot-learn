package co.dianjiu.hello.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String  postRequest(String body, String urls) {
        StringBuilder result = new StringBuilder();

        HttpURLConnection conn = null;
        BufferedReader in = null;
        BufferedOutputStream o = null;
        Reader reader = null;
        try {
            conn = (HttpURLConnection) new URL(urls).openConnection();
            conn.setDoInput(true); // 设置可输入
            conn.setDoOutput(true); // 设置该连接是可以输出的
            conn.setUseCaches(true);
            conn.setRequestMethod("POST"); // 设置请求方式
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setConnectTimeout(3000);
            o = new BufferedOutputStream(conn.getOutputStream());
            o.write(body.getBytes("UTF-8"));
            o.flush();
            reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
            in = new BufferedReader(reader);

            String line = null;
            while ((line = in.readLine()) != null) { // 读取数据
                result.append(line);
            }
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != o){
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != conn){
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

}
