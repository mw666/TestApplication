package newmatch.zbmf.com.testapplication.mvp.YeHiBean;

import java.util.List;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public class UserHomeShow {
    /**
     * rows : [{"label":"黑丝控","userName":"efef1008","userId":10,"showImg":"https://www.ttbcdn.com/d/file/p/2015-05-20/ehwkh0nzvzr12017.jpg"},{"label":"黑丝控","userName":"efef1010","userId":12,"showImg":"https://www.ttbcdn.com/d/file/p/2015-05-20/gwru4iads3k11395.jpg"},{"label":"黑丝控","userName":"efef1014","userId":16,"showImg":"https://www.ttbcdn.com/d/file/p/2016-02-12/d6464768ae6cbe0088156381bb62f395.jpg"},{"label":"黑丝控","userName":"efef1015","userId":17,"showImg":"https://www.ttbcdn.com/d/file/p/2015-05-20/4bpiekkee34127206.jpg"},{"label":"黑丝控","userName":"efef1018","userId":20,"showImg":"https://www.ttbcdn.com/d/file/p/2015-05-20/r0k53nsq12s38539.jpg"},{"label":"黑丝控","userName":"efef1019","userId":21,"showImg":"https://www.ttbcdn.com/d/file/p/2015-05-20/rhvb231dfjq38826.jpg"}]
     * total : 6
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * label : 黑丝控
         * userName : efef1008
         * userId : 10
         * showImg : https://www.ttbcdn.com/d/file/p/2015-05-20/ehwkh0nzvzr12017.jpg
         */

        private String label;
        private String userName;
        private int userId;
        private String showImg;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getShowImg() {
            return showImg;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }
    }
}
