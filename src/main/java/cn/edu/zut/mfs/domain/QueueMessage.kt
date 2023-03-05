package cn.edu.zut.mfs.domain

class QueueMessage {
    private var action: String? = null//DeleteQueue
    private var queueName: String? = null
    private var autoDeleteState: Boolean? = null //是否自动删除
    private var exclusiveState: Boolean? = null //是否为排他性
    private var messageTTL: Long? = null //消息在Queue中的有效期
    private var autoExpireState: Long? = null //Queue的自动过期时间
    private var maxLength: Long? = null //Queue中消息的最大数量
    private var deadLetterExchange: String? = null //死信DLExchange
    private var deadLetterRoutingKey: String? = null //死信Routing Key
    private var maximumPriority: Int? = null //优先级功能
}