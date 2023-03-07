package cn.edu.zut.mfs.domain

import lombok.Data

@Data
class ExchangeMessage {
    private var action = "CreateExchange"//DeleteExchange
    private var autoDeleteState = false
    private var exchangeName = ""
    private var exchangeType = "DIRECT"//Exchange类型。取值：DIRECT,FANOUT,TOPIC
    private var alternateExchange = ""//备份Exchange接收路由失败消息
}