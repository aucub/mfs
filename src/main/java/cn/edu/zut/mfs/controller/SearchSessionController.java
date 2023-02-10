package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.config.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa-Token 会话查询示例
 */
@RestController
@RequestMapping("/search/")
public class SearchSessionController {

	/*
	 * 测试步骤：
	 	1、先登录

	 	2、根据分页参数获取会话列表
	 		http://localhost:8081/search/getList?start=0&size=10
	 */

    // 会话查询接口  ---- http://localhost:8081/search/getList?start=0&size=10
    @RequestMapping("getList")
    public BaseResponse<List> getList(int start, int size) {
        // 创建集合
        List<SaSession> sessionList = new ArrayList<>();

        // 分页查询数据
        List<String> sessionIdList = StpUtil.searchSessionId("", start, size, false);
        for (String sessionId : sessionIdList) {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            sessionList.add(session);
        }

        // 返回
        return BaseResponse.success(sessionList);
    }

}
