# mfs

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2023-04-15 23:29:46|2023-04-15 23:29:46|auto|@yrumily|Created by smart-doc|



## 连接控制器
## 消费控制器
## Sa-Token 账号封禁
### 
**URL:** /disable/disable.do

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|user_id|string|false|No comments found.|-|
|time|int64|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /disable/disable.do --data '{
  "user_id": "164",
  "time": 1681572587660
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 660,
  "message": "success",
  "data": "12tv2o"
}
```

### 
**URL:** /disable/untieDisable.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|user_id|string|true|No comments found.|-|

**Request-example:**
```
curl -X POST -i /disable/untieDisable.do --data 'user_id=164'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 519,
  "message": "success",
  "data": "eb5pt1"
}
```

## Sa-Token 权限
### 
**URL:** /jur/getPermission.do

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Request-example:**
```
curl -X GET -i /jur/getPermission.do
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|object|No comments found.|-|
|└─mapKey|array|A map key.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─ -|array[string]|array of string|-|

**Response-example:**
```
{
  "code": 450,
  "message": "success",
  "data": {
    "mapKey": [
      "6wzsum",
      "x1fyj4"
    ]
  }
}
```

## Sa-Token 账号注销
### 
**URL:** /kickout/logout.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|user_id|string|true|No comments found.|-|

**Request-example:**
```
curl -X POST -i /kickout/logout.do --data 'user_id=164'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 709,
  "message": "success",
  "data": "k1isfi"
}
```

### 
**URL:** /kickout/kickout.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|user_id|string|true|No comments found.|-|

**Request-example:**
```
curl -X POST -i /kickout/kickout.do --data 'user_id=164'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 32,
  "message": "success",
  "data": "9igrhk"
}
```

## Sa-Token 登录
### 
**URL:** /login/doLogin.do

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|username|string|false|No comments found.|-|
|password|string|false|No comments found.|-|
|is_lasting_cookie|boolean|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /login/doLogin.do --data '{
  "username": "nick.fisher",
  "password": "hsrtep",
  "is_lasting_cookie": true
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 236,
  "message": "success",
  "data": "m3xx5f"
}
```

### 
**URL:** /login/isLogin.do

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Request-example:**
```
curl -X GET -i /login/isLogin.do
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 265,
  "message": "success",
  "data": "q1x1h7"
}
```

### 
**URL:** /login/logout.do

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Request-example:**
```
curl -X GET -i /login/logout.do
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 905,
  "message": "success",
  "data": "mi8p89"
}
```

### 
**URL:** /login/checkLogin.do

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Request-example:**
```
curl -X GET -i /login/checkLogin.do
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 173,
  "message": "success",
  "data": "0jhvlm"
}
```

### 
**URL:** /login/getLoginIdAsString.do

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Request-example:**
```
curl -X GET -i /login/getLoginIdAsString.do
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 968,
  "message": "success",
  "data": "ojv6s3"
}
```

### 
**URL:** /login/updatePassword.do

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|username|string|false|No comments found.|-|
|password|string|false|No comments found.|-|
|is_lasting_cookie|boolean|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /login/updatePassword.do --data '{
  "username": "nick.fisher",
  "password": "shez5p",
  "is_lasting_cookie": true
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 123,
  "message": "success",
  "data": "riy1l8"
}
```

## 用户注册
### 
**URL:** /register/user.do

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
|create_time|string|false|No comments found.|-|
|last_update_time|string|false|No comments found.|-|
|note|string|false|No comments found.|-|
|deleted|boolean|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /register/user.do --data '{
  "id": "164",
  "username": "nick.fisher",
  "password": "bgaa2j",
  "nickname": "natividad.hane",
  "create_time": "2023-04-15 23:30:51",
  "last_update_time": "2023-04-15 23:30:51",
  "note": "cfjwmg",
  "deleted": true
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 295,
  "message": "success",
  "data": "c98qmc"
}
```

## 角色管理
### 
**URL:** /role/list.do

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Request-example:**
```
curl -X GET -i /role/list.do
```
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

**Response-example:**
```
{
  "code": 795,
  "message": "success",
  "data": [
    {
      "id": "164",
      "name": "nick.fisher",
      "description": "x3t8se",
      "deleted": 6
    }
  ]
}
```

### 
**URL:** /role/save.do

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

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /role/save.do --data '{
  "id": "164",
  "name": "nick.fisher",
  "description": "04kaq3",
  "deleted": 162
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 785,
  "message": "success",
  "data": "5vlnhu"
}
```

