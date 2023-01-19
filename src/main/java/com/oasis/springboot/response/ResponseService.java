package com.oasis.springboot.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    public<T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse singleResponse = new SingleResponse(data);
        setSuccessResponse(singleResponse);

        return singleResponse;
    }

    public<T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse listResponse = new ListResponse(dataList);
        setSuccessResponse(listResponse);

        return listResponse;
    }

    void setSuccessResponse(CommonResponse response){
        response.code = 200;
        response.success = true;
        response.message = "SUCCESS";
    }
}
