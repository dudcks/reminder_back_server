package com.selective.reminderproject.util;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeywordAnalyzer {

    private static Komoran komoran = null;

    public KeywordAnalyzer(){
        komoran = new Komoran(DEFAULT_MODEL.FULL);
    }


    public static Map<String, Integer> keywords(String a) {

        String text = a;

        // KOMORAN 초기화

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
        //System.out.println(result);


        return keywordCounts;
    }

    public static void main(String[] args) {
        String text = "알고리즘 공부, 데이터 베이스, 인공지능 운영체제 과제 실행해보기 프로젝트 과제 마무리, 회의 진행하기  알고리즘 공부, 데이터 베이스, 인공지능 운영체제 과제 실행해보기 프로젝트 과제 마무리, 회의 진행하기  내일 아침 10시에 회의가 있.설정했어요. 오늘은 운동 후에 단백질 섭취를 잊지 않기 위해 알람을 설정했어요. 다가오는 세일 이벤트를 기억하기 위해 쇼핑 리스트를 작성해 두었어요. 휴대폰 충전기를 집에 가져가기 위해 메모를 남겼어요.  책상 위에 있는 식물에 물을 주 두었어요. 휴대폰 충전기를 집에 가져가기 위해 메모를 남겼어요.   내일 아침 10시에 회의가 있는데 잊지 않으려고 알람을 설정했어요. 다음 주 금요일에는 프로젝트 마감이 있어서 계획을 잘 세워야겠어요. 여행 계획을 메모해 두어서 일정을 조정할 때 참고할 수 있게 했어요 주말에 친구들과 약속이 있어서 시 간을 조율하고 있어요.  오늘은 찬혁이의 생일이에요 꼭 축하할거에요!. 알고리즘 공부, 데이터 베이스, 인공지능 운영체제 과제 실행해보기 프로젝트 과제 마무리, 회의 진행하기  알고리즘 공부, 데이터 베이스, 인공지능 운영체제 과제 실행해보기 프로젝트 과제 마무리, 회의 진행하기  내일 아침 10시에 회의가 있했어요  책상 위에 있는 식물에 물을 주는 것을 잊지 않으려고 알람을 설정했어요. 오늘은 운동 후에 단백질 섭취를 잊지 않기 위해 알람을 설정했어요. 다가오는 세일 이벤트를 기억하기 위해 쇼핑 리스트를 작성해 두었어요. 휴대폰 충전기를 집정했어요. 다가오는 세일 이벤트를 기억하기 위해 쇼핑 리스트를 작성해 두었어요. 휴대폰 충전기를 집에 가져가기 위해 메모를 남겼어요.   내일 아침 10시에 회의가 있는데 잊지 않으려고 알람을 설정했어요. 다음 주 금요일에는 프로젝트 마감워야겠어요. 여행 계획을 메모해 두어서 일정을 조정할 때 참고할 수 있게 했어요 주말에 친구들과 약속이 있어서 시 간을 조율하고 있어요.  오늘은 찬혁이의 생일이에요 꼭 축하할거에요!.";
        System.out.println(" keywords_s 실행중...\n");
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
    }
}