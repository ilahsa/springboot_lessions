package com.alta.service;

import java.util.*;

//敏感词过滤
public class TextFilter {
    private Map<Object,Object> sensitiveWordsMap;
    private static final String END_FLAG="end";

    /**
     * 初始化敏感词过滤的map
     * @param sensitiveWords 敏感词列表
     */
    public void initSensitiveWordsMap(Set<String> sensitiveWords){
        if(sensitiveWords==null||sensitiveWords.isEmpty()){
            throw new IllegalArgumentException("Senditive words must not be empty!");
        }
        sensitiveWordsMap= new HashMap<>(sensitiveWords.size());
        String currentWord;
        Map<Object,Object> currentMap;
        Map<Object,Object> subMap;
        Iterator<String> iterator = sensitiveWords.iterator();
        while (iterator.hasNext()){
            currentWord=iterator.next();
            if(currentWord==null||currentWord.trim().length()<2){  //敏感词长度必须大于等于2
                continue;
            }
            currentMap=sensitiveWordsMap;
            for(int i=0;i<currentWord.length();i++){
                char c=currentWord.charAt(i);
                subMap=(Map<Object, Object>) currentMap.get(c);
                if(subMap==null){
                    subMap=new HashMap<>();
                    currentMap.put(c,subMap);
                    currentMap=subMap;
                }else {
                    currentMap= subMap;
                }
                if(i==currentWord.length()-1){
                    //如果是最后一个字符，则put一个结束标志，这里只需要保存key就行了，value为null可以节省空间。
                    //如果不是最后一个字符，则不需要存这个结束标志，同样也是为了节省空间。
                    currentMap.put(END_FLAG,null);
                }
            }
        }
    }

    /**
     * 方法则是扫描一遍待检测文本，逐个检测每个字符是否在敏感词库中，然后将检测到的敏感词截取出来放到集合中返回给客户端。
     * @param text 待检测文本
     * @param matchType 表示匹配规则，有时候我们只需要找到一个敏感词就可以了，有时候则需要知道待检测文本中到底包含多少个敏感词，前者对应的是最小匹配原则，后者则是最大匹配原则。
     * @return
     */
    public Set<String> getSensitiveWords(String text,MatchType matchType){
        if(text==null||text.trim().length()==0){
            throw new IllegalArgumentException("The input text must not be empty.");
        }
        Set<String> sensitiveWords=new HashSet<>();
        for(int i=0;i<text.length();i++){
            int sensitiveWordLength = getSensitiveWordLength(text, i, matchType);
            if(sensitiveWordLength>0){
                String sensitiveWord = text.substring(i, i + sensitiveWordLength);
                sensitiveWords.add(sensitiveWord);
                if(matchType==MatchType.MIN_MATCH){
                    break;
                }
                i=i+sensitiveWordLength-1;
            }
        }
        return sensitiveWords;
    }

    /**
     * 方法的作用是根据给定的待检测文本及起始下标，还有匹配规则，计算出待检测文本中的敏感词长度，如果不存在，则返回0，存在则返回匹配到的敏感词长度。
     * @param text
     * @param startIndex
     * @param matchType
     * @return
     */
    public int getSensitiveWordLength(String text,int startIndex,MatchType matchType){
        if(text==null||text.trim().length()==0){
            throw new IllegalArgumentException("The input text must not be empty.");
        }
        char currentChar;
        Map<Object,Object> currentMap=sensitiveWordsMap;
        int wordLength=0;
        boolean endFlag=false;
        for(int i=startIndex;i<text.length();i++){
            currentChar=text.charAt(i);
            Map<Object,Object> subMap=(Map<Object,Object>) currentMap.get(currentChar);
            if(subMap==null){
                break;
            }else {
                wordLength++;
                if(subMap.containsKey(END_FLAG)){
                    endFlag=true;
                    if(matchType==MatchType.MIN_MATCH){
                        break;
                    }else {
                        currentMap=subMap;
                    }
                }else {
                    currentMap=subMap;
                }
            }
        }
        if(!endFlag){
            wordLength=0;
        }
        return wordLength;
    }
}
