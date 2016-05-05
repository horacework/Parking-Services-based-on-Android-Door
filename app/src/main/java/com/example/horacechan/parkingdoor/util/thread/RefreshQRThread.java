package com.example.horacechan.parkingdoor.util.thread;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.horacechan.parkingdoor.api.ParkingApp;
import com.example.horacechan.parkingdoor.api.http.base.BaseResponse;
import com.example.horacechan.parkingdoor.api.http.base.BaseResponseListener;
import com.example.horacechan.parkingdoor.api.http.request.LogidRequest;
import com.example.horacechan.parkingdoor.api.model.StatusEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HoraceChan on 2016/5/4.
 */
public enum  RefreshQRThread {
    INSTANCE;

    private long lastRequestTime=0;


    public static final int TIME=3000;

    //中断线程
    private boolean block=false;
    //请求
    private LogidRequest mLogidRequest;
    //id
    private String logid;
    //
    private boolean isOnRequestRunning=false;

    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");



    private Thread mThread=new Thread(new RequestRunnable());

    //初始化
    public RefreshQRThread init(String logid,@NonNull OnDataRefreshListener l){
        mLogidRequest=new LogidRequest();
        mLogidRequest.logid=logid;
        mLogidRequest.markerid= ParkingApp.MARKERID;
        this.logid=logid;
        mLogidRequest.setOnResponseListener(responseListener);
        this.mListener=l;
        return INSTANCE;
    }


    public void start(){
        mThread.start();
    }

    private void startAgain(){
        lastRequestTime=System.currentTimeMillis();
        Log.d("requestTime",format.format(new Date(lastRequestTime)));
        mLogidRequest.logid=this.logid;
        mLogidRequest.post();
    }

    //====================================================================thread
    private class RequestRunnable implements Runnable{

        @Override
        public void run() {
            while (!block){
                if (!isOnRequestRunning){
                    startAgain();
                }
                SystemClock.sleep(TIME);
            }
        }
    }


    //====================================================================Getter/Setter

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

//====================================================================call back

    private BaseResponseListener responseListener=new BaseResponseListener() {
        @Override
        public void onStart(BaseResponse response) {
            isOnRequestRunning=true;
        }

        @Override
        public void onFailure(BaseResponse response) {
            isOnRequestRunning=false;

        }

        @Override
        public void onSuccess(BaseResponse response) {
            StatusEntity entity= (StatusEntity) response.getData();
            if (entity!=null){
                RefreshQRThread.this.logid=entity.getNewLogId();
                mListener.onDataRefresh((StatusEntity) response.getData());
            }
            isOnRequestRunning=false;

        }
    };

    private OnDataRefreshListener mListener;

    public OnDataRefreshListener getOnDataRefreshListener() {
        return mListener;
    }

    public interface OnDataRefreshListener{
        void onDataRefresh(StatusEntity entity);
    }



}
