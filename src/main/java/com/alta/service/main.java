package com.alta.service;

import java.util.HashSet;
import java.util.Set;

public class main {
    public static void main(String[] args) {
        Set<String> sensitiveWords=new HashSet<>();
        sensitiveWords.add("你是傻逼");
        sensitiveWords.add("你是傻逼啊");
        sensitiveWords.add("你是坏蛋");
        sensitiveWords.add("你个大笨蛋");
        sensitiveWords.add("我去年买了个表");
        sensitiveWords.add("shit");
        TextFilter textFilter=new TextFilter();
        textFilter.initSensitiveWordsMap(sensitiveWords);
        String text="你你你你是傻逼啊你，说你呢，你个大笨蛋。";
        System.out.println(textFilter.getSensitiveWords(text,MatchType.MAX_MATCH));    }
}
