package com.arc.contact.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arc.contact.model.AppContact;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 联系人数据获取服务
 *
 * @author may
 * @date 2020/08/23
 */
public class ContactDataProvider {

    /**
     * 提供测试数据
     *
     * @return 返回联系人数据列表
     */
    public List<AppContact> getContactList() {

        return listMockDataList();
    }

    public List<AppContact> listMockDataList() {

        //名字 = lastName + firstName
        String familyName = "叶";
        String givenName = System.currentTimeMillis() + "";

        List<String> phones = Arrays.asList("18674192462", "021-1564");
        List<String> emails = Arrays.asList("lamymay@outlook.com", "2320158601@qq.com");

        AppContact contact1 = new AppContact(familyName,givenName,phones,emails);
        AppContact contact2 = new AppContact("张","三",Arrays.asList("13674192462"),Arrays.asList("123456@qq.com"));

        ArrayList<AppContact> contacts = new ArrayList<>(16);
        contacts.add(contact1);
        contacts.add(contact2);
        return contacts;
    }

    public boolean save(AppContact appContact, AppCompatActivity context) {
        try {
            ContentResolver resolver = context.getContentResolver();

            // 创建一个空的ContentValues
            ContentValues contentValues = new ContentValues();

            // 向RawContacts.CONTENT_URI空值插入，
            // 先获取Android系统返回的rawContactId
            // 后面要基于此id插入值
            Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
            long contact_id = ContentUris.parseId(rawContactUri);


            //实际上是四个属性的
            //1 raw_contact_id 申请的
            //2 mimetype  固定值
            //3 类型 固定值
            //4 data1 数据本身
            //-------------------------------------------------------------------------
            contentValues.clear();

            // 内容类型             contentValues.put("mimetype", "vnd.android.cursor.item/name");
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

            // 联系人名字
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, appContact.getGivenName());
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, appContact.getFamilyName());
//            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, appContact.getDisplayName());

            //contentValues.put("raw_contact_id", contact_id);
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);

            // 向联系人URI添加联系人名字
            resolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
            contentValues.clear();


            //-------------------------------------------------------------------------
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            // 电话类型
            contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);

            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);

            // 联系人的电话号码
            contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, appContact.getPhones().get(0));

//            List<String> phones = appContact.getPhones();
//            if (phones != null && phones.size() > 0) {
//                int len = phones.size();
//                for (int i = 1; i <= len; i++) {
//                    contentValues.put("data" + i, phones.get(i - 1));   //手机号码
//                }
//            }

            // 向联系人电话号码URI添加电话号码
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
            contentValues.clear();


            //---------------- EMAIL
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            // 电子邮件的类型
            contentValues.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);

            //email  data.put("mimetype", "vnd.android.cursor.item/email_v2");


            // 联系人的Email地址
            contentValues.put(ContactsContract.CommonDataKinds.Email.ADDRESS,  appContact.getEmails().get(0));

            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);

            // 向联系人Email URI添加Email数据
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);

            Toast.makeText(context, "save" + appContact.toString(), Toast.LENGTH_SHORT).show();
            System.out.println("########################saveOne######################");

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

    }

    //获取手机号
    public static List<AppContact> listAllNumber(Context context) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//content://com.android.contacts/data/phones
        System.out.println(" 获取联系人电话 ContactsContract.CommonDataKinds.Phone.CONTENT_URI\n" + uri);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        String phoneNumber = null;
        String name = null;

        LinkedList<AppContact> users = new LinkedList<>();
        while (cursor.moveToNext()) {
            System.out.println(cursor);

            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            //组装联系人数据
            AppContact user = new AppContact(   );
            user.setName(name);
            ArrayList<String> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(phoneNumber);
            user.setPhones(phoneNumbers);
            users.add(user);
        }

        //测试  print user bean
        System.out.println("联系人条数=" + users.size());
        for (AppContact user : users) {
            System.out.println(user);
        }

        return users;
    }
}

