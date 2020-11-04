package com.arc.contact.util;

import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.arc.contact.MainActivity;
import com.arc.contact.model.AppContact;

import java.util.List;

public class ContactButton {

    public static void main(String[] args) {
       /* saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
//                addPermissByPermissionList(MainActivity.this, permissions, PERMISSION_CONTACT);

                List<AppContact> contacts = dataProvider.getContactList();
                if (contacts == null && contacts.size() == 0) {
                }
                for (AppContact contact : contacts) {
                    boolean save = dataProvider.save(contact, MainActivity.this);
                }
            }


        });

        //delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ContactTool.delete(MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    //todo error
                }
            }
        });

        //update
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactTool.update(MainActivity.this, null);
            }
        });

        //getOne
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     * 1、打开一个页面要求用户输入
                //     * 2、参数点击搜素后查询数据
                //     * 3、显示结果
                //todo 打开一个页面要求用户输入
                String name = input1.getText().toString();
                System.out.println(name);
//                Toast.makeText(MainActivity.this, "输入文本name\n" + name  , Toast.LENGTH_SHORT).show();
//
//                if("save".equals(name)){
//                    saveButton=syncButton;
//                } else if ("del".equals(name)) {
//                    deleteButton=syncButton;
//                }else if ("update".equals(name)) {
//                    updateButton=syncButton;
//                }else if ("get".equals(name)) {
//                    getButton=syncButton;
//                }
//                 else if ("list".equals(name)) {
//                    listAllButton=syncButton;
//                }


                AppContact localContact = ContactTool.getContactByDisplayNameWithAllPhone(name, MainActivity.this);
                //todo 显示结果
                String toJSONString = JSON.toJSONString(localContact);
                Toast.makeText(MainActivity.this, "根据参数name\n" + name + "查询到的结果=" + toJSONString, Toast.LENGTH_SHORT).show();
                outputText.setText(toJSONString);
                System.out.println(localContact);
            }
        });*/

       //    //listAll
        //        listAllButton.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                listAll3();
        //            }
        //        });
    }

    //    //调用并获取联系人信息
//    private void readContacts() {
//        Cursor cursor = null;
//        try {
//
//            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    null, null, null, null);
//            if (cursor != null) {
//                while (cursor.moveToNext()) {
//                    String displayName = cursor.getString(cursor.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                    String number = cursor.getString(cursor.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    contactsList.add(displayName + "\n" + number);
//                }
//                //刷新
//                adapter.notifyDataSetChanged();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }


}
