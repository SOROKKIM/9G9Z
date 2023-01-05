package com.sparta.moviecomunnity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /* 200 OK : 성공 */

    // 회원가입 성공
    SUCCESS_SIGNUP(OK, "회원가입이 완료되었습니다."),

    // 회원탈퇴 성공
    SUCCESS_UNREGISTER(OK, "회원탈퇴가 완료되었습니다."),

    // 로그인 성공
    SUCCESS_SIGNIN(OK, "로그인이 완료되었습니다."),

    // 게시글 및 댓글 작성 성공
    SUCCESS_CREATE(OK, "성공적으로 작성하였습니다."),

    // 게시글 및 댓글 수정 성공
    SUCCESS_EDIT(OK, "수정이 완료되었습니다."),

    // 게시글 및 댓글 삭제 성공
    SUCCESS_DELETE(OK, "성공적으로 삭제하였습니다."),

    // 좋아요 기능 성공
    SUCCESS_LIKE(OK,"좋아요하였습니다."),
    SUCCESS_DELETE_LIKE(OK, "좋아요를 취소하였습니다."),

    /* 400 BAD_REQUEST : 잘못된 요청 */

    // 회원가입 시, ID, PASSWORD의 요구되는 패턴에 적합하지 않았을 경우
    INVALID_ID_PATTERN(BAD_REQUEST, "아이디는 4글자 이상, 10글자 이하의 소문자와 숫자로 구성해야 합니다."),
    INVALID_PASSWORD_PATTERN(BAD_REQUEST, "비밀번호는 8글자 이상, 15글자 이하의 알파벳, 숫자, 특수문자를 각 각 하나 이상 포함하여 구성해야 합니다."),
    INVALID_ADMIN_PASSWORD(BAD_REQUEST, "관리자 가입을 위한 비밀번호가 틀렸습니다."),

    // 로그인에 실패하는 경우 (비밀번호가 맞지 않는 경우, 탈퇴한 회원으로 로그인 시도하는 경우)
    INVALID_SIGNIN_ID(BAD_REQUEST, "아이디에 빈 칸을 입력할 수 없습니다."),
    INVALID_SIGNIN_PASSWORD(BAD_REQUEST, "비밀번호에 빈 칸을 입력할 수 없습니다."),
    INVALID_PASSWORD(BAD_REQUEST, "비밀번호가 맞지 않습니다"),
    MEMBER_IS_UNREGTER(BAD_REQUEST, "이미 탈퇴한 회원입니다."),

    // 게시글 작성 시 제목에 빈 칸을 입력한 경우
    INVALID_POST_TITLE(BAD_REQUEST, "제목에 빈 칸을 입력할 수 없습니다."),
    // 게시글 / 댓글 작성 시 내용에 빈 칸을 입력한 경우
    INVALID_CONTENT(BAD_REQUEST, "내용에 빈 칸을 입력할 수 없습니다."),
    // 게시글 수정 시 제목, 내용에 모두 빈 칸을 입력한 경우
    // 댓글 수정 시 내용에 빈 칸을 입력한 경우
    INVALID_EDIT_VALUE(BAD_REQUEST, "수정할 내용이 없습니다."),

    // 삭제된 게시글 / 댓글 조회 혹은 다시 삭제 실패
    POST_IS_DELETED(NOT_FOUND, "삭제된 게시글입니다."),
    COMMENT_IS_DELETED(NOT_FOUND, "삭제된 댓글입니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    // 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "삭제/수정할 수 있는 권한이 없습니다."),
    INVALID_AUTH_TOKEN_ID(UNAUTHORIZED, "회원 탈퇴 권한이 없습니다."),

    /* 404 NOT_FOUND : 리소스를 찾을 수 없음 */
    // 로그인 시, 회원을 찾을 수 없는 경우
    MEMBER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다."),
    // 리소스를 찾을 수 없는 경우
    RESOURCE_NOT_FOUND(NOT_FOUND, "리소스를 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    // DB에 이미 존재하는 username으로 회원가입을 요청한 경우
    DUPLICATE_USER(BAD_REQUEST, "중복된 아이디의 회원입니다."),
    DUPLICATE_RESOURCE(BAD_REQUEST, "중복된 리소스입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
