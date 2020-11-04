package com.arc.contact;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.arc.contact.util.ContactDataProvider;
import com.arc.contact.model.AppContact;
import com.arc.contact.util.ContactTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author may
 * todo 需要完成的问题
 * 1 一个人多个电话怎么插入?
 * 2 怎么发送post请求?
 * 3 怎么解析返回值
 * 4 怎么加快处理速度?
 * 5 怎么读取一个人的多个电话号码?
 */
public class MainActivity extends AppCompatActivity {


    //输入框
    private EditText input1;

    Button syncButton = null;
//    Button saveButton = null;
//    Button deleteButton = null;
//    Button updateButton = null;
//    Button getButton = null;
//    Button listAllButton = null;

    //private static final String TAG = "Contact_Test";

    //permission
//    private static final int PERMISSION_CONTACT = 1;


    /**
     * 联系人获取服务
     */
    ContactDataProvider dataProvider = new ContactDataProvider();

    //数据输出展示的地方
    private TextView outputText;

    /**
     * 程序入口
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 第一个测试
        //listAllContactAndShowByTextViewExample1();

        // 第二个测试 查询全部数据
        setContentView(R.layout.activity_contact);


        Toast.makeText(MainActivity.this, "即将init", Toast.LENGTH_SHORT).show();

        //初始化
        initContact();

        input1 = findViewById(R.id.inputCommand);

    }


    /**
     * 第一个测试，渲染一个
     */
    private void listAllContactAndShowInTextView() {
        //加载渲染主图
        setContentView(R.layout.activity_test_textview);
        //测试方法
        //UserService.listAllNumber(this);
        ListView userListView = (ListView) findViewById(R.id.listView1);
        BaseAdapter adapter = new MyContactListViewAdapter(ContactDataProvider.listAllNumber(this), this);
        userListView.setAdapter(adapter);
    }


    //init layout  with method
    private void initContact() {
        // 写几个按钮，对于不同按钮分别绑定crud是事件
        syncButton = findViewById(R.id.syncBtn);
//        saveButton = findViewById(R.id.saveBtn);
//        deleteButton = findViewById(R.id.deleteBtn);
//        updateButton = findViewById(R.id.updateBtn);
//        getButton = findViewById(R.id.getOneBtn);
//        listAllButton = findViewById(R.id.listAllBtn);


        outputText = findViewById(R.id.outputText);

        //todo 打开一个页面要求用户输入
        String name = input1.getText().toString();
        System.out.println(name);
        Toast.makeText(MainActivity.this, "输入文本name\n" + name, Toast.LENGTH_SHORT).show();

//        if("save".equals(name)){
//            saveButton=syncButton;
//        } else if ("del".equals(name)) {
//            deleteButton=syncButton;
//        }else if ("update".equals(name)) {
//            updateButton=syncButton;
//        }else if ("get".equals(name)) {
//            getButton=syncButton;
//        }
//        else if ("list".equals(name)) {
//            listAllButton=syncButton;
//        }

        //绑定事件

        /*
         * sync
         * 0、提示是同步方案
         * 1、远程覆盖本地
         * 2、远程被本地覆盖
         * 3、合并后处理      【暂时使用该方案，且有server完成】
         * 4、
         */
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync();
            }
        });

    }


    //------------------------------


    /**
     *
     */
    private void sync() {
        try {
            // 1 listAll
            // 2 save batch data to local database

            // 1、list
            List<AppContact> contacts = dataProvider.listMockDataList();
            verifyContacts(contacts);

            int insertSuccess = 0;
            for (AppContact contact : contacts) {
                boolean save = dataProvider.save(contact, MainActivity.this);
                if (save) {
                    insertSuccess = insertSuccess + 1;
                }
            }

            Toast.makeText(MainActivity.this, "保存成功" + insertSuccess, Toast.LENGTH_SHORT).show();


//        List<AppContact> contacts = listAll3();
//
//        //                UNION_SET(1, "取并集"),
//        //                ONLY_ACCEPT_SERVER(2, "仅取服务器的数据"),
//        //                ONLY_ACCEPT_CLIENT(3, "仅取客户端数据"),
//        String inputType = input1.getText().toString();
//        Integer synStrategyType = 3;
//        if (inputType != null) {
//            synStrategyType = Integer.valueOf(inputType.trim());
//        }
//
//        System.out.println("String inputType=" + inputType);
//        System.out.println("Integer synStrategyType=" + synStrategyType);
//        System.out.println();
//
//        AppContactRequest requestData = new AppContactRequest("18674192462", contacts, synStrategyType);
//        //2、send data
//        post(requestData);
//
//    }
//
//    public final static String uriForPostListAll = "http://192.168.2.243:8001/zero/contacts/sync";
//
//    private void post(final AppContactRequest requestData) {
//        Map<String, Object> resultMap = new HashMap<>();
//
//        System.out.println("########################################");
//        //开启线程，发送请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                HttpURLConnection connection = null;
//                BufferedReader reader = null;
//                try {
//                    URL url = new URL(uriForPostListAll);
//                    connection = (HttpURLConnection) url.openConnection();
//                    //设置请求方法
//                    connection.setRequestMethod("POST");
//                    //设置连接超时时间（毫秒）
//                    connection.setConnectTimeout(9000);
//                    //设置读取超时时间（毫秒）
//                    connection.setReadTimeout(9000);
//
//                    // 设置输入可用
//                    connection.setDoInput(true);
//                    // 设置输出可用
//                    connection.setDoOutput(true);
//                    // 设置不使用缓存
//                    connection.setUseCaches(false);
//
//                    // 设置HTTP请求属性 - 连接方式：保持
//                    connection.setRequestProperty("Connection", "Keep-Alive");
//                    // 设置HTTP请求属性 - 字符集：UTF-8
//                    connection.setRequestProperty("Charset", "UTF-8");
//                    // 设置HTTP请求属性 - 传输内容的类型 - 简单表单
////                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////                    connection.setRequestProperty("Content-Type", "application/json");
//
//                    //设置参数类型是json格式
//                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//
//
//                    // 发送参数 ，采用字符流发送数据
//                    //                    PrintWriter pw = new PrintWriter(connection.getOutputStream());
//                    //                    pw.write(params);
//                    //                    pw.flush();
//                    //                    pw.close();
//
//                    //                    String body = "[{},{}]";
//
//                    //contacts
//
//
//                    String params = JSON.toJSONString(requestData);
//                    String body = params;
//                    System.out.println("#################################################################");
//                    System.out.println(params);
//                    System.out.println(body);
//                    System.out.println("#################################################################");
//
//                    // 设置HTTP请求属性 - 传输内容的长度
////                    connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
//
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
//                    writer.write(body);
//                    writer.close();
//
//                    //返回输入流
//                    InputStream in = connection.getInputStream();
//
//                    //读取输入流
//                    reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//                    String resultString = result.toString();
//                    show(resultString);
//                    resultMap.put("response", resultString);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (ProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (connection != null) {//关闭连接
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//
//        System.out.println(resultMap.size());
//        System.out.println(resultMap.get("response"));
//        System.out.println("################## END ######################");
//        System.out.println("################## END ######################");

        } catch (Exception e) {
            e.printStackTrace();
            //todo error
        }

    }

    private void verifyContacts(List<AppContact> contacts) {
        if (contacts == null && contacts.size() == 0) {
            Toast.makeText(MainActivity.this, "无可以保存的数据", Toast.LENGTH_SHORT).show();
            throw new RuntimeException("本设备上无可以同步的数据");
        }
    }

    /**
     * 展示
     *
     * @param result
     */
    private void show(final String result) {
        //todo 返回数据的处理
        System.out.println(result);

        //因为在 Android 中不允许在子线程中执行 UI 操作，所以我们通过 runOnUiThread 方法，切换为主线程，然后再更新 UI 元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outputText.setText(result);
            }
        });
    }

    //========================== 第2个测试 START================================




