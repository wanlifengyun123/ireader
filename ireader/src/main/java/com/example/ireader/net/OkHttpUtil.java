package com.example.ireader.net;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.ireader.dialog.DefaultDialogFragment;
import com.example.ireader.global.HttpException;
import com.example.ireader.utils.LogUtil;
import com.example.ireader.utils.ToastUtil;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yajun on 2016/9/22.
 *
 */
public class OkHttpUtil {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType STREAM = MediaType.parse("application/octet-stream");
    public static final MediaType DEFUALT = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");


    private OkHttpClient mOkHttpClient;
    private static Object lock = new Object();

    private static OkHttpUtil instance;

    public static OkHttpUtil getInstance() {
        if(instance == null){
            synchronized (lock){
                instance = new OkHttpUtil();
            }
        }
        return instance;
    }

    public OkHttpUtil() {
        synchronized (OkHttpUtil.class) {
            if (mOkHttpClient == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(15, TimeUnit.SECONDS); // 连接超时15秒
                builder.readTimeout(20, TimeUnit.SECONDS);    // 读取超时20秒
                builder.writeTimeout(20, TimeUnit.SECONDS);//写入超时时间
                builder.retryOnConnectionFailure(false); //方法为设置出现错误进行重新连接。
                mOkHttpClient = builder.build();
            }
        }
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * GET请求
     * @param url RequestParams.toQueryString(url)
     * @return
     * @throws IOException
     */
    public Response doGet(String url) throws IOException {
        Request.Builder builder = createRequestBuilder(url);
        builder.addHeader("Connection", "close");
        return execute(builder.build());
    }

    public Response doGet(String url,RequestParams params) throws IOException {
        Request.Builder builder = createRequestBuilder(params.toQueryString(url));
        return execute(builder.build());
    }

    public <T> void doGet(String url,RequestParams params,RCallBack<T> callBack) {
        Request.Builder builder = createRequestBuilder(params.toQueryString(url));
        enqueue(builder.build(),callBack);
    }

    /**
     * Post方式提交String字符串
     * @param url
     * @param postBody
     * @return
     * @throws IOException
     */
    public Response doPost(String url,String postBody) throws IOException {
        Request.Builder builder = createRequestBuilder(url);
        if(isJson(postBody)){
            builder.post(RequestBody.create(MEDIA_TYPE_JSON, postBody));
        }else {
            builder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody));
        }
        return execute(builder.build());
    }

    /**
     * Post方式提交表单和文件
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public Response doPost(String url,RequestParams params) throws IOException {
        Request.Builder builder = createRequestBuilder(url);
        builder.post(params.toRequestBody());
        return execute(builder.build());
    }

    public <T> void doPost(String url,RequestParams params,RCallBack<T> callBack){
        Request.Builder builder = createRequestBuilder(url);
        builder.post(params.toRequestBody());
        enqueue(builder.build(),callBack);
    }

    /**
     * Create a Request.Builder.
     *
     * @param url the URL of HTTP request.
     * @return
     */
    public Request.Builder createRequestBuilder(String url) {
        Request.Builder builder = new Request.Builder().url(url).tag(url);
        return builder;
    }

    /**
     * 该不会开启异步线程。
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return getInstance().getOkHttpClient().newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     * @param request
     */
    public static <T> void enqueue(Request request,RCallBack<T> callBack){
        getInstance().getOkHttpClient().newCall(request).enqueue(callBack);
    }

    /**
     * 是否是json字符串
     * @param json
     * @return
     */
    public static boolean isJson(String json) {
        if (TextUtils.isEmpty(json)) return false;
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 根据Tag取消请求
     */
    public static void cancelTag(Object tag) {
        for (Call call : getInstance().getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getInstance().getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static abstract class RCallBack<T> implements Callback {

        BaseBusEvent event;

        public RCallBack(BaseBusEvent event) {
            this.event = event;
        }

        public abstract RequestResult<T> pareResponse(String jsonData);

        @Override
        public void onFailure(Call call, IOException e) {
            String errorMsg = new HttpException((Exception) e).getMessage();
            LogUtil.d(OkHttpUtil.class, "onFailure:" + e.getMessage());
            event.setResultCode(BaseBusEvent.CODE_ERROR);
            event.setMsg(errorMsg);
            EventBus.getDefault().post(event);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response.isSuccessful()){
                String toJson = response.body().string();
                LogUtil.d(OkHttpUtil.class,"onResponse:" + toJson);
                try {
                    RequestResult<T> t = pareResponse(toJson);
                    if(t != null){
                        if(t.getStatus() == 1){
                            event.setResultCode(BaseBusEvent.CODE_OK);
                            event.setObject(t);
                            if(!TextUtils.isEmpty(t.getMsg())){
                                event.setMsg(t.getMsg());
                            }
                        }else {
                            event.setResultCode(BaseBusEvent.CODE_ERROR_RESPONSE);
                            event.setMsg(t.getMsg() + ":" + t.getErrorcode());
                        }
                    }else {
                        event.setResultCode(BaseBusEvent.CODE_ERROR);
                        event.setMsg("返回数据为空");
                    }
                }catch (JsonSyntaxException e){
                    String errorMsg = new HttpException((Exception) e).getMessage();
                    LogUtil.d(OkHttpUtil.class, "onResponse:" + e.getMessage());
                    event.setResultCode(BaseBusEvent.CODE_ERROR);
                    event.setMsg(errorMsg);
                }
            }else {
                event.setResultCode(BaseBusEvent.CODE_ERROR);
                event.setMsg(response.message());
            }
            EventBus.getDefault().post(event);
        }
    }

    public static <T> T getData(Context context, BaseBusEvent event){
        if(event.getResultCode() == BaseBusEvent.CODE_ERROR){
            ToastUtil.show(event.getMsg());
            return null;
        }else if(event.getResultCode() == BaseBusEvent.CODE_ERROR){
            DefaultDialogFragment.newInstance("提示",event.getMsg(), null)
                    .showDialog(((AppCompatActivity)context).getSupportFragmentManager());
            return null;
        }
        RequestResult<T> t = (RequestResult<T>) event.getObject();
        if(t.getData() != null){
            return t.getData();
        }
        return null;
    }

    public static boolean getDataState(Context context, BaseBusEvent event){
        if(event.getResultCode() == BaseBusEvent.CODE_ERROR){
            ToastUtil.show(event.getMsg());
            return false;
        }else if(event.getResultCode() == BaseBusEvent.CODE_ERROR){
            DefaultDialogFragment.newInstance("提示",event.getMsg(), null)
                    .showDialog(((AppCompatActivity)context).getSupportFragmentManager());
            return false;
        }
        ToastUtil.show(event.getMsg());
        return true;
    }

}
