package com.arc.contact.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户实体
 * 注解是使用插件 Lombok， bean可以省略手动写getter setter
 *
 * @author may
 */
public class AppContact {

    private static final long serialVersionUID = 1L;

    private Integer id;//后台统中的id是主键
    private Long userId;//后台统唯一的用户id

    private Integer deviceContactId;//是每个通讯录中的id，不唯一，每个设备上的通讯录id各自独立


    private String familyName = "";//英文中的人名姓氏 lastName/family name
    private String givenName = System.currentTimeMillis() + "";//名字 = given name / firstName
    private String name = familyName + givenName;//中文习惯姓在前名在后则名= familyName + givenName

    @Deprecated
    private String displayName;//昵称 等效 displayName

    private List<String> phones; //电话号码集合
    private List<String> emails;
    private List<String> ims;

    private String photo;
    private String organization;
    private String postalAddress;
    private String groupMembership;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private Integer state;
    private String note;
    private String jsonString;//预留jsonString


    public AppContact() {
    }

    public AppContact(String familyName, String givenName, List<String> phones, List<String> emails) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.phones = phones;
        this.emails = emails;

        this.name = this.familyName + this.givenName;
        this.displayName = this.name;

    }

    //******************

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static AppContact getMockData() {
        AppContact contact = new AppContact("姓", "名", Arrays.asList("13612345678", "18612345678"), Arrays.asList("13612345678@qq.com", "lamymay@out.com"));
        return contact;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDeviceContactId() {
        return deviceContactId;
    }

    public void setDeviceContactId(Integer deviceContactId) {
        this.deviceContactId = deviceContactId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getIms() {
        return ims;
    }

    public void setIms(List<String> ims) {
        this.ims = ims;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getGroupMembership() {
        return groupMembership;
    }

    public void setGroupMembership(String groupMembership) {
        this.groupMembership = groupMembership;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public void appendPhone(String phoneNumber) {
        List<String> existPhones = this.phones;
        if (existPhones == null) {
            existPhones = new ArrayList<>();
        }
        existPhones.add(phoneNumber);
        this.phones = existPhones;
    }
}
