package cn.edu.zut.mfs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * broker管理
 */
@Slf4j
@RestController
@RequestMapping("/broker/")
@Tag(name = "broker管理")
public class RabbitController {
}
