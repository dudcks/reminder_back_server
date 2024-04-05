package com.selective.reminderproject.util;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import java.util.List;

public class KeywordAnalyzer {

    public static void main(String[] args) {
        // KOMORAN 초기화
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // LIGHT 모델은 속도가 빠름
        String text = "한국어는 아름다운 언어입니다. 그 풍부한 어휘와 다양한 표현은 많은 이들에게 매력을 느끼게 합니다. 한국어는 교묘하게 어미와 조사를 사용하여 다양한 뉘앙스를 표현할 수 있습니다. 또한 한국어는 문장 구조가 유연하여 다양한 문장 패턴을 가질 수 있습니다. 이 모든 특징들은 한국어를 배우는 사람들에게 흥미로운 공부 과제를 제공합니다.한국어는 아름다운 언어입니다. 그 풍부한 어휘와 다양한 표현은 많은 이들에게 매력을 느끼게 합니다. 한국어는 교묘하게 어미와 조사를 사용하여 다양한 뉘앙스를 표현할 수 있습니다. 또한 한국어는 문장 구조가 유연하여 다양한 문장 패턴을 가질 수 있습니다. 이 모든 특징들은 한국어를 배우는 사람들에게 흥미로운 공부 과제를 제공합니다.한국어는 아름다운 언어입니다. 그 풍부한 어휘와 다양한 표현은 많은 이들에게 매력을 느끼게 합니다. 한국어는 교묘하게 어미와 조사를 사용하여 다양한 뉘앙스를 표현할 수 있습니다. 또한 한국어는 문장 구조가 유연하여 다양한 문장 패턴을 가질 수 있습니다. 이 모든 특징들은 한국어를 배우는 사람들에게 흥미로운 공부 과제를 제공합니다.한국어는 아름다운 언어입니다. 그 풍부한 어휘와 다양한 표현은 많은 이들에게 매력을 느끼게 합니다. 한국어는 교묘하게 어미와 조사를 사용하여 다양한 뉘앙스를 표현할 수 있습니다. 또한 한국어는 문장 구조가 유연하여 다양한 문장 패턴을 가질 수 있습니다. 이 모든 특징들은 한국어를 배우는 사람들에게 흥미로운 공부 과제를 제공합니다.한국어는 아름다운 언어입니다. 그 풍부한 어휘와 다양한 표현은 많은 이들에게 매력을 느끼게 합니다.";


        KomoranResult analyzeResultList = komoran.analyze(text);

        System.out.println(analyzeResultList.getPlainText());

        List<Token> tokenList = analyzeResultList.getTokenList();
        for (Token token : tokenList) {
            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }
//        // 텍스트 분석
//        List<Token> tokens = komoran.analyze(text).getTokenList();
//
//        // 키워드 및 그 빈도 계산
//        HashMap<String, Integer> keywordMap = new HashMap<>();
//        for(Token token : tokens) {
//            String morph = token.getMorph();
//            keywordMap.put(morph, keywordMap.getOrDefault(morph, 0) + 1);
//        }
//
//        // 키워드 출력 (여기에는 정렬 로직 추가 필요)
//        keywordMap.forEach((key, value) -> System.out.println(key + ":" + value));
//        System.out.println("이거 왜 안되냐고 시발");
    }
}