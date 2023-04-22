# mfs

基于RabbitMQ的消息转发服务端.

使用RabbitMQ,RSocket进行消息转发.

## 目录

- [mfs](#mfs)
  - [目录](#目录)
  - [背景](#背景)
  - [API](#api)
    - [连接](#连接)
    - [生产](#生产)
    - [生产-经典](#生产-经典)
    - [推送](#推送)
    - [消费](#消费)
    - [消费-经典](#消费-经典)
  - [使用](#使用)
  - [规范](#规范)
    - [普通消息](#普通消息)
    - [经典消息](#经典消息)
    - [普通消息-消费](#普通消息-消费)
    - [经典消息-消费](#经典消息-消费)
    - [推送消息](#推送消息)
    - [身份验证-元数据](#身份验证-元数据)
    - [MetadataHeader-元数据](#metadataheader-元数据)
  - [额外部分](#额外部分)

## 背景

## API

### 连接

**URL:** /connect

**Type:** connectTcp

**MimeType:** application/cloudevents+json

**Description:** 

**Body-parameters:**

| Parameter    | Type                                       | Required |
| ------------ | ------------------------------------------ | -------- |
| tokenValue   | message/x.rsocket.authentication.bearer.v0 | 必须     |
| cloudEventV1 | CloudEventV1                               | 可以     |

### 生产

**URL:** /publish

**Type:** Channel

**MimeType:** application/cloudevents+json

**Description:** 

**Body-parameters:**

| Parameter      | Type                                       | Required |
| -------------- | ------------------------------------------ | -------- |
| tokenValue     | message/x.rsocket.authentication.bearer.v0 | 必须     |
| metadataHeader | application/x.metadataHeader+json          | 必须     |
| cloudEventFlux | Flux\<CloudEvent\>                         | 必须     |

**Response-fields:**

| Field     | Type           | Required | Description      |
| --------- | -------------- | -------- | ---------------- |
| messageId | Flux\<String\> | 推荐     | RabbitMQ确认返回 |

### 生产-经典

**URL:** /publishClassic,/publishTask,/publishBatch

**Type:** Channel

**MimeType:** application/cloudevents+json

**Description:** 

**Body-parameters:**

| Parameter      | Type                                       | Required |
| -------------- | ------------------------------------------ | -------- |
| tokenValue     | message/x.rsocket.authentication.bearer.v0 | 必须     |
| metadataHeader | application/x.metadataHeader+json          | 必须     |
| cloudEventFlux | Flux\<CloudEvent\>                         | 必须     |

**Response-fields:**

| Field | Type           | Required | Description |
| ----- | -------------- | -------- | ----------- |
| OK    | Flux\<String\> | 可以     | 定时返回    |

### 推送

**URL:** /push

**Type:** HTTP Request/Response

**MimeType:** None

**Description:** 

**Body-parameters:**

| Parameter   | Type        | Required |
| ----------- | ----------- | -------- |
| pushMessage | PushMessage | 必须     |

**Response-fields:**

| Field  | Type    | Required | Description |
| ------ | ------- | -------- | ----------- |
| result | Boolean | 可以     | 返回        |

### 消费

**URL:** /consume

**Type:** Request/Stream

**MimeType:** application/json

**Description:** 

**Body-parameters:**

| Parameter  | Type                                       | Required |
| ---------- | ------------------------------------------ | -------- |
| tokenValue | message/x.rsocket.authentication.bearer.v0 | 必须     |
| consume    | Consume                                    | 必须     |

**Response-fields:**

| Field  | Type                 | Required | Description   |
| ------ | -------------------- | -------- | ------------- |
| result | Flux\<CloudEventV1\> | 必须     | 普通消息-消费 |

### 消费-经典

**URL:** /consumeBatch

**Type:** Request/Stream

**MimeType:** application/json

**Description:** 

**Body-parameters:**

| Parameter  | Type                                       | Required |
| ---------- | ------------------------------------------ | -------- |
| tokenValue | message/x.rsocket.authentication.bearer.v0 | 必须     |
| consume    | Consume                                    | 必须     |

**Response-fields:**

| Field  | Type                 | Required | Description   |
| ------ | -------------------- | -------- | ------------- |
| result | Flux\<CloudEventV1\> | 必须     | 经典消息-消费 |

## 使用

## 规范

### 普通消息

| 属性     | 符号约定 | RabbitMQ        | CloudEvents           | 样例                                 |
| -------- | -------- | --------------- | --------------------- | ------------------------------------ |
| 消息ID   | 必须     | messageId       | id(必需)              | c9d1e5b2-35fb-4bb4-b2f8-41824f2011eb |
| 内容类型 | 推荐     | contentType     | datacontenttype(可选) | application/octet-stream             |
| 内容编码 | 推荐     | contentEncoding | contentencoding(扩展) | gzip                                 |
| 主题     | 推荐     | subject         | subject(可选)         | mynewfile.jpg                        |
| 时间     | 推荐     | creationTime    | time(可选)            | 2023-04-22T11:42:45.827747033Z       |
| 发布ID   | 必须     | publishingId    | publishingid(扩展)    | 1099415566856224768                  |
| 内容     | 必须     | data            | data(可选)            |                                      |

### 经典消息

| 属性     | 符号约定 | RabbitMQ        | CloudEvents           | 样例                                 |
| -------- | -------- | --------------- | --------------------- | ------------------------------------ |
| 消息ID   | 必须     | messageId       | id(必需)              | c9d1e5b2-35fb-4bb4-b2f8-41824f2011eb |
| 内容类型 | 推荐     | contentType     | datacontenttype(可选) | application/octet-stream             |
| 类型     | 推荐     | type            | type(必需)            | com.example.object.deleted.v2        |
| 内容编码 | 推荐     | contentEncoding | contentencoding(扩展) | gzip                                 |
| 时间     | 推荐     | timestamp       | time(可选)            | 2023-04-22T11:42:45.827747033Z       |
| 应用ID   | 推荐     | appId           | appid(扩展)           | mfs                                  |
| 内容     | 必须     | data            | data(可选)            |                                      |
| 优先级   | 可选     | priority        | priority(扩展)        | 10                                   |
| 过期     | 可选     | expiration      | expiration(扩展)      | 60000(毫秒)                          |
| 延时     | 可选     | receivedDelay   | delay(扩展)           | 5000(毫秒)                           |

### 普通消息-消费

| 属性   | RabbitMQ     | CloudEvents        | 样例                                 |
| ------ | ------------ | ------------------ | ------------------------------------ |
| 消息ID | messageId    | id(必需)           | c9d1e5b2-35fb-4bb4-b2f8-41824f2011eb |
| 时间   | creationTime | time(可选)         | 2023-04-22T11:42:45.827747033Z       |
| 内容   | data         | data(可选)         |                                      |
| 发布ID | publishingId | publishingid(扩展) | 1099415566856224768                  |
| 偏移量 | offset       | offset(扩展)       | 10                                   |

### 经典消息-消费

| 属性     | 符号约定 | RabbitMQ        | CloudEvents           | 样例                                 |
| -------- | -------- | --------------- | --------------------- | ------------------------------------ |
| 消息ID   | 必须     | messageId       | id(必需)              | c9d1e5b2-35fb-4bb4-b2f8-41824f2011eb |
| 内容类型 | 可选     | contentType     | datacontenttype(可选) | application/octet-stream             |
| 来源     | 可以     | source          | source(必需)          | /sensors/tn-1234567/alerts           |
| 主题     | 可以     | subject         | subject(可选)         | mynewfile.jpg                        |
| 类型     | 推荐     | type            | type(必需)            | com.example.object.deleted.v2        |
| 内容编码 | 可选     | contentEncoding | contentencoding(扩展) | gzip                                 |
| 时间     | 推荐     | timestamp       | time(可选)            | 2023-04-22T11:42:45.827747033Z       |
| 应用ID   | 推荐     | appId           | appid(扩展)           | mfs                                  |
| 内容     | 必须     | data            | data(可选)            |                                      |
| 优先级   | 可以     | priority        | priority(扩展)        | 10                                   |
| 过期     | 可以     | expiration      | expiration(扩展)      | 60000(毫秒)                          |
| 延时     | 可以     | receivedDelay   | delay(扩展)           | 5000(毫秒)                           |

### 推送消息

| 属性   | 符号约定 | PushMessage | 样例                             |
| ------ | -------- | ----------- | -------------------------------- |
| 用户ID | 必须     | userId      | 0c59989d3970380ae168880686c4a070 |
| 路由   | 必须     | route       | status                           |
| 内容   | 必须     | body        |                                  |


### 身份验证-元数据

| 属性       | 符号约定 | MimeType                                   | 样例                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| ---------- | -------- | ------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| tokenValue | 必须     | message/x.rsocket.authentication.bearer.v0 | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJkMWM1ZDI3OS1kMTk4LTRiMTYtYjEzMy1mYzE5ODhjNWJjYzUiLCJpc3MiOiIwYzU5OTg5ZDM5NzAzODBhZTE2ODg4MDY4NmM0YTA3MCIsInN1YiI6IjBjNTk5ODlkMzk3MDM4MGFlMTY4ODgwNjg2YzRhMDcwIiwiZXhwIjoxNzgyMDE2OTEyLCJhdWQiOiJtZnMiLCJzY29wZSI6WyJ1c2VyTWFuIiwiZ2V0Snd0IiwiZ2VuZXJhdGVKd3QiLCJzZWFyY2hTZXNzaW9uIiwicm9sZSIsImtpY2tvdXQiLCJkaXNhYmxlIiwiY29ubmVjdCIsInB1c2giLCJwdWJsaXNoIiwiY29uc3VtZSJdfQ.8wHE60sj9wYkZ_aejpgIpssi6-S034td3GjnF7qW2Sw |

### MetadataHeader-元数据

> MimeType:application/x.metadataHeader+json

| 属性   | 符号约定 | MetadataHeader | RabbitMQ           | 样例       |
| ------ | -------- | -------------- | ------------------ | ---------- |
| 交换机 | 可选     | exchange       | receivedExchange   | amq.direct |
| 路由键 | 必须     | routingKey     | receivedRoutingKey | hello      |
| 批大小 | 可选     | batchSize      |                    | 10         |

## 额外部分
