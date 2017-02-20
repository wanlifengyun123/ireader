package com.example.ireader.service;

import com.example.ireader.bean.ChapterDetail;
import com.example.ireader.bean.DetailModule;
import com.example.ireader.bean.MainDetails;
import com.example.ireader.bean.MainInfo;
import com.example.ireader.bean.NovelModule;
import com.example.ireader.common.JsoupHelper;
import com.example.ireader.net.OkHttpUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by yajun on 2016/10/27.
 *
 */

public class CommonService {

    /**
     * 主页
     */
    private static final String MAIN_URL = "http://m.uuxs.net/";

    /**
     * 最新
     */
    private static final String LAST_NEW = MAIN_URL +  "toplist/lastupdate-";
    /**
     * 排行
     */
    private static final String ALL_VISTIT = MAIN_URL +   "toplist/allvisit-";
    /**
     * 分类index/type-0-1
     * 1: page
     * 0: type
     */
    private static final String TYPE = MAIN_URL +   "index/type-0-";

    /**
     * 全本
     */
    private static final String QUAN_BEN = MAIN_URL +   "index/quanben-";

//    http://m.uuxs.net/book/21/21346/8043567.html
    private static final String CHAPTER_DETAIL = "";

    /**
     * 搜索
     * http://m.uuxs.net/modules/article/search.php?searchkey=%CA%BF%B4%F3%B7%F2
     */
    private static final String QUERY_TEXT = "modules/article/search.php";


    public static Observable<List<MainInfo>> getMainInfo(){
        return Observable.create(new Observable.OnSubscribe<List<MainInfo>>() {
            @Override
            public void call(Subscriber<? super List<MainInfo>> subscriber) {
                try {
                    Response response = OkHttpUtil.getInstance().doGet(MAIN_URL);
                    if(response.isSuccessful()){
                        String result = getBodyByResponse(response);
                        List<MainInfo> mainList = JsoupHelper.getMainList(result);
                        subscriber.onNext(mainList);
                        subscriber.onCompleted();
                    }else {
                        subscriber.onError(new Throwable("连接失败"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<List<MainDetails>> getSearchMainInfo(final String queryText){
        return Observable.create(new Observable.OnSubscribe<List<MainDetails>>() {
            @Override
            public void call(Subscriber<? super List<MainDetails>> subscriber) {
                try {
                    Response response = OkHttpUtil.getInstance().doGet(MAIN_URL + QUERY_TEXT + "?searchkey=" +  URLEncoder.encode(queryText,"gbk"));
                    if(response.isSuccessful()){
                        String result = getBodyByResponse(response);
                        List<MainDetails> mainList = JsoupHelper.getSearchMainList(result);
                        subscriber.onNext(mainList);
                        subscriber.onCompleted();
                    }else {
                        subscriber.onError(new Throwable("连接失败"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<NovelModule> getSearchNovelModule(final String queryText){
        return Observable.create(new Observable.OnSubscribe<NovelModule>() {
            @Override
            public void call(Subscriber<? super NovelModule> subscriber) {
                try {
                    Response response = OkHttpUtil.getInstance().doGet(MAIN_URL + QUERY_TEXT + "?searchkey=" +  URLEncoder.encode(queryText,"gbk"));
                    if(response.isSuccessful()){
                        String result = getBodyByResponse(response);
                        NovelModule novelModule = JsoupHelper.getNovelModule(result);
                        subscriber.onNext(novelModule);
                        subscriber.onCompleted();
                    }else {
                        subscriber.onError(new Throwable("连接失败"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 根据分类 获取页面数据
     * @return
     */
    public static Observable<DetailModule> getDetailModule(final int position, final int page){
        return Observable.create(new Observable.OnSubscribe<DetailModule>() {
            @Override
            public void call(Subscriber<? super DetailModule> subscriber) {
                try {
                    Response response = OkHttpUtil.getInstance().doGet(getUrlByIndex(position,page));
                    if(response.isSuccessful()){
                        String result = getBodyByResponse(response);
                        DetailModule detailModule = JsoupHelper.getDetailModule(result);
                        subscriber.onNext(detailModule);
                        subscriber.onCompleted();
                    }else {
                        subscriber.onError(new Throwable("连接失败"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 数据筛选
     * @param hrefStr
     * @param page
     * @return
     */
    public static Observable<DetailModule> getDetailModule2(String hrefStr,int page){
        final String substring = hrefStr.substring(0, hrefStr.length() - 1) + page;
        return Observable.create(new Observable.OnSubscribe<DetailModule>() {
            @Override
            public void call(Subscriber<? super DetailModule> subscriber) {
                try {
                    Response response = OkHttpUtil.getInstance().doGet(MAIN_URL + substring);
                    if(response.isSuccessful()){
                        String result = getBodyByResponse(response);
                        DetailModule detailModule = JsoupHelper.getDetailModule(result);
                        subscriber.onNext(detailModule);
                        subscriber.onCompleted();
                    }else {
                        subscriber.onError(new Throwable("连接失败"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 获取章节目录
     * @param hrefStr
     * @return
     */
    public static Observable<NovelModule> getNovelModule(final String hrefStr){
        return Observable.create(new Observable.OnSubscribe<NovelModule>() {
            @Override
            public void call(Subscriber<? super NovelModule> subscriber) {
                try {
                    Response response = OkHttpUtil.getInstance().doGet(MAIN_URL + hrefStr);
                    if(response.isSuccessful()){
                        String result = getBodyByResponse(response);
                        NovelModule novelModule = JsoupHelper.getNovelModule(result);
                        subscriber.onNext(novelModule);
                        subscriber.onCompleted();
                    }else {
                        subscriber.onError(new Throwable("连接失败"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 获取章节详情
     * @param url
     * @return
     */
    public static Observable<ChapterDetail> getChapterDetail(final String url){
        return Observable.create(new Observable.OnSubscribe<ChapterDetail>() {
            @Override
            public void call(Subscriber<? super ChapterDetail> subscriber) {
                try {
                    Response response = OkHttpUtil.getInstance().doGet(MAIN_URL + url);
                    if(response.isSuccessful()){
                        String result = getBodyByResponse(response);
                        ChapterDetail chapterDetail = JsoupHelper.getChapterDetail(result);
                        subscriber.onNext(chapterDetail);
                        subscriber.onCompleted();
                    }else {
                        subscriber.onError(new Throwable("连接失败"));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private static String getUrlByIndex(int index,int page){
        String url = "";
        switch (index){
            case 1:
                url = LAST_NEW ;
                break;
            case 2:
                url = ALL_VISTIT ;
                break;
            case 3:
                url = TYPE;
                break;
            case 4:
                url = QUAN_BEN;
                break;
        }
        return url + page;
    }

    /**
     * 数据编码转换，获取数据
     * @param response
     * @return
     * @throws IOException
     */
    private static String getBodyByResponse(Response response) throws IOException {
        byte[] b = response.body().bytes();     //获取数据的bytes
        String result = new String(b, "GB2312");   //然后将其转为gb2312
        String a = result.substring(result.indexOf("<body"),result.indexOf("</body>") + "</body>".length());
        return a;
    };
}
