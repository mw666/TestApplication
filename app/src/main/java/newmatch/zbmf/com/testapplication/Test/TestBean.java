package newmatch.zbmf.com.testapplication.Test;

import java.util.List;

/**
 * Created By pq
 * on 2019/8/1
 */
public class TestBean {


    /**
     * msg : success
     * result_data : [{"adfunction":1,"adsource":0,"adstyle":0,"clickurl":null,"guide":"大家伙","id":2,"url":"https://www.ttbcdn.com/d/file/p/2015-05-20/qqkairrykfn12667.jpg","userid":2},{"adfunction":1,"adsource":0,"adstyle":0,"clickurl":null,"guide":"一起high","id":3,"url":"https://www.ttbcdn.com/d/file/p/2015-05-20/tlg5xvmff3p145642.jpg","userid":3}]
     * status_code : 200
     */

    private String msg;
    private int status_code;
    private List<ResultDataBean> result_data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<ResultDataBean> getResult_data() {
        return result_data;
    }

    public void setResult_data(List<ResultDataBean> result_data) {
        this.result_data = result_data;
    }

    public static class ResultDataBean {
        /**
         * adfunction : 1
         * adsource : 0
         * adstyle : 0
         * clickurl : null
         * guide : 大家伙
         * id : 2
         * url : https://www.ttbcdn.com/d/file/p/2015-05-20/qqkairrykfn12667.jpg
         * userid : 2
         */

        private int adfunction;
        private int adsource;
        private int adstyle;
        private Object clickurl;
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

        public Object getClickurl() {
            return clickurl;
        }

        public void setClickurl(Object clickurl) {
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
    }
}
