package com.sparta.moviecomunnity.exception;

public class UserIdNotExistException extends IllegalArgumentException{
    public UserIdNotExistException(){
        super("중복된 아이디가 존재합니다.");
    }
}