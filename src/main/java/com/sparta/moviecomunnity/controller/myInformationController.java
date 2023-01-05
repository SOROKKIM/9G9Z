package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import com.sparta.moviecomunnity.service.MyInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;


@RestController
@RequiredArgsConstructor
public class myInformationController {

    private final MyInformationService myInformationService;

    @PostMapping("/my-information/unregister")
    public ResponseEntity<ServerResponse> unregister(@RequestParam String unregisterUserName, @AuthenticationPrincipal UserDetailsImpl userDetails){

        String username = userDetails.getUsername();

        UserRoleEnum role = userDetails.getUser().getRole();

        if(role.equals(UserRoleEnum.ADMIN)){
            myInformationService.unregister(unregisterUserName);
        }else {
            if(!unregisterUserName.equals(username)){
                throw new CustomException(INVALID_AUTH_TOKEN_ID); //에러 "자신의 아이디만 삭제할 수 있습니다."
            }else {
                myInformationService.unregister(username);
            }
        }
        return ServerResponse.toResponseEntity(SUCCESS_UNREGISTER);
    }

//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("admin/unregister")
//    public ResponseEntity<ServerResponse> adminUnregister(@RequestParam String unregisterUserName, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        myInformationService.unregister(unregisterUserName);
//        return ServerResponse.toResponseEntity(SUCCESS_UNREGISTER);
//    }
//
//    @PostMapping("/my-information/unregister2")
//    public ResponseEntity<ServerResponse> userUnregister(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        String username = userDetails.getUsername();
//        myInformationService.unregister(username);
//        return ServerResponse.toResponseEntity(SUCCESS_UNREGISTER);
//    }
//




}
