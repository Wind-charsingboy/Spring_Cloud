package com.example.apigateway.utils;


import com.djo.user.vo.ResultVO;

/**
 * Created by 廖师兄
 * 2017-12-10 18:03
 */
public class ResultVOUtil {

    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO error(String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg(message);
        return resultVO;
    }
}
