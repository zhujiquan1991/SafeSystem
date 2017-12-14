package entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/13 0013.
 */
public class SafeEntity {

    private String state;// 状态
    private String temperature;// 温度
    private String heartrate;// 心率
    private String longitude;// 经度
    private String latitude;// 纬度

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    private Date updateDate;//时间



    public SafeEntity() {
    }


    public SafeEntity(String state, String temperature, String heartrate, String longitude, String latitude,Date updateDate) {
        this.state = state;
        this.temperature = temperature;
        this.heartrate = heartrate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.updateDate=updateDate;
    }

    @Override
    public String toString() {
        return "state="+  "[" + state +  "]"+"temperature="+  "[" + temperature +  "]"+"heartrate="+  "[" + heartrate +  "]"+"longitude="+  "[" + longitude +  "]"+"latitude="+  "[" + latitude +  "]"+"updateDate="+  "[" + updateDate +  "]";
    }
}
