package com.fewwind.mydesign.bean;

import java.util.List;

/**
 * Created by fewwind on 2016/3/11.
 */
public class VoiceDemo {


    public int rc;
    public String text;
    public String service;
    public String code;


    public General general;

    public static class Sementic {
        public IntentVoice intent;

        @Override
        public String toString() {
            return "Sementic{" +
                    "intent=" + intent +
                    '}';
        }
    }



    public static class IntentVoice {
        public String song;
        public String keyword;
        public List<Operations> operations;

        @Override
        public String toString() {
            return "IntentVoice{" +
                    "song='" + song + '\'' +
                    ", keyword='" + keyword + '\'' +
                    ", operations=" + operations +
                    '}';
        }
    }

    public static class Operations {
        public String operator;
        public String operands;

        public String value;

        @Override
        public String toString() {
            return "Operations{" +
                    "operator='" + operator + '\'' +
                    ", operands='" + operands + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }



    public static class General {
        public String type;
        public String text;


        @Override
        public String toString() {
            return "General{" +
                    "type='" + type + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "VoiceDemo{" +
                "rc=" + rc +
                ", text='" + text + '\'' +
                ", service='" + service + '\'' +
                ", code='" + code + '\'' +
                ", general=" + general +
                '}';
    }
}
