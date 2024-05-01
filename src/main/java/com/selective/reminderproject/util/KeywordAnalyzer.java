package com.selective.reminderproject.util;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeywordAnalyzer {

    public static void main(String[] args) {
        // KOMORAN 초기화
        LocalDate today = LocalDate.now();
        System.out.println(today);
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // LIGHT 모델은 속도가 빠름
        String text = "1. 이용자가 분석할 데이터를 입력함. n2. 시스템은 입력받은 데이터를 Symbol 단위로 나누어 데이터를 분리해 리스트를 작성함. n3. 시스템은 Symbol 리스트를 이용자에게 제공함. n4 .이용자는 Symbol 리스트에 대해서 통합, 제거, Type 변경 그리고 주석 추가 기능을 이용할 수 \n" +
                "있음.만약 이용자가 Symbol을 통합하고 싶다면 S-1을 호출함. 그렇지 않고 이용자가 Symbol을 제거하고 싶다면 S-2을 호출함. 그렇지 않고 이용자가 Symbol Type을 변경하고 싶다면 S-3을 호출함. 그렇지 않고 이용자가 Symbol에 주석을 추가하고 싶다면 S-4을 호출함. n5. 만약 이용자가 추가로 통합, 제거, Type 변경 그리고 주석 추가 기능을 이용하고 싶다면 3번 항\n" +
                "목으로 이동함. n6. 이용자는 출력, 시각화 그리고 업로드 등의 기능을 이용하고 할 수 있음. 만약 이용자가 분석된 데이터를 출력하고 싶다면 Use Case 데이터 출력을 호출함. 그렇지 않고 이용자가 분석된 데이터를 시각화하고 싶다면 Use Case 데이터 시각화를 호출함. 그렇지 않고 이용자가 분석된 데이터를 업로드하고 싶다면 Use Case 데이터 업로드를 호출함. n7. 만약 이용자가 추가로 출력, 시각화 그리고 업로드 등의 기능을 이용하고 싶다면 n6으로 이동함. n8. 시스템은 데이터 분석 기능을 종료함.S-1\n" +
                "s1.1. 이용자는 통합을 원하는 Symbol을 선택함. s1.2. 이용자는 통합 후 Symbol이 가질 이름을 입력함. s1.3. 시스템은 이용자가 통합을 원하는 Symbol들의 정보를 기반으로 이용자가 정한 이름의 \n" +
                "Symbol을 정의함. s1.4. 시스템은 이용자가 통합이 완료된 Symbol들을 제거함\n" +
                "S-2\n" +
                "s2.1. 이용자는 삭제하고자 하는 Symbol을 선택함. s2.2. 이용자는 삭제하고자 하는 것이 Symbol 자체인지 또는 Symbol의 데이터인지 선택함. s2.3. 만약 Symbol 자체를 삭제하고자 한다면 해당 Symbol을 삭제하고 5번으로 이동함. s2.4. 시스템은 Symbol의 데이터 리스트를 이용자에게 제공함. s2.5. 이용자는 삭제하고자 하는 Symbol의 데이터들을 선택함. s2.6. 시스템은 이용자가 삭제하고자 하는 Symbol의 데이터들을 삭제함. S-3\n" +
                "s3.1. 이용자는 변경하고자 하는 Symbol을 선택함. s3.2. 이용자는 변경하고자 하는 Symbol의 Symbol type을 선택함. s3.3. 시스템은 이용자가 변경하고자 하는 Symbol을 이용자가 변경하고자 하는 Symbol type으\n" +
                "로 변경함. s3.4. 만약 이용자가 추가로 변경하고자 하는 경우 s3.1.로 이동함. S-4\n" +
                "s4.1. 이용자는 주석을 추가하고자 하는 Symbol을 선택함. s4.2. 이용자는 Symbol에 추가하고자 하는 주석을 입력함. s4.3. 시스템은 이용자가 추가하고자 하는 주석을 이용자가 원하는 Symbol에 입력함. s4.4. 만약 이용자가 추가로 주석을 입력하고자 하는 경우 s4.1.로 이동함.1. 이용자가 데이터 분석 후 데이터 업로드 기능을 선택함. n2. 이용자는 업로드 할 데이터의 이름을 입력함. n3. 이용자는 업로드 할 데이터의 카테고리를 지정함. n4. 시스템은 이용자에게 이름과 카테고리 확인을 요구함. n5. 이용자는 이름과 카테고리 확인함. n6. 시스템은 이용자가 분석한 데이터, 분석된 데이터의 이름 및 카테고리를 합친 정보를 외부 DB로 \n" +
                "전송함. n7. 외부 DB는 시스템으로부터 분석된 데이터를 받아 저장함. n8. 시스템은 외부 DB로의 데이터 전송이 완료되면 이용자에게 알림. n9. 시스템은 데이터 업로드 기능을 종료함4.a. 이용자가 확인한 내용이 마음에 들지 않는 경우 n2으로 이동함. 6.a. 시스템은 외부 DB로 정보 송신에 실패하면 이용자에게 오류를 표시하고 재시도할지 종료할지 \n" +
                "선택을 제공함. 7.a.1. 외부 DB는 분석된 데이터 저장에 실패하면 시스템에게 오류가 발생했다고 알림. 7.a.2. 시스템은 오류를 수신하고 이용자에게 데이터 전송을 재시도할지 종료할지 선택을 제공함. 8.a. 시스템은 외부 DB로의 데이터 전송 중 오류가 발생하면 이용자에게 오류를 표시하고 재시도할\n" +
                "지 종료할지 선택을 제공함.1. 이용자가 Symbol 리스트에서 데이터를 선택함. n2. 시스템은 이용자에게 목적 분류 카테고리를 제공함. n3. 이용자가 분포, 관계, 비교, 구성 카테고리 중 하나를 선택함. 만약 이용자가 분포 카테고리를 선택하였다면 S-1로 이동함. 그렇지 않고 이용자가 관계 카테고리를 선택하였다면 S-2로 이동함. 그렇지 않고 이용자가 비교 카테고리를 선택하였다면 S-3로 이동함. 그렇지 않고 이용자가 구성 카테고리를 선택하였다면 S-4로 이동함. n4. 이용자는 시스템이 보여준 차트 중 하나를 선택함. n5. 시스템은 이용자에게 이용자가 선택한 Symbol에 대한 선택된 차트를 제공함. n6. 만약 이용자가 시각화된 차트를 출력하고 싶다면 Use Case 데이터 출력으로 이동함. n7. 시스템은 데이터 시각화 기능을 종료함. S-1 이용자가 분포 카테고리를 선택한 경우\n" +
                "s.1.1 시스템은 분포 카테고리에 해당하는 차트를 이용자에게 보여줌. s.1.2 만약 이용자가 원하는 차트가 없다면 이용자는 n2으로 이동함. S-2 이용자가 관계 카테고리를 선택한 경우\n" +
                "s.2.1 시스템은 관계 카테고리에 해당하는 차트를 이용자에게 보여줌. s.2.2 만약 이용자가 원하는 차트가 없다면 이용자는 n2으로 이동함.S-3 이용자가 분포 카테고리를 선택한 경우\n" +
                "s.3.1 시스템은 분포 카테고리에 해당하는 차트를 이용자에게 보여줌. s.3.2 만약 이용자가 원하는 차트가 없다면 이용자는 n2으로 이동함. S-4 이용자가 비교 카테고리를 선택한 경우\n" +
                "s.4.1 시스템은 비교 카테고리에 해당하는 차트를 이용자에게 보여줌. s.4.2 만약 이용자가 원하는 차트가 없다면 이용자는 n2으로 이동함.4.a. 선택한 Symbol에 대응하는 차트가 존재하지 않는 경우 시스템은 이용자에게 알림 메시지를 띄\n" +
                "워준 후 n2으로 이동함.n1. 이용자가 데이터 출력하기를 선택함. n2. 시스템은 이용자가 출력하고자 하는 데이터가 분석된 데이터인지 시각화된 데이터인지에 따라 \n" +
                "다른 확장자를 제공함. 만약 이용자가 출력하고자 하는 데이터가 분석된 데이터라면 S-1을 호출함. 그렇지 않고 이용자가 출력하고자 하는 데이터가 시각화된 이미지라면 S-2을 호출함. n3. 이용자는 확장자 리스트 중에서 이용자가 원하는 확장자를 선택함. n4. 시스템은 이용자가 선택한 확장자로 결과를 만들어 냄. n5. 시스템은 이용자에게 만들어낸 결과를 제공함. n6. 이용자는 만들어진 결과를 다운로드할 수 있음. n7. 시스템은 데이터 출력 기능을 종료함.S-1\n" +
                "s1.1 시스템은 xl, csv, html 등의 확장자 리스트를 이용자에게 제공함. S-2\n" +
                "s2.1 시스템은 png, jpg, jpeg 등의 확장자 리스트를 이용자에게 제공함 5.a. 만약 시스템이 결과를 만드는 데 실패한다면 이용자에게 오류 메시지를 제공하고 다시 시도 \n" +
                "할지 종료할지 선택을 제공함1. 이용자가 데이터 로드 기능을 선택함. n2. 시스템은 S-1을 호출함. n3. 만약에 이용자가 로드 가능한 데이터 리스트에서 데이터를 검색하고 싶다면 Use Case 데이터 \n" +
                "검색을 호출함. n4. 이용자는 로드 가능한 데이터 리스트에서 데이터를 선택하여 시스템에 로드를 요청함. n5. 시스템은 외부 DB로 이용자가 선택한 데이터에 대한 로드 요청을 보냄. n6. 외부 DB는 시스템으로부터 요청받은 데이터를 시스템에게 전송함. n7. 시스템은 받아온 데이터를 이용자에게 제공함. n8. 만약 시스템이 데이터 시각화 기능을 이용하고 싶다면 Use Case 데이터 시각화 기능을 호출함. n9. 시스템은 데이터 로드 기능을 종료함. S-1\n" +
                "s1.1. 시스템은 외부 DB로 로드 가능한 데이터 리스트를 요청함. s1.2. 외부 DB는 로드 가능한 데이터 리스트를 작성함. s1.3. 외부 DB는 로드 가능한 데이터 리스트를 시스템에 제공함. 4.a. 시스템은 로드 요청에 실패하면 이용자에게 오류를 표시하고 재시도할지 종료할지 선택을 제공\n" +
                "함..a.1. 외부 DB는 유효하지 않은 데이터일 경우 시스템에 유효하지 않은 데이터임을 알림. 6.a.2. 시스템은 이용자에게 오류를 표시한 후 n2으로 이동할지 종료할지 선택을 제공함. 7.a. 시스템은 유효하지 않은 데이터의 경우 데이터를 삭제하고 이용자에게 오류를 표시한 후 n2으\n" +
                "로 이동할지 종료할지 선택을 제공함. 1. 이용자가 데이터 검색 기능을 선택함. n2. 이용자는 검색하고자 하는 카테고리를 선택함. n3. 이용자는 검색하고자 하는 조건에 부합하는 키워드를 입력함. n4. 시스템은 입력된 검색 조건에 부합하는 데이터를 로드 가능한 데이터 리스트에서 검색함. n5. 시스템은 검색된 데이터 목록을 이용자에게 제공함. 만약 이용자가 원하는 데이터가 존재한다면 이용자는 원하는 데이터를 선택함. 그렇지 않다면 시스템은 이용자에게 n2으로 이동할지 데이터 검색 기능을 종료할지 선택을 제\n" +
                "공함. n6. 이용자가 다른 데이터를 원하는 경우 재검색을 하거나 데이터 목록으로 이동함. 만약 재검색을 원하는 경우 n2으로 이동함. 그렇지 않고 데이터 목록으로 이동하고 싶은 경우 n5으로 이동함. n7. 데이터 검색 기능을 종료함.4.a. 만약 입력된 검색 조건에 부합하는 데이터가 존재하지 않는다면 이용자에게 알림 메시지를 제\n" +
                "공하고 n2으로 이동할지 종료할지 선택을 제공 함.";
        KomoranResult analyzeResultList = komoran.analyze(text);

        System.out.println(analyzeResultList.getPlainText());

        String filePath = "./output.txt";

        // 파일에 쓰기 위한 BufferedWriter를 초기화합니다.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 분석 결과 텍스트를 파일에 씁니다.
            writer.write(analyzeResultList.getPlainText());
            System.out.println("SUCCESS FILE WIRTE\n");
        } catch (IOException e) {
            // 파일 쓰기 중 오류가 발생한 경우 예외를 처리합니다.
     //       e.printStackTrace();
            System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRr\n");
        }

        Set<String> uniqueTokens = new HashSet<>();

        List<Token> tokenList = analyzeResultList.getTokenList();
        for (Token token : tokenList) {
            if(token.getPos().equals("NNG")||
                    token.getPos().equals("NNP")||
                    token.getPos().equals("NNB")||
                    token.getPos().equals("VA")||
                    token.getPos().equals("NP")||
                    token.getPos().equals("MAG")||
                    token.getPos().equals("VV")||
                    token.getPos().equals("SL")
            ) {
                //System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
                String tokenString = token.getMorph() + "/" + token.getPos();
                uniqueTokens.add(tokenString);
            }
        }

        for (String token : uniqueTokens) {
            System.out.println(token);
        }
        // 텍스트 분석
    }
}