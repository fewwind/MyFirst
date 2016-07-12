package com.fewwind.mydesign.net.PicGrid;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/5/25.
 */
public class CommonPluginBean implements Serializable {


    /**
     * list : [{"preview":"http://192.168.0.86:8086/stickercms/1f9f6f2c-a14e-4433-ae53-9d4532a8e242.jpg","imgList":[{"preview":"http://192.168.0.86:8086/stickercms/9b3510a6-ca30-45c3-a985-d5b5cf3d4cbb.jpg","id":5},{"preview":"http://192.168.0.86:8086/stickercms/c797e684-40c1-4292-941d-d992c295a347.jpg","id":6},{"preview":"http://192.168.0.86:8086/stickercms/a9d026c4-76cd-49e9-a784-be19c7dad48f.jpg","id":7},{"preview":"http://192.168.0.86:8086/stickercms/518dde1f-cac5-4cc7-af65-3f961cf03fff.jpg","id":8},{"preview":"http://192.168.0.86:8086/stickercms/a9524eed-31c3-458a-8692-db4601aca6aa.jpg","id":9}],"name":"Pic Grid","id":4,"packageName":"com.rcplatform.photocollage"},{"preview":"http://192.168.0.86:8086/stickercms/de6d1893-e248-4d7e-ba41-8bd519826eb4.jpg","imgList":[{"preview":"http://192.168.0.86:8086/stickercms/a0b32d70-cb00-47c6-8ea3-b39143d39dc2.jpg","id":10},{"preview":"http://192.168.0.86:8086/stickercms/b5e00249-094a-4996-8565-db023e0b5022.jpg","id":11},{"preview":"http://192.168.0.86:8086/stickercms/69b98a09-e06a-44b9-b1b8-97b5e65b023f.jpg","id":12},{"preview":"http://192.168.0.86:8086/stickercms/5c8bdc9b-e2c8-48c7-a83b-5ca1565e131c.jpg","id":13},{"preview":"http://192.168.0.86:8086/stickercms/1eb92b6b-8bfb-4518-9f1b-0b9f5ee1614b.jpg","id":14}],"name":"Pic Grid","id":5,"packageName":"com.rcplatform.photocollage"}]
     * stat : 10000
     * mess : null
     */

    private int stat;
    private String mess;
    /**
     * preview : http://192.168.0.86:8086/stickercms/1f9f6f2c-a14e-4433-ae53-9d4532a8e242.jpg
     * imgList : [{"preview":"http://192.168.0.86:8086/stickercms/9b3510a6-ca30-45c3-a985-d5b5cf3d4cbb.jpg","id":5},{"preview":"http://192.168.0.86:8086/stickercms/c797e684-40c1-4292-941d-d992c295a347.jpg","id":6},{"preview":"http://192.168.0.86:8086/stickercms/a9d026c4-76cd-49e9-a784-be19c7dad48f.jpg","id":7},{"preview":"http://192.168.0.86:8086/stickercms/518dde1f-cac5-4cc7-af65-3f961cf03fff.jpg","id":8},{"preview":"http://192.168.0.86:8086/stickercms/a9524eed-31c3-458a-8692-db4601aca6aa.jpg","id":9}]
     * name : Pic Grid
     * id : 4
     * packageName : com.rcplatform.photocollage
     */

    private List<PluginBean> list;


    public List<PluginBean> getList() {
        return list;
    }

    public void setList(List<PluginBean> list) {
        this.list = list;
    }

    public static class PluginBean implements Serializable {
        private String preview;
        private String name;
        private int id;
        private String packageName;

        /**
         * preview : http://192.168.0.86:8086/stickercms/9b3510a6-ca30-45c3-a985-d5b5cf3d4cbb.jpg
         * id : 5
         */

        private List<ImgListBean> imgList;

        public PluginBean(String preview, String name, int id, String packageName,List<ImgListBean> imgList) {
            this.preview = preview;
            this.name = name;
            this.id = id;
            this.packageName = packageName;
            this.imgList = imgList;
        }

        public String getPreview() {
            return preview;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }


        public static class ImgListBean implements Serializable {
            private String preview;
            private int id;
            private int frameSize;
            private List<FrameListBean> frameList;


            public ImgListBean(String preview, int id, int frameSize,List<FrameListBean> frameList) {
                this.preview = preview;
                this.id = id;
                this.frameList = frameList;
                this.frameSize = frameSize;
            }

            public String getPreview() {
                return preview;
            }

            public int getFrameSize() {
                return frameSize;
            }

            public List<FrameListBean> getFrameList() {
                return frameList;
            }

            public int getId() {
                return id;
            }

            public static class FrameListBean implements Serializable {
                private String preview;
                private int id;

                public String getPreview() {
                    return preview;
                }

                public int getId() {
                    return id;
                }

                public FrameListBean(String preview, int id) {
                    this.preview = preview;
                    this.id = id;
                }

                @Override
                public String toString() {
                    return "FrameListBean{" +
                            "preview='" + preview + '\'' +
                            ", id=" + id +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ImgListBean{" +
                        "preview='" + preview + '\'' +
                        ", id=" + id + '\'' +
                        ", frameList=" + frameList +
                        '\'' +
                        ", frameSize=" + frameSize +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "PluginBean{" +
                    "preview='" + preview + '\'' +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", imgList=" + imgList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CommonPluginBean{" +
                "stat=" + stat +
                ", mess='" + mess + '\'' +
                ", list=" + list +
                '}';
    }
}
