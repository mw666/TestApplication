package newmatch.zbmf.com.testapplication.mvp.YeHiBean;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class OfficialHomeBanner {

    /**
     * adfunction : 3
     * adsource : 1
     * adstyle : 0
     * clickurl : null
     * guide :
     * id : 7
     * url : https://www.ttbcdn.com/d/file/p/2015-05-20/lixg4ckvgyl145507.jpg
     * userid : 6
     */

    private int adfunction;
    private int adsource;
    private int adstyle;
    private String clickurl;
    private String guide;
    private int id;
    private String url;
    private int userid;

    public int getAdfunction() {
        return adfunction;
    }

    public void setAdfunction(int adfunction) {
        this.adfunction = adfunction;
    }

    public int getAdsource() {
        return adsource;
    }

    public void setAdsource(int adsource) {
        this.adsource = adsource;
    }

    public int getAdstyle() {
        return adstyle;
    }

    public void setAdstyle(int adstyle) {
        this.adstyle = adstyle;
    }

    public String getClickurl() {
        return clickurl;
    }

    public void setClickurl(String clickurl) {
        this.clickurl = clickurl;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "OfficialHomeBanner{" +
                "adfunction=" + adfunction +
                ", adsource=" + adsource +
                ", adstyle=" + adstyle +
                ", clickurl='" + clickurl + '\'' +
                ", guide='" + guide + '\'' +
                ", id=" + id +
                ", url='" + url + '\'' +
                ", userid=" + userid +
                '}';
    }
}
