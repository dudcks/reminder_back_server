package com.selective.reminderproject.util;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeywordAnalyzer {

    public static Map<String, Integer> kekwords(String text) {
        // KOMORAN 초기화
        Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT); // LIGHT 모델은 속도가 빠름

        KomoranResult analyzeResultList = komoran.analyze(text);

        Map<String, Integer> keywordCounts;
        keywordCounts = new HashMap<>();
        String previousToken = null;

        List<Token> tokenList = analyzeResultList.getTokenList();
        for (Token token : tokenList) {
            if(previousToken!=null){
                String tokenString = previousToken+token.getMorph();
                // 기존에 있던지 없던지 해당 키워드의 개수를 증가시킵니다.
                keywordCounts.put(tokenString, keywordCounts.getOrDefault(tokenString, 0) + 1);
                previousToken=null;
            }
            if(token.getPos().equals("VV")||
                token.getPos().equals("VA")){
                previousToken= token.getMorph();
            }
            if(     token.getPos().equals("NNG")||
                    token.getPos().equals("NNP")||
                    token.getPos().equals("NP")||
                    token.getPos().equals("MAG")||
                    token.getPos().equals("SL")
            ) {
                    String tokenString = token.getMorph();
                    // 기존에 있던지 없던지 해당 키워드의 개수를 증가시킵니다.
                    keywordCounts.put(tokenString, keywordCounts.getOrDefault(tokenString, 0) + 1);
                }
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : keywordCounts.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            sb.append(key).append(": ").append(value).append("\n");
        }
        String result = sb.toString();
        System.out.println(result);


        return keywordCounts;
    }

    public static void main(String[] args) {
            // KOMORAN 초기화
            Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT); // LIGHT 모델은 속도가 빠름

            String text="첫번째 두번 째 다섯번째 나는 무엇을 위해 사는가?";
            KomoranResult analyzeResultList = komoran.analyze(text);

            System.out.println(analyzeResultList.getPlainText());

            Map<String, Integer> keywordCounts;
            keywordCounts = new HashMap<>();
            String previousToken = null;

            List<Token> tokenList = analyzeResultList.getTokenList();
            for (Token token : tokenList) {
                if(previousToken!=null){
                    String tokenString = previousToken+token.getMorph();
                    // 기존에 있던지 없던지 해당 키워드의 개수를 증가시킵니다.
                    keywordCounts.put(tokenString, keywordCounts.getOrDefault(tokenString, 0) + 1);
                    previousToken=null;
                }
                if(token.getPos().equals("VV")||
                        token.getPos().equals("VA")){
                    previousToken= token.getMorph();
                }
                if(     token.getPos().equals("NNG")||
                        token.getPos().equals("NNP")||
                        token.getPos().equals("NP")||
                        token.getPos().equals("MAG")||
                        token.getPos().equals("SL")
                ) {
                    String tokenString = token.getMorph();
                    // 기존에 있던지 없던지 해당 키워드의 개수를 증가시킵니다.
                    keywordCounts.put(tokenString, keywordCounts.getOrDefault(tokenString, 0) + 1);
                }
            }

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : keywordCounts.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                sb.append(key).append(": ").append(value).append("\n");
            }
            String result = sb.toString();
            System.out.println(result);
    }
}