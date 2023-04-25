# mfs

| Version              | Update Time         | Status | Author   | Description          |
|----------------------|---------------------|--------|----------|----------------------|
| v2023-04-25 17:00:55 | 2023-04-25 17:00:55 | auto   | @yrumily | Created by smart-doc |

##  

##  

## 登录

###  

**URL:** /login/doLogin

**Type:** POST

**Content-Type:** application/json

**Description:**

**Request-headers:**

| Header          | Type   | Required | Description        | Since |
|-----------------|--------|----------|--------------------|-------|
| X-Forwarded-For | string | true     | No comments found. | -     |


**Body-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| username  | string | false    | No comments found. | -     |
| password  | string | false    | No comments found. | -     |

**Response-fields:**

| Field        | Type   | Description        | Since |
|--------------|--------|--------------------|-------|
| code         | int32  | No comments found. | -     |
| message      | string | No comments found. | -     |
| data         | object | No comments found. | -     |
| └─any object | object | any object.        | -     |


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

| Parameter   | Type   | Required | Description        | Since |
|-------------|--------|----------|--------------------|-------|
| username    | string | false    | No comments found. | -     |
| oldPassword | string | false    | No comments found. | -     |
| newPassword | string | false    | No comments found. | -     |

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

**Type:** GET


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

**Type:** GET


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

**Type:** GET


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

**Type:** GET


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
**URL:** /user/generateJwt

**Type:** POST

**Content-Type:** application/json

**Description:**

**Body-parameters:**

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

###  

**URL:** /user/connectList

**Type:** POST

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:**

**Response-fields:**

| Field            | Type    | Description        | Since |
|------------------|---------|--------------------|-------|
| code             | int32   | No comments found. | -     |
| message          | string  | No comments found. | -     |
| data             | array   | No comments found. | -     |
| └─id             | string  | ID                 | -     |
| └─username       | string  | 用户名                | -     |
| └─nickname       | string  | 用户昵称               | -     |
| └─creator        | string  | No comments found. | -     |
| └─createTime     | string  | No comments found. | -     |
| └─updater        | string  | No comments found. | -     |
| └─lastUpdateTime | string  | No comments found. | -     |
| └─loginTime      | string  | No comments found. | -     |
| └─type           | boolean | No comments found. | -     |
| └─note           | string  | No comments found. | -     |
| └─deleted        | boolean | No comments found. | -     |

###  

**URL:** /user/getUserLoginLogList

**Type:** POST

**Content-Type:** application/json

**Description:**

**Body-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| keyword   | string | false    | No comments found. | -     |
| pageSize  | int32  | false    | No comments found. | -     |
| pageNum   | int32  | false    | No comments found. | -     |

**Response-fields:**

| Field   | Type   | Description        | Since |
|---------|--------|--------------------|-------|
| code    | int32  | No comments found. | -     |
| message | string | No comments found. | -     |
| data    | object | No comments found. | -     |

###  

**URL:** /user/getLinkLogList

**Type:** POST

**Content-Type:** application/json

**Description:**

**Body-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| keyword   | string | false    | No comments found. | -     |
| pageSize  | int32  | false    | No comments found. | -     |
| pageNum   | int32  | false    | No comments found. | -     |

**Response-fields:**

| Field   | Type   | Description        | Since |
|---------|--------|--------------------|-------|
| code    | int32  | No comments found. | -     |
| message | string | No comments found. | -     |
| data    | object | No comments found. | -     |

##  

### 推

**URL:** /push

**Type:** POST

**Content-Type:** application/json

**Description:** 推

**Body-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| userId    | string | false    | No comments found. | -     |
| route     | string | false    | No comments found. | -     |
| body      | array  | false    | No comments found. | -     |

##  

###  

**URL:** /query/queryPublish

**Type:** POST

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:**

**Query-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| start     | string | true     | No comments found. | -     |
| stop      | string | true     | No comments found. | -     |

**Response-fields:**

| Field           | Type    | Description        | Since |
|-----------------|---------|--------------------|-------|
| messageId       | string  | No comments found. | -     |
| source          | string  | No comments found. | -     |
| type            | string  | No comments found. | -     |
| appId           | string  | No comments found. | -     |
| userId          | string  | No comments found. | -     |
| priority        | int32   | No comments found. | -     |
| expiration      | string  | No comments found. | -     |
| delay           | int32   | No comments found. | -     |
| publishingId    | int64   | No comments found. | -     |
| dataContentType | string  | No comments found. | -     |
| contentEncoding | string  | No comments found. | -     |
| subject         | string  | No comments found. | -     |
| body            | string  | No comments found. | -     |
| submit          | boolean | No comments found. | -     |
| time            | object  | No comments found. | -     |

###  

**URL:** /query/queryConsume

**Type:** POST

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:**

**Query-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| start     | string | true     | No comments found. | -     |
| stop      | string | true     | No comments found. | -     |

**Response-fields:**

| Field        | Type   | Description        | Since |
|--------------|--------|--------------------|-------|
| messageId    | string | No comments found. | -     |
| publishingId | int64  | No comments found. | -     |
| offset       | int64  | No comments found. | -     |
| queue        | string | No comments found. | -     |
| userId       | string | No comments found. | -     |
| time         | object | No comments found. | -     |

###  

**URL:** /query/queryPush

**Type:** POST

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:**

**Query-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| start     | string | true     | No comments found. | -     |
| stop      | string | true     | No comments found. | -     |

**Response-fields:**

| Field  | Type   | Description        | Since |
|--------|--------|--------------------|-------|
| userId | string | No comments found. | -     |
| route  | string | No comments found. | -     |
| body   | array  | No comments found. | -     |

###  

**URL:** /query/search

**Type:** POST

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:**

**Query-parameters:**

| Parameter | Type   | Required | Description        | Since |
|-----------|--------|----------|--------------------|-------|
| uid       | string | false    | No comments found. | -     |
| keyword   | string | false    | No comments found. | -     |
| offset    | int32  | false    | No comments found. | -     |

##  

### 在线列表

**URL:** /searchOnline/onlineList

**Type:** GET

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 在线列表

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

### 在线用户
**URL:** /searchOnline/onlineUsers

**Type:** GET

**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 在线用户




