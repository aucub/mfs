# mfs

基于RabbitMQ的消息转发服务端.

使用RabbitMQ,RSocket进行消息转发.

## 目录

- [mfs](#mfs)
  - [目录](#目录)
  - [背景](#背景)
  - [API](#api)
  - [使用](#使用)
  - [规范](#规范)
    - [普通消息](#普通消息)
  - [额外部分](#额外部分)

## 背景

## API

## 使用

## 规范

### 普通消息

| 属性     | 符号约定 | RabbitMQ        | CloudEvents           | 样例                                 |
| -------- | -------- | --------------- | --------------------- | ------------------------------------ |
| 消息ID   | 必须     | messageId       | ID(必需)              | c9d1e5b2-35fb-4bb4-b2f8-41824f2011eb |
| 内容类型 | 推荐     | contentType     | datacontenttype(可选) | application/octet-stream             |
| 内容编码 | 推荐     | contentEncoding | contentencoding(扩展) | gzip                                 |
| 主题     | 推荐     | subject         | subject(可选)         | mynewfile.jpg                        |
| 时间     | 推荐     | creationTime    | time(可选)            | 2023-04-22T11:18:04.259599874Z       |
| 发布ID   | 必须     | publishingId    | publishingid(扩展)    | 1099415566856224768                  |
| 内容     | 必须     | data            | data(可选)            |                                      |

## 额外部分
