package com.example.horacechan.parkingdoor.api.http.request;

import com.example.horacechan.parkingdoor.api.ParkingApp;
import com.example.horacechan.parkingdoor.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parkingdoor.api.http.base.BaseResponse;
import com.example.horacechan.parkingdoor.api.model.StatusEntity;
import com.example.horacechan.parkingdoor.util.JSONUtils;
import com.example.horacechan.parkingdoor.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class LogidRequest extends BaseHttpRequestClient {

    public String logid;
    public String markerid;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("statusCheck")
//                .addParam("logid",logid)
//                .addParam("markerid",markerid)
                .build();
    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

        if (response.getStatus()==200){
            StatusEntity info= JSONUtils.toObject(json.optString("data"), StatusEntity.class);
            response.setData(info);
        }
    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("logid",logid);
        keyValue.put("markerid",markerid);
    }

}
