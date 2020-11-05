package com.arc.contact.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionTest {


    /**
     * 动态申请权限
     * @param context    上下文
     * @param permission 要申请的一个权限，列如写的权限：Manifest.permission.WRITE_EXTERNAL_STORAGE
     * @return  是否有当前权限
     */

    public static boolean requestPermissions(@NonNull Context context, @NonNull String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            Log.i("requestMyPermissions",": 【 " + permission + " 】没有授权，申请权限");
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 100);
            return false;
        } else {
            Log.i("requestMyPermissions",": 【 " + permission + " 】有权限");
            return true;
        }
    }



    private void checkRWContactsPermission() {
        //获取listview
        //适配器是为了将构造函数中把要适配的数据传入 当前提供的数据是字符串 所以泛型为String 第二个参数是子项的布局
        //判断用户是否已经授权给我们了 如果没有，调用下面方法向用户申请授权，之后系统就会弹出一个权限申请的对话框
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
//        }
//        else {
//            readContacts();
//        }
    }
}



//    /**
//     * 动态权限
//     */
//    public void addPermissonByPermissionList(Activity activity, String[] permissions, int request) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //Android 6.0开始的动态权限，这里进行版本判断
//            ArrayList<String> mPermissionList = new ArrayList<>();
//            for (int i = 0; i < permissions.length; i++) {
//                if (ContextCompat.checkSelfPermission(activity, permissions[i])
//                        != PackageManager.PERMISSION_GRANTED) {
//                    mPermissionList.add(permissions[i]);
//                }
//            }
//            if (mPermissionList.isEmpty()) {  //非初次进入App且已授权
//                //   showContacts();
//                Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show();
//            } else {
//                //请求权限方法
//                String[] permissionsNew = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
//                ActivityCompat.requestPermissions(activity, permissionsNew, request); //这个触发下面onRequestPermissionsResult这个回调
//            }
//        }
//    }
//
//    //动态权限
//    private void listAll() {
//        Toast.makeText(MainActivity.this, "listAll", Toast.LENGTH_SHORT).show();
//        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
//
//        //addPermissionsByPermissionList(MainActivity.this, permissions, PERMISSION_CONTACT);
//        //动态权限     public void addPermissionsByPermissionList(Activity activity, String[] permissions, int request) {
//        //ro.build.version.sdk  == 26?  [Build.VERSION_CODES.M即26]
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //Android 6.0开始的动态权限，这里进行版本判断
//            List<String> mPermissionList = new LinkedList<>();
//            for (int i = 0; i < permissions.length; i++) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i])
//                        != PackageManager.PERMISSION_GRANTED) {
//                    mPermissionList.add(permissions[i]);
//                }
//            }
//
//            //非初次进入App且已授权
//            if (mPermissionList.isEmpty()) {
//                List<AppContact> contacts = ContactTool.listAllContacts(MainActivity.this);
//                showContacts(contacts.toString());
//                Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show();
//            } else {
//                //请求权限方法
//                String[] permissionsNew = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
//                ActivityCompat.requestPermissions(MainActivity.this, permissionsNew, 1); //这个触发下面onRequestPermissionsResult这个回调
//            }
//        }
//    }