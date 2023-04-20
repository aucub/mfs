# mfs

| Version              | Update Time         | Status | Author   | Description          |
|----------------------|---------------------|--------|----------|----------------------|
| v2023-04-20 17:15:25 | 2023-04-20 17:15:25 | auto   | @yrumily | Created by smart-doc |



## 连接控制器
## 消费控制器
## Sa-Token 账号封禁
### 
**URL:** /disable/disable

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|false|No comments found.|-|
|time|int64|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /disable/untieDisable

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


## Sa-Token 账号注销
### 
**URL:** /kickout/logout

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


## Sa-Token 登录
### 
**URL:** /login/doLogin

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|username|string|false|No comments found.|-|
|password|string|false|No comments found.|-|
|isLastingCookie|boolean|false|No comments found.|-|

**Response-fields:**

| Field        | Type   | Description        | Since |
|--------------|--------|--------------------|-------|
| code         | int32  | No comments found. | -     |
| message      | string | No comments found. | -     |
| data         | object | No comments found. | -     |
| └─any object | object | any object.        | -     |


### 
**URL:** /login/isLogin

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /login/logout

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /login/checkLogin

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /login/getLoginIdAsString

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /login/updatePassword

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|username|string|false|No comments found.|-|
|password|string|false|No comments found.|-|
|isLastingCookie|boolean|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


## 用户注册
### 
**URL:** /register/user

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|false|ID|-|
|username|string|false|用户名|-|
|password|string|false|No comments found.|-|
|nickname|string|false|用户昵称|-|
|createTime|string|false|No comments found.|-|
|lastUpdateTime|string|false|No comments found.|-|
|note|string|false|No comments found.|-|
|deleted|boolean|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


## 角色管理
### 
**URL:** /role/list

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|array|No comments found.|-|
|└─id|string|No comments found.|-|
|└─name|string|No comments found.|-|
|└─description|string|No comments found.|-|
|└─deleted|int32|No comments found.|-|


### 
**URL:** /role/save

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|false|No comments found.|-|
|name|string|false|No comments found.|-|
|description|string|false|No comments found.|-|
|deleted|int32|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /role/update

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|false|No comments found.|-|
|name|string|false|No comments found.|-|
|description|string|false|No comments found.|-|
|deleted|int32|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /role/delete

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|true|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

## Sa-Token 会话查询
### 

**URL:** /searchSession/list

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type  | Required | Description        | Since |
|-----------|-------|----------|--------------------|-------|
| start     | int32 | false    | No comments found. | -     |
| size      | int32 | false    | No comments found. | -     |

**Response-fields:**

| Field   | Type   | Description        | Since |
|---------|--------|--------------------|-------|
| code    | int32  | No comments found. | -     |
| message | string | No comments found. | -     |
| data    | array  | No comments found. | -     |


### 

**URL:** /searchSession/onlineList

**Type:** POST

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Response-fields:**

| Field          | Type    | Description        | Since |
|----------------|---------|--------------------|-------|
| id             | string  | ID                 | -     |
| username       | string  | 用户名                | -     |
| nickname       | string  | 用户昵称               | -     |
| creator        | string  | No comments found. | -     |
| createTime     | string  | No comments found. | -     |
| updater        | string  | No comments found. | -     |
| lastUpdateTime | string  | No comments found. | -     |
| loginTime      | string  | No comments found. | -     |
| type           | boolean | No comments found. | -     |
| note           | string  | No comments found. | -     |
| deleted        | boolean | No comments found. | -     |


### 

**URL:** /searchSession/onlineUsers

**Type:** POST

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:**

## 
## 用户管理
### 
**URL:** /user/list

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|keyword|string|false|No comments found.|-|
|roleId|string|false|No comments found.|-|
|pageSize|int32|false|No comments found.|-|
|pageNum|int32|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|object|No comments found.|-|


