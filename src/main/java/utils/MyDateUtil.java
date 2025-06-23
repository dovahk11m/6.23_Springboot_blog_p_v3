package utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.util.Date;

/* 날짜와 시간 관련 유틸리티 클래스
static 메서드로 구현해서 객체 생성 없이 바로 사용이 가능하도록 설계한다
 */
public class MyDateUtil {

    public static String timestampFormat(Timestamp time) {

        /*Board 엔티티에 선언된 Timestamp를 포매팅
        Date 객체로 변환한다
        getTim() 메서드를 호출해서
        밀리초 단위로 시간을 받아
        Date 객체를 생성한다
        */
        Date currentDate = new Date(time.getTime());

        //아파치 Commons 라이브러리 활용
        return DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm");
    }

}//