### 
**URL:** /role/update.do

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

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /role/update.do --data '{
  "id": "164",
  "name": "nick.fisher",
  "description": "q9nf74",
  "deleted": 709
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 792,
  "message": "success",
  "data": "t6sv3n"
}
```

### 
**URL:** /role/delete.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|true|No comments found.|-|

**Request-example:**
```
curl -X POST -i /role/delete.do --data 'id=164'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 478,
  "message": "success",
  "data": "7ijryp"
}
```

### 
**URL:** /role/allocPermission.do

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|role_id|string|false|No comments found.|-|
|permission_ids|array|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /role/allocPermission.do --data '{
  "role_id": "164",
  "permission_ids": [
    "aygf2s"
  ]
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 795,
  "message": "success",
  "data": "ijqucf"
}
```

### 
**URL:** /role/listPermission.do

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|role_id|string|true|No comments found.|-|

**Request-example:**
```
curl -X GET -i /role/listPermission.do?role_id=164 --data '&164'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|array|No comments found.|-|
|└─id|string|No comments found.|-|
|└─pid|string|No comments found.|-|
|└─name|string|No comments found.|-|
|└─value|string|No comments found.|-|
|└─deleted|int32|No comments found.|-|

**Response-example:**
```
{
  "code": 323,
  "message": "success",
  "data": [
    {
      "id": "164",
      "pid": "164",
      "name": "nick.fisher",
      "value": "ptp11j",
      "deleted": 693
    }
  ]
}
```

## Sa-Token 会话查询
### 
**URL:** /searchSession/list.do

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|start|int32|false|No comments found.|-|
|size|int32|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /searchSession/list.do --data '{
  "start": 879,
  "size": 10
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|array|No comments found.|-|

**Response-example:**
```
{
  "code": 844,
  "message": "success",
  "data": [
    {}
  ]
}
```

## 
## 用户管理
### 
**URL:** /user/list.do

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|keyword|string|false|No comments found.|-|
|role_id|string|false|No comments found.|-|
|page_size|int32|false|No comments found.|-|
|page_num|int32|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /user/list.do --data '{
  "keyword": "zgw938",
  "role_id": "164",
  "page_size": 10,
  "page_num": 615
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|object|No comments found.|-|

**Response-example:**
```
{
  "code": 688,
  "message": "success",
  "data": {}
}
```

### 
**URL:** /user/save.do

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
|create_time|string|false|No comments found.|-|
|last_update_time|string|false|No comments found.|-|
|note|string|false|No comments found.|-|
|deleted|boolean|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /user/save.do --data '{
  "id": "164",
  "username": "nick.fisher",
  "password": "oi3vrg",
  "nickname": "natividad.hane",
  "create_time": "2023-04-15 23:30:51",
  "last_update_time": "2023-04-15 23:30:51",
  "note": "vcxhls",
  "deleted": true
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 699,
  "message": "success",
  "data": "rk6dhe"
}
```

