package newmatch.zbmf.com.testapplication.mvp.YeHiBean;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class UserBanner {


    /**
     * adfunction : 2
     * adsource : 0
     * adstyle : 0
     * clickurl : null
     * guide : null
     * id : null
     * url : https://www.ttbcdn.com/d/file/p/2015-05-20/edf03oa1z2n11157.jpg
     * userid : 25
     */

    private int adfunction;
    private int adsource;
    private int adstyle;
    private Object clickurl;
    private Object guide;
    private Object id;
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

    public Object getClickurl() {
        return clickurl;
    }

    public void setClickurl(Object clickurl) {
        this.clickurl = clickurl;
    }

    public Object getGuide() {
        return guide;
    }

    public void setGuide(Object guide) {
        this.guide = guide;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
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
        return "UserBanner{" +
                "adfunction=" + adfunction +
                ", adsource=" + adsource +
                ", adstyle=" + adstyle +
                ", clickurl=" + clickurl +
                ", guide=" + guide +
                ", id=" + id +
                ", url='" + url + '\'' +
                ", userid=" + userid +
                '}';
    }
}
