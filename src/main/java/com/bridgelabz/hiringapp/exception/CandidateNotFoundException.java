package com.bridgelabz.hiringapp.exception;

public class CandidateNotFoundException extends RuntimeException{

    private Long id;

    public CandidateNotFoundException(String msg){
        super(msg);
        this.id = id;
    }
    public Long candidateId(){
        return id;
    }

}
