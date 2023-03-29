package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.dto.SearchSessionDto;
import cn.edu.zut.mfs.pojo.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa-Token 会话查询
 */
@Tag(name = "会话查询")
@Slf4j
@RestController
@RequestMapping("/searchSession/")
public class SearchSessionController {

    @Operation(summary = "会话查询接口----根据分页参数获取会话列表")
    @SaCheckPermission("searchSession:getList")
    @PostMapping("getList")
    public BaseResponse<List<SaSession>> getList(@RequestBody SearchSessionDto searchSessionDto) {
        // 创建集合
        List<SaSession> sessionList = new ArrayList<>();
        // 分页查询数据
        List<String> sessionIdList = StpUtil.searchSessionId("", searchSessionDto.getStart(), searchSessionDto.getSize(), false);
        for (String sessionId : sessionIdList) {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            sessionList.add(session);
        }
        // 返回
        return BaseResponse.success(sessionList);
    }

}