### 
**URL:** /user/update.do

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
|create_time|string|false|No comments found.|-|
|updater|string|false|No comments found.|-|
|last_update_time|string|false|No comments found.|-|
|login_time|string|false|No comments found.|-|
|type|boolean|false|No comments found.|-|
|note|string|false|No comments found.|-|
|deleted|boolean|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /user/update.do --data '{
  "id": "164",
  "username": "nick.fisher",
  "nickname": "natividad.hane",
  "creator": "a72shy",
  "create_time": "2023-04-15 23:30:51",
  "updater": "ly3qj0",
  "last_update_time": "2023-04-15 23:30:51",
  "login_time": "2023-04-15 23:30:51",
  "type": true,
  "note": "odqsz5",
  "deleted": true
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 12,
  "message": "success",
  "data": "2x2l8e"
}
```

### 
**URL:** /user/delete.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|id|string|true|No comments found.|-|

**Request-example:**
```
curl -X POST -i /user/delete.do --data 'id=164'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 304,
  "message": "success",
  "data": "c80zso"
}
```

### 
**URL:** /user/getUserInfoByUsername.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|username|string|true|No comments found.|-|

**Request-example:**
```
curl -X POST -i /user/getUserInfoByUsername.do --data 'username=nick.fisher'
```
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
|└─create_time|string|No comments found.|-|
|└─updater|string|No comments found.|-|
|└─last_update_time|string|No comments found.|-|
|└─login_time|string|No comments found.|-|
|└─type|boolean|No comments found.|-|
|└─note|string|No comments found.|-|
|└─deleted|boolean|No comments found.|-|

**Response-example:**
```
{
  "code": 105,
  "message": "success",
  "data": {
    "id": "164",
    "username": "nick.fisher",
    "nickname": "natividad.hane",
    "creator": "q768ap",
    "create_time": "2023-04-15 23:30:51",
    "updater": "ciw2a8",
    "last_update_time": "2023-04-15 23:30:51",
    "login_time": "2023-04-15 23:30:51",
    "type": true,
    "note": "2bj77o",
    "deleted": true
  }
}
```

### 
**URL:** /user/getUserInfoByUserId.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|user_id|string|true|No comments found.|-|

**Request-example:**
```
curl -X POST -i /user/getUserInfoByUserId.do --data 'user_id=164'
```
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
|└─create_time|string|No comments found.|-|
|└─updater|string|No comments found.|-|
|└─last_update_time|string|No comments found.|-|
|└─login_time|string|No comments found.|-|
|└─type|boolean|No comments found.|-|
|└─note|string|No comments found.|-|
|└─deleted|boolean|No comments found.|-|

**Response-example:**
```
{
  "code": 651,
  "message": "success",
  "data": {
    "id": "164",
    "username": "nick.fisher",
    "nickname": "natividad.hane",
    "creator": "180jfr",
    "create_time": "2023-04-15 23:30:51",
    "updater": "2tgkpb",
    "last_update_time": "2023-04-15 23:30:51",
    "login_time": "2023-04-15 23:30:51",
    "type": true,
    "note": "qxlmx8",
    "deleted": true
  }
}
```

### 
**URL:** /user/getLoginUserInfo.do

**Type:** GET


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Request-example:**
```
curl -X GET -i /user/getLoginUserInfo.do
```
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
|└─create_time|string|No comments found.|-|
|└─updater|string|No comments found.|-|
|└─last_update_time|string|No comments found.|-|
|└─login_time|string|No comments found.|-|
|└─type|boolean|No comments found.|-|
|└─note|string|No comments found.|-|
|└─deleted|boolean|No comments found.|-|

**Response-example:**
```
{
  "code": 567,
  "message": "success",
  "data": {
    "id": "164",
    "username": "nick.fisher",
    "nickname": "natividad.hane",
    "creator": "ttxxcu",
    "create_time": "2023-04-15 23:30:51",
    "updater": "a2f2fc",
    "last_update_time": "2023-04-15 23:30:51",
    "login_time": "2023-04-15 23:30:51",
    "type": true,
    "note": "tbb9n7",
    "deleted": true
  }
}
```

### 
**URL:** /user/getRoleListByUserId.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|user_id|string|true|No comments found.|-|

**Request-example:**
```
curl -X POST -i /user/getRoleListByUserId.do --data 'user_id=164'
```
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

**Response-example:**
```
{
  "code": 92,
  "message": "success",
  "data": [
    {
      "id": "164",
      "name": "nick.fisher",
      "description": "rrz275",
      "deleted": 681
    }
  ]
}
```

### 
**URL:** /user/saveAuthRole.do

**Type:** POST


**Content-Type:** application/json

**Description:** 

**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|user_id|string|false|No comments found.|-|
|role_ids|array|false|No comments found.|-|

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json' -i /user/saveAuthRole.do --data '{
  "user_id": "164",
  "role_ids": [
    "qdwfo7"
  ]
}'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 740,
  "message": "success",
  "data": "t76jp8"
}
```

### 
**URL:** /user/getJwt.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|jwt_id|string|false|No comments found.|-|
|issuer|string|false|No comments found.|-|
|subject|string|false|No comments found.|-|
|expires_at|object|false|No comments found.|-|
|audience|string|false|No comments found.|-|
|not_before|object|false|No comments found.|-|
|issued_at|object|false|No comments found.|-|
|authorities|array|false|No comments found.|-|

**Request-example:**
```
curl -X POST -i /user/getJwt.do --data 'jwt_id=164&issuer=18ptil&subject=08idzk&audience=6yzcbx&authorities=87gwd2,87gwd2'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 147,
  "message": "success",
  "data": "0fwsin"
}
```

### 
**URL:** /user/generateJwt.do

**Type:** POST


**Content-Type:** application/x-www-form-urlencoded;charset=UTF-8

**Description:** 

**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|jwt_id|string|false|No comments found.|-|
|issuer|string|false|No comments found.|-|
|subject|string|false|No comments found.|-|
|expires_at|object|false|No comments found.|-|
|audience|string|false|No comments found.|-|
|not_before|object|false|No comments found.|-|
|issued_at|object|false|No comments found.|-|
|authorities|array|false|No comments found.|-|

**Request-example:**
```
curl -X POST -i /user/generateJwt.do --data 'jwt_id=164&issuer=poah1d&subject=p6jy7a&audience=28bfwf&authorities=f6wv1i,f6wv1i'
```
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|int32|No comments found.|-|
|message|string|No comments found.|-|
|data|string|No comments found.|-|

**Response-example:**
```
{
  "code": 776,
  "message": "success",
  "data": "oi2e40"
}
```


