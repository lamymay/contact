package com.arc.contact.util;

/**
 * @author 叶超
 * @since 2020/2/27 15:56
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtils {

//    private static String PATH = "http://172.24.87.47:8088/myhttp/servlet/LoginAction";
//    private static URL url;

    public HttpUtils() {
    }

//    static {
//        try {
//            url = new URL(PATH);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] arsg) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", "lili");
//        params.put("password", "123");
//        String result = sendPostMessage(params, "utf-8");
//        System.out.println("result->" + result);
//    }


    /**
     * @param params 填写的url的参数
     * @param encode 字节编码
     * @return
     */
    public static String postWithBody(URL url,Map<String, String> params, String encode) {
        StringBuffer buffer = new StringBuffer();
        try {//把请求的主体写入正文！！
            if (params != null && !params.isEmpty()) {
                //迭代器
                //Map.Entry 是Map中的一个接口，他的用途是表示一个映射项（里面有Key和Value）
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    buffer.append(entry.getKey()).append("=").
                            append(URLEncoder.encode(entry.getValue(), encode)).
                            append("&");
                }
            }
//            System.out.println(buffer.toString());
            //删除最后一个字符&，多了一个;主体设置完毕
            buffer.deleteCharAt(buffer.length() - 1);
            byte[] mydata = buffer.toString().getBytes();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);//表示从服务器获取数据
            connection.setDoOutput(true);//表示向服务器写数据

            connection.setRequestMethod("POST");
            //是否使用缓存
            connection.setUseCaches(false);
            //表示设置请求体的类型是文本类型
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
            connection.connect();   //连接，不写也可以。。？？有待了解

            //获得输出流，向服务器输出数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(mydata, 0, mydata.length);
            //获得服务器响应的结果和状态码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return changeInputeStream(connection.getInputStream(), encode);

            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 将一个输入流转换成字符串
     *
     * @param inputStream
     * @param encode
     * @return
     */
    private static String changeInputeStream(InputStream inputStream, String encode) {
        //通常叫做内存流，写在内存中的
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    data.toString();

                    outputStream.write(data, 0, len);
                }
                //result是在服务器端设置的doPost函数中的
                result = new String(outputStream.toByteArray(), encode);
                outputStream.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

}