/*

dataX 对应的 类型
            public static final int TYPE_HOME = 1;
            public static final int TYPE_MOBILE = 2;
            public static final int TYPE_WORK = 3;
            public static final int TYPE_FAX_WORK = 4;
            public static final int TYPE_FAX_HOME = 5;
            public static final int TYPE_PAGER = 6;
            public static final int TYPE_OTHER = 7;
            public static final int TYPE_CALLBACK = 8;
            public static final int TYPE_CAR = 9;
            public static final int TYPE_COMPANY_MAIN = 10;
            public static final int TYPE_ISDN = 11;
            public static final int TYPE_MAIN = 12;
            public static final int TYPE_OTHER_FAX = 13;
            public static final int TYPE_RADIO = 14;
            public static final int TYPE_TELEX = 15;
            public static final int TYPE_TTY_TDD = 16;
            public static final int TYPE_WORK_MOBILE = 17;
            public static final int TYPE_WORK_PAGER = 18;
            public static final int TYPE_ASSISTANT = 19;
            public static final int TYPE_MMS = 20;
**/

//    /**
//     * 请转换为此项
//     *
//     * @return
//     */
//    private List<AppContact> listAll3() {
//        Toast.makeText(MainActivity.this, "listAll3", Toast.LENGTH_SHORT).show();
//        List<AppContact> rows = new ArrayList<>();
//        //1、访问raw_contacts表 uri = content://com.android.contacts/contacts
////        Uri uri = Uri.parse("content://com.android.contacts/contacts");
//        //Uri uri = ContactsContract.Contacts.CONTENT_URI;
//
//        ContentResolver resolver = MainActivity.this.getContentResolver();
//
//        //2、获得_id属性
//        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
//                new String[]{ContactsContract.Contacts.Data._ID},
//                null,
//                null,
//                null);
//        while (cursor.moveToNext()) {
//            //========================================================
//
//            //获得id并且在data中寻找数据
//            int contactId = cursor.getInt(0);
//            AppContact contact = new AppContact();
//            List<String> phoneNumbers = new ArrayList<>();
//            List<String> emails = new ArrayList<>();
//
//            contact.setContactId(contactId);
//
////            int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//            //data1存储各个记录的总数据，MIMETYPE 存放记录的类型，如电话、email等
//            Cursor cursor2 = resolver.query(
//                    Uri.parse("content://com.android.contacts/contacts/" + contactId + "/data"),
//                    new String[]{ContactsContract.Contacts.Data.DATA1, ContactsContract.Contacts.Data.MIMETYPE},
//                    null,
//                    null,
//                    null);
//            while (cursor2.moveToNext()) {
//                String data = cursor2.getString(cursor2.getColumnIndex("data1"));
//                if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")) {       //如果是名字
//                    contact.setDisplayName(data);
//
//                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/nickname")) {  //如果是昵称
//                    contact.setNickname(data);
//
//                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")) {  //如果是电话
//                    data = data.replaceAll("//s", "");
//                    data = data.replaceAll("-", "");
//
//                    phoneNumbers.add(data);
//                    contact.setCellphone(data);
//
//                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")) {  //如果是email
//                    emails.add(data);
//                    contact.setEmail(data);
//
//                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")) { //如果是地址
//                    contact.setPostalAddress(data);
//                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")) {  //如果是组织
//                    contact.setOrganization(data);
//
//                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/photo")) {  //如果是照片
//                    //appContact.setPhoto(data);
//                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/group_membership")) {  //如果是组织关系
//                    contact.setGroupMembership(data);
//
//                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/im")) {  //如果是即时通讯IM
//                    contact.setIm(data);
//                }
//                contact.setPhones(phoneNumbers);
//                contact.setEmails(emails);
//            }
//            //            Log.i("Contacts", str);
//            rows.add(contact);
//        }
//        //输出显示
//        String string = JSON.toJSONString(rows);
//        outputText.setText(string);
//        return rows;
//    }