//    private void listAll() {
//        onClick = findViewById(R.id.button);
//        showContact = findViewById(R.id.text);
//        onClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String[] permissList = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
//                addPermissByPermissionList(MainActivity.this, permissList, PERMISSION_CONTACT);
//            }
//        });
//    }

    private void showContacts(String msg) {
        outputText.setText(msg);
        //Log.e(TAG, "contacts:" + msg);
    }

    /**
     * requestPermissions的回调
     * 一个或多个权限请求结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;

        //判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int grantResult : grantResults) {
            if (PackageManager.PERMISSION_DENIED == grantResult) {
                hasAllGranted = false;
                break;
            }
        }

        //同意权限做的处理,开启服务提交通讯录
        if (hasAllGranted) {
            List<AppContact> contacts = ContactTool.listAllContacts(MainActivity.this);
            showContacts(contacts.toString());
            Toast.makeText(this, "APP拥有的确认授权" + (Arrays.toString(grantResults)), Toast.LENGTH_SHORT).show();
        } else {
            //拒绝授权做的处理，弹出弹框提示用户授权
            dealWithDeniedPermission(MainActivity.this, permissions[0]);
        }

//        switch (requestCode) {
//             其实就是1,1就是允许
//            case PackageManager.COMPONENT_ENABLED_STATE_ENABLED:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    readContacts();
//                } else {
//                    Toast.makeText(this, "获取联系人权限失败", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                Toast.makeText(this, "联系人权限异常了走了 default 分支", Toast.LENGTH_SHORT).show();
//
//        }


    }

    public void dealWithDeniedPermission(final Activity context, String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("操作提示")
                    .setMessage("注意：当前缺少必要权限！\n请点击“设置”-“权限”-打开所需权限\n最后点击两次后退按钮，即可返回")
                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "取消操作", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        }
    }
//

    //========================== 第2个测试 END================================


}