### 
**URL:** /user/save

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|false|ID|-|
|username|string|false|用户名|-|
|password|string|false|No comments found.|-|
|nickname|string|false|用户昵称|-|
|createTime|string|false|No comments found.|-|
|lastUpdateTime|string|false|No comments found.|-|
|note|string|false|No comments found.|-|
|deleted|boolean|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /user/update

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|false|ID|-|
|username|string|false|用户名|-|
|nickname|string|false|用户昵称|-|
|creator|string|false|No comments found.|-|
|createTime|string|false|No comments found.|-|
|updater|string|false|No comments found.|-|
|lastUpdateTime|string|false|No comments found.|-|
|loginTime|string|false|No comments found.|-|
|type|boolean|false|No comments found.|-|
|note|string|false|No comments found.|-|
|deleted|boolean|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /user/delete

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|true|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /user/getUserInfoByUsername

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|username|string|true|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|object|No comments found.|-|
|└─id|string|ID|-|
|└─username|string|用户名|-|
|└─nickname|string|用户昵称|-|
|└─creator|string|No comments found.|-|
|└─createTime|string|No comments found.|-|
|└─updater|string|No comments found.|-|
|└─lastUpdateTime|string|No comments found.|-|
|└─loginTime|string|No comments found.|-|
|└─type|boolean|No comments found.|-|
|└─note|string|No comments found.|-|
|└─deleted|boolean|No comments found.|-|


### 
**URL:** /user/getUserInfoByUserId

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|object|No comments found.|-|
|└─id|string|ID|-|
|└─username|string|用户名|-|
|└─nickname|string|用户昵称|-|
|└─creator|string|No comments found.|-|
|└─createTime|string|No comments found.|-|
|└─updater|string|No comments found.|-|
|└─lastUpdateTime|string|No comments found.|-|
|└─loginTime|string|No comments found.|-|
|└─type|boolean|No comments found.|-|
|└─note|string|No comments found.|-|
|└─deleted|boolean|No comments found.|-|


### 
**URL:** /user/getLoginUserInfo

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|object|No comments found.|-|
|└─id|string|ID|-|
|└─username|string|用户名|-|
|└─nickname|string|用户昵称|-|
|└─creator|string|No comments found.|-|
|└─createTime|string|No comments found.|-|
|└─updater|string|No comments found.|-|
|└─lastUpdateTime|string|No comments found.|-|
|└─loginTime|string|No comments found.|-|
|└─type|boolean|No comments found.|-|
|└─note|string|No comments found.|-|
|└─deleted|boolean|No comments found.|-|


### 
**URL:** /user/getRoleListByUserId

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|true|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|array|No comments found.|-|
|└─id|string|No comments found.|-|
|└─name|string|No comments found.|-|
|└─description|string|No comments found.|-|
|└─deleted|int32|No comments found.|-|


### 
**URL:** /user/saveAuthRole

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|userId|string|false|No comments found.|-|
|roleIds|array|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /user/getJwt

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|jwtId|string|false|No comments found.|-|
|issuer|string|false|No comments found.|-|
|subject|string|false|No comments found.|-|
|expiresAt|object|false|No comments found.|-|
|audience|string|false|No comments found.|-|
|authorities|array|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|


### 
**URL:** /user/generateJwt

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter   | Type   | Required | Description        | Since |
|-------------|--------|----------|--------------------|-------|
| jwtId       | string | false    | No comments found. | -     |
| issuer      | string | false    | No comments found. | -     |
| subject     | string | false    | No comments found. | -     |
| expiresAt   | object | false    | No comments found. | -     |
| audience    | string | false    | No comments found. | -     |
| authorities | array  | false    | No comments found. | -     |

**Response-fields:**

| Field   | Type   | Description        | Since |
|---------|--------|--------------------|-------|
| code    | int32  | No comments found. | -     |
| message | string | No comments found. | -     |
| data    | string | No comments found. | -     |

##  

###  

**URL:** /push

**Type:** POST

**Content-Type:** application/json

**Description:**

**Body-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| userId    | string | false    | No comments found. | -     |
| route     | string | false    | No comments found. | -     |
| body      | array  | false    | No comments found. | -     |




