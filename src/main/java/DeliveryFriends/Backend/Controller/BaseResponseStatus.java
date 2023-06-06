package DeliveryFriends.Backend.Controller;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    Success(200, "요청에 성공하였습니다."),
    Bad_Request(400, "요청이 잘못되었습니다."),
    Unauthorized(401, "인증되지 않은 사용자입니다."),
    Forbidden(403, "잘못된 접근입니다."),
    Not_Found(404, "찾을 수 없습니다."),


    EXISTS_KAKAOID(10000, "존재하는 카카오ID입니다."),
    FILE_CONVERT_ERROR(10001, "파일 변환에 실패하였습니다."),
    EMPTY_JWT(10002, "JWT가 비어있습니다."),
    INVALID_JWT(10003, "JWT에 오류가 있습니다."),
    EXPIRED_JWT(10003, "JWT가 만료되었습니다."),
    KAKAO_SERVER_ERROR(10004, "카카오 서버에 문제가 있습니다."),


    CANNOT_FOUND_STORE(20000, "가게를 찾을 수 없습니다."),
    CANNOT_FOUND_MENU(20001, "메뉴를 찾을 수 없습니다."),
    CANNOT_FOUND_MENU_OPTION_GROUP(20002, "옵션 그룹을 찾을 수 없습니다."),
    CANNOT_FOUND_MENU_OPTION(20003, "옵션을 찾을 수 없습니다."),
    CANNOT_FOUND_USER(20004, "옵션을 찾을 수 없습니다."),
    CANNOT_FOUND_CART(20005, "카트를 찾을 수 없습니다."),
    CANNOT_FOUND_TEAM(20006, "팀을 찾을 수 없습니다."),
    CANNOT_FOUND_TEAM_ORDER(20007, "팀의 주문을 찾을 수 없습니다."),
    CANNOT_FOUND_USER_ORDER(20008, "유저의 주문을 찾을 수 없습니다."),

    ALREADY_WRITED_REVIEW(30000, "리뷰가 이미 작성된 주문입니다."),
    NOT_MATCH_STORE(30001, "카트가 가게와 매칭되지 않습니다."),
    ALREADY_LIKE(30002, "이미 좋아하는 상태입니다."),
    ALREADY_DISLIKE(30003, "이미 좋아하는 상태가 아닙니다."),
    ALREADY_JOIN_TEAM(30004, "이미 가입한 팀이 있습니다."),
    ALREADY_PROGRESS_TEAM(30005, "이미 진행 중인 팀이 있습니다."),

    NOT_LEADER(40000, "리더가 아닙니다."),
    CART_NOT_SETTING(40001, "카트가 정해지지 않았습니다."),
    ORDER_PROGRESS_ERROR(40002, "주문 진행에 오류가 발생했습니다."),
    PROGRESS_CART(40003, "주문 진행중인 카트입니다."),

    NICKNAME_ENTER(50000, "닉네임을 입력해주세요."),
    NICKNAME_LENGTH_MIN_INSUFFICIENT(50001, "닉네임 길이는 2자 이상입니다."),
    NICKNAME_LENGTH_MAX_INSUFFICIENT(50002, "닉네임 길이는 9자 이하입니다."),
    NICKNAME_NOT_ALLOW_CHARACTER(50002, "허용되지 않은 문자가 있습니다."),
    NICKNAME_ALREADY_USE(50002, "중복된 닉네임이 존재합니다.");


    private final int statusCode;
    private final String message;

    private BaseResponseStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}