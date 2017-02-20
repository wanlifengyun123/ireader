package com.example.ireader.common;

import com.example.ireader.bean.ChapterDetail;
import com.example.ireader.bean.DetailModule;
import com.example.ireader.bean.DetailType;
import com.example.ireader.bean.MainDetails;
import com.example.ireader.bean.MainInfo;
import com.example.ireader.bean.NovelChapter;
import com.example.ireader.bean.NovelModule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yajun on 2016/10/27.
 *
 * 使用Jsoup解析html
 *
 */
public class JsoupHelper {

    /**
     * 获取主页数据
     * @param string
     * @return
     */
    public static List<MainInfo> getMainList(String string){

        List<MainInfo> mainInfos = new ArrayList<>();


        Document doc = Jsoup.parseBodyFragment(string);
        Element nodes = doc.getElementById("container");
        Elements inner = nodes.getElementsByClass("inner");
        MainInfo mainInfo;
        for (int i = 0; i < inner.size(); i++) {
            mainInfo = new MainInfo();
            Element itemElement = inner.get(i);
            String title = itemElement.getElementsByClass("title").text();
            mainInfo.setTitle(title);

            List<MainDetails> mainDetailList = getMainDetailList(itemElement);
            mainInfo.setMainDetailses(mainDetailList);
            mainInfos.add(mainInfo);
        }
        return mainInfos;

    }

    /**
     * 获取搜索数据
     * @param string
     * @return
     */
    public static List<MainDetails> getSearchMainList(String string){

        List<MainInfo> mainInfos = new ArrayList<>();


        Document doc = Jsoup.parseBodyFragment(string);
        Element nodes = doc.getElementById("container");
        Elements inner = nodes.getElementsByClass("inner");
        Element itemElement = inner.get(0);
        List<MainDetails> mainDetailList = getMainDetailList(itemElement);

        return mainDetailList;

    }

    /**
     * 获取小说列表数据
     * @param itemElement
     * @return
     */
    private static List<MainDetails>  getMainDetailList(Element itemElement){
        List<MainDetails> mainDetailList = new ArrayList<>();
        MainDetails mainDetails;
        Elements elements = itemElement.getElementsByClass("item-list").get(0).children();
        for (int j = 0; j < elements.size(); j++) {
            Element element = elements.get(j);
            mainDetails = new MainDetails();
            String li = element.getElementsByTag("li").get(0).text();
            String attr = element.select("a").attr("href");
            mainDetails.setHrefStr(attr);
            String[] split = li.split(" ");
            if(split.length == 3){
                mainDetails.setAuthor(split[2]);
                mainDetails.setTitleName(split[1]);
                mainDetails.setTypeName(split[0]);
                mainDetailList.add(mainDetails);
            }
        }
        return mainDetailList;
    }

    /**
     * 获取除主页外其他小说列表数据
     * @param string
     * @return
     */
    public static DetailModule getDetailModule(String string){

        DetailModule detailModule = new DetailModule();
        List<DetailType> detailTypeList = new ArrayList<>();

        Document doc = Jsoup.parseBodyFragment(string);
        Element nodes = doc.getElementById("container");
        Elements inner = nodes.getElementsByClass("inner");

        Element headElement = inner.get(0);
        Elements details = headElement.getElementsByClass("details");
        Elements listTypeElement = details.get(0).getElementsByClass("item-type").get(0).children();
        DetailType detailType;
        for (int i = 0; i < listTypeElement.size(); i++) {
            Element element = listTypeElement.get(i);
            detailType = new DetailType();
            detailType.typeName = element.getElementsByTag("li").get(0).text();
            detailType.typeHref = element.select("a").attr("href");
            detailTypeList.add(detailType);
        }
        detailModule.setDetailTypes(detailTypeList);

        Element itemElement = inner.get(1);
        List<MainDetails> mainDetailList = getMainDetailList(itemElement);
        detailModule.setMainDetailsList(mainDetailList);

        return detailModule;
    }

    /**
     * 获取小说目录
     * @param string
     * @return
     */
    public static NovelModule getNovelModule(String string){
        NovelModule module = new NovelModule();

        Document doc = Jsoup.parseBodyFragment(string);
        Element page = doc.getElementById("page");

        Element bookinfo = page.getElementsByClass("bookinfo").get(0);

        Element btitle = bookinfo.getElementsByClass("btitle").get(0);
        String bookTitle = btitle.select("h1").text();
        String bookAuthor = btitle.select("em").text();

        module.setNovelName(bookTitle);
        module.setAuthor(bookAuthor);

        Elements stats = bookinfo.getElementsByClass("stats");
        Element f1 = stats.get(0).children().get(0);
        String attr = f1.select("a").attr("href");
        String text = f1.select("a").text();

        module.setLatestchapter(text);
        module.setLatestchapterHref(attr);

        Element fr = stats.get(0).children().get(1);
        Elements children = fr.children();
        String state = children.get(0).select("i").text();
        String number = children.get(1).select("i").text();
        String date = children.get(2).select("i").text();
        String content = bookinfo.getElementsByClass("intro").get(0).select("p").text();
        String substring = content.substring("内容简介：".length(), content.length());

        module.setNovelState(state);
        module.setWordNumber(number);
        module.setUpdateDate(date);
        module.setContentDetail(content);

        Element main = page.getElementById("main");
        Elements chapterlist = main.getElementsByClass("chapterlist").get(0).children();
        List<NovelChapter> chapters = new ArrayList<>();
        NovelChapter chapter;
        for (int i = 0; i < chapterlist.size(); i++) {
            chapter = new NovelChapter();
            Element element = chapterlist.get(i);
            String attr1 = element.select("a").attr("href");
            String a = element.select("a").text();
            chapter.setBookHref(attr1);
            chapter.setBookTitle(a);
            chapter.setBookText("");
            chapters.add(chapter);
        }
        module.setChapterList(chapters);
        return module;
    }

    /**
     * 获取章节内容
     * @param string
     * @return
     */
    public static ChapterDetail getChapterDetail(String string){

        ChapterDetail detail = new ChapterDetail();

        Document doc = Jsoup.parseBodyFragment(string);
        Element page = doc.getElementById("page");

        Element inner = page.getElementsByClass("inner").get(0);

        Element bookTitle = inner.getElementById("BookTitle");
        String text = bookTitle.select("h1").text();
        detail.mTitle = text;

        Element element = inner.getElementsByClass("link").get(0);
        String bookPrev = element.getElementById("book-prev").select("a").attr("href");
        String bookIndex = element.getElementById("book-index").select("a").attr("href");
        String bookNext = element.getElementById("book-next").select("a").attr("href");
        detail.chapterDown = bookNext;
        detail.chapterUp = bookPrev;
        detail.chapterNoll = bookIndex;


        Element bookText = inner.getElementById("BookText");
        String content = bookText.toString();
        String subBookTitle = content.replace("<div id=\"BookText\">"," ");
        String textContent = subBookTitle.replace("<br />"," ");
        String con = textContent.replace("&nbsp;&nbsp;"," ");
        detail.mContent = con;
        return detail;
    }

}
