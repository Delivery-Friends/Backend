package DeliveryFriends.Backend.Controller;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    Success(200, "요청에 성공하였습니다."),
    Bad_Request(400, "요청이 잘못되었습니다."),
    Unauthorized(401, "인증되지 않은 사용자입니다."),
    Not_Found(404, "찾을 수 없습니다."),


    EXISTS_EMAIL(10000, "존재하는 이메일입니다."),
    FILE_CONVERT_ERROR(10001, "파일 변환에 실패하였습니다."),
    EMPTY_JWT(10002, "JWT가 비어있습니다."),
    INVALID_JWT(10002, "JWT에 오류가 있습니다."),
    KAKAO_SERVER_ERROR(10003, "카카오 서버에 문제가 있습니다.");


    private final int statusCode;
    private final String message;

    private BaseResponseStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}