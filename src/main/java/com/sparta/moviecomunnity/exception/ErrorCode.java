package com.sparta.moviecomunnity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 200 OK : 성공한 결과 반환 */


    /* 400 BAD_REQUEST : 잘못된 요청 */

    // 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때
    INVALID_TOKEN(BAD_REQUEST, "토큰이 유효하지 않습니다."),

    // 회원가입 시 username과 password의 구성이 알맞지 않을 경우
    INVALID_INFO(BAD_REQUEST, "아이디 또는 비밀번호를 확인해주세요."),

    // 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우
    INVALID_AUTH_TOKEN(BAD_REQUEST, "작성자만 삭제/수정할 수 있습니다."),

    // 로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있다면
    MEMBER_NOT_FOUND(BAD_REQUEST, "회원을 찾을 수 없습니다."),

    // DB에 이미 존재하는 username으로 회원가입을 요청한 경우
    DUPLICATE_RESOURCE(BAD_REQUEST, "중복된 username 입니다."),
    // 리소스를 찾을 수 없는 경우
    RESOURCE_NOT_FOUND(BAD_REQUEST, "리소스를 찾을 수 없습니다."),
    // 게시글 제목에 빈 칸을 입력한 경우
    INVALID_POST_TITLE(BAD_REQUEST, "제목에 빈 칸을 입력할 수 없습니다."),
    // 게시글 내용에 빈 칸을 입력한 경우
    INVALID_POST_CONTENT(BAD_REQUEST, "내용에 빈 칸을 입력할 수 없습니다.")
    ;

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    /* 404 NOT_FOUND : 회원을 찾을 수 없음 */
    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */

    private final HttpStatus httpStatus;
    private final String detail;
}
