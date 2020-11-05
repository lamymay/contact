package com.arc.contact.util;


import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.arc.contact.MainActivity;
import com.arc.contact.model.AppContact;

import java.util.List;

/**
 * 测试本地的 insert delete update get query
 *
 * @author may
 * @date 2020/11/05
 */
public class ContactTest {


    public boolean save(MainActivity context, TextView outputText) {
        //  获取一条数据
        AppContact contact = ContactDataProvider.get();

        //权限
        boolean getPermissionSuccess = PermissionTest.requestPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean INTERNET = PermissionTest.requestPermissions(context, Manifest.permission.INTERNET);
        boolean READ_CONTACTS = PermissionTest.requestPermissions(context, Manifest.permission.READ_CONTACTS);
        boolean CALL_PHONE = PermissionTest.requestPermissions(context, Manifest.permission.CALL_PHONE);
        boolean READ_PHONE_STATE = PermissionTest.requestPermissions(context, Manifest.permission.READ_PHONE_STATE);

        String message = "\n联网权限=" + INTERNET
                + "\n读联系人权限=" + READ_CONTACTS
                + "\n拨号权限权限=" + CALL_PHONE
                + "\nREAD_PHONE_STATE=" + READ_PHONE_STATE;

        outputText.setText(JSON.toJSONString(getPermissionSuccess));

        // save 一条数据
        saveOne(context, outputText, contact);
        return true;
    }

    /**
     * 测试
     * insert
     * 注意：对某个联系人插入姓名、电话等记录时必须要插入Data.MIMETYPE（或者是"mimetype"）属性,而不是插入"mimetype_id"!
     * 比如：values.put(Data.MIMETYPE,"vnd.android.cursor.item/phone_v2")
     */
    public static void saveOne(Context context, TextView outputText, AppContact contact) {

        outputText.setText(JSON.toJSONString(contact));


        //插入raw_contacts表，并获取_id属性
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        long contact_id = ContentUris.parseId(resolver.insert(uri, values));

        //插入data表
        uri = Uri.parse("content://com.android.contacts/data");
        //add name
        values.put("raw_contact_id", contact_id);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/name");
        values.put("data2", contact.getGivenName());
        values.put("data1", contact.getFamilyName());
        Uri uri1 = resolver.insert(uri, values);
        outputText.setText(JSON.toJSONString(uri1));

        values.clear();

        //add phone
        values.put("raw_contact_id", contact_id);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");


        List<String> phones = contact.getPhones();
        List<String> emails = contact.getEmails();
        values.put("data2", "2");  //手机

        values.put("data1", phones.get(0));
        resolver.insert(uri, values);
        values.clear();

        //add email
        values.put("raw_contact_id", contact_id);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/email_v2");
        values.put("data1", emails.get(0));
        values.put("data2", emails.get(1));
        resolver.insert(uri, values);


        //方便测试这里使用一个文本框做输出显示
        Toast.makeText(context, "saveOne", Toast.LENGTH_SHORT).show();
        System.out.println("########################saveOne######################");
    }

}



