package com.fewwind.mydesign.bean;

import java.util.List;

/**
 * Created by fewwind on 2016/3/14.
 */
public class GsonFormat {

    /**
     * rc : 0
     * text : 收听91.5兆赫
     * service : cn.yunzhisheng.broadcast
     * code : SEARCH
     * semantic : {"intent":{"station":"中国国际广播电台","channelList":[{"channel":"中国国际广播电台英语","frequencyList":[{"frequency":"91.5","type":"FM","unit":"MHz"}]}],"channelType":"radio"}}
     * history : cn.yunzhisheng.broadcast
     * responseId : 7c76dc6c648c4d9084b0e25a4da3d27b
     */

    private int rc;
    private String text;
    private String service;
    private String code;
    /**
     * intent : {"station":"中国国际广播电台","channelList":[{"channel":"中国国际广播电台英语","frequencyList":[{"frequency":"91.5","type":"FM","unit":"MHz"}]}],"channelType":"radio"}
     */

    private SemanticEntity semantic;
    private String history;
    private String responseId;

    public void setRc(int rc) {
        this.rc = rc;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSemantic(SemanticEntity semantic) {
        this.semantic = semantic;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public int getRc() {
        return rc;
    }

    public String getText() {
        return text;
    }

    public String getService() {
        return service;
    }

    public String getCode() {
        return code;
    }

    public SemanticEntity getSemantic() {
        return semantic;
    }

    public String getHistory() {
        return history;
    }

    public String getResponseId() {
        return responseId;
    }

    public static class SemanticEntity {
        /**
         * station : 中国国际广播电台
         * channelList : [{"channel":"中国国际广播电台英语","frequencyList":[{"frequency":"91.5","type":"FM","unit":"MHz"}]}]
         * channelType : radio
         */

        private IntentEntity intent;

        public void setIntent(IntentEntity intent) {
            this.intent = intent;
        }

        public IntentEntity getIntent() {
            return intent;
        }

        public static class IntentEntity {
            private String station;
            private String channelType;
            /**
             * channel : 中国国际广播电台英语
             * frequencyList : [{"frequency":"91.5","type":"FM","unit":"MHz"}]
             */

            private List<ChannelListEntity> channelList;

            public void setStation(String station) {
                this.station = station;
            }

            public void setChannelType(String channelType) {
                this.channelType = channelType;
            }

            public void setChannelList(List<ChannelListEntity> channelList) {
                this.channelList = channelList;
            }

            public String getStation() {
                return station;
            }

            public String getChannelType() {
                return channelType;
            }

            public List<ChannelListEntity> getChannelList() {
                return channelList;
            }

            public static class ChannelListEntity {
                private String channel;
                /**
                 * frequency : 91.5
                 * type : FM
                 * unit : MHz
                 */

                private List<FrequencyListEntity> frequencyList;

                public void setChannel(String channel) {
                    this.channel = channel;
                }

                public void setFrequencyList(List<FrequencyListEntity> frequencyList) {
                    this.frequencyList = frequencyList;
                }

                public String getChannel() {
                    return channel;
                }

                public List<FrequencyListEntity> getFrequencyList() {
                    return frequencyList;
                }

                public static class FrequencyListEntity {
                    private String frequency;
                    private String type;
                    private String unit;

                    public void setFrequency(String frequency) {
                        this.frequency = frequency;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public void setUnit(String unit) {
                        this.unit = unit;
                    }

                    public String getFrequency() {
                        return frequency;
                    }

                    public String getType() {
                        return type;
                    }

                    public String getUnit() {
                        return unit;
                    }
                }
            }
        }
    }
}
