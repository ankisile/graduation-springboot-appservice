package com.oasis.springboot.common.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    public<T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data=data;
        setSuccessResponse(singleResponse);

        return singleResponse;
    }

    public<T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse listResponse = new ListResponse(dataList);
        listResponse.data = dataList;
        setSuccessResponse(listResponse);

        return listResponse;
    }

    void setSuccessResponse(CommonResponse response){
        response.setCode(200);
        response.setSuccess(true);
        response.setMessage("SUCCESS");
    }

    public CommonResponse getErrorResponse(int code, String message){
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

}

