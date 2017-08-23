package com.qcj.common.model;

/**
 * Created by qiuchunjia on 2016/11/1.
 */
public class PinnerSectionBean extends Model {
    public static final int SECTION = 0;   // 悬浮标题栏
    public static final int ITEM = 1;     //下面的子列表
    private String groupName;   //每一组的名字
    private int itemType;   //组的类型
    private Object object;  //对象数据，用的时候需要强转

    public PinnerSectionBean(int itemType, Object object) {
        this.itemType = itemType;
        this.object = object;
    }

    public PinnerSectionBean(int itemType, String groupName) {
        this.groupName = groupName;
        this.itemType = itemType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
