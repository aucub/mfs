package cn.edu.zut.mfs.domain

class BindingMessage {
    private var action = "CreateBinding"//DeleteBinding
    private var bindingKey = ""//绑定键
    private var sourceExchange = ""//源Exchange名称
    private var destinationName = ""//绑定目标名称
}