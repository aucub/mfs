# mfs

| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|v2023-04-15 15:38:35|2023-04-15 15:38:35|auto|@yrumily|Created by smart-doc|



## 
## 
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
  "user_id": "192",
  "time": 1681544714656
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
  "code": 679,
  "message": "success",
  "data": "bsl0it"
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
curl -X POST -i /disable/untieDisable.do --data 'user_id=192'
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
  "code": 238,
  "message": "success",
  "data": "83no97"
}
```

## Sa-Token 权限认证
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
  "code": 393,
  "message": "success",
  "data": {
    "mapKey": [
      "7n4wgn",
      "azmxyu"
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
curl -X POST -i /kickout/logout.do --data 'user_id=192'
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
  "code": 451,
  "message": "success",
  "data": "r2pwqx"
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
curl -X POST -i /kickout/kickout.do --data 'user_id=192'
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
  "code": 421,
  "message": "success",
  "data": "ema1i4"
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
  "username": "marilou.lubowitz",
  "password": "w1ynir",
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
  "code": 723,
  "message": "success",
  "data": "0m2z2z"
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
  "code": 416,
  "message": "success",
  "data": "bnxcgb"
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
  "code": 659,
  "message": "success",
  "data": "jug86g"
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
  "code": 99,
  "message": "success",
  "data": "oa1l59"
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
  "code": 367,
  "message": "success",
  "data": "hhw0ud"
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
  "username": "marilou.lubowitz",
  "password": "zdarp3",
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
  "code": 536,
  "message": "success",
  "data": "36nt9r"
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
  "id": "192",
  "username": "marilou.lubowitz",
  "password": "tumqar",
  "nickname": "hoyt.schimmel",
  "create_time": "2023-04-15 15:45:14",
  "last_update_time": "2023-04-15 15:45:14",
  "note": "m2p8nd",
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
  "code": 306,
  "message": "success",
  "data": "dy9s0g"
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
  "code": 221,
  "message": "success",
  "data": [
    {
      "id": "192",
      "name": "marilou.lubowitz",
      "description": "6jjk1a",
      "deleted": 609
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
  "id": "192",
  "name": "marilou.lubowitz",
  "description": "ok9ngt",
  "deleted": 949
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
  "code": 594,
  "message": "success",
  "data": "7xjgyi"
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
  "id": "192",
  "name": "marilou.lubowitz",
  "description": "qultum",
  "deleted": 177
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
  "code": 662,
  "message": "success",
  "data": "zvbx3f"
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
curl -X POST -i /role/delete.do --data 'id=192'
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
  "code": 237,
  "message": "success",
  "data": "3bl1lo"
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
  "role_id": "192",
  "permission_ids": [
    "pjuhuc"
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
  "code": 62,
  "message": "success",
  "data": "30jhnu"
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
curl -X GET -i /role/listPermission.do?role_id=192 --data '&192'
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
  "code": 307,
  "message": "success",
  "data": [
    {
      "id": "192",
      "pid": "192",
      "name": "marilou.lubowitz",
      "value": "3pnrnn",
      "deleted": 626
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
  "start": 487,
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
  "code": 829,
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
  "keyword": "bww0vc",
  "role_id": "192",
  "page_size": 10,
  "page_num": 337
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
  "code": 379,
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
  "id": "192",
  "username": "marilou.lubowitz",
  "password": "0v4du0",
  "nickname": "hoyt.schimmel",
  "create_time": "2023-04-15 15:45:14",
  "last_update_time": "2023-04-15 15:45:14",
  "note": "9zzhsm",
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
  "code": 713,
  "message": "success",
  "data": "oq9yhq"
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
  "id": "192",
  "username": "marilou.lubowitz",
  "nickname": "hoyt.schimmel",
  "creator": "i98wii",
  "create_time": "2023-04-15 15:45:14",
  "updater": "va9q1n",
  "last_update_time": "2023-04-15 15:45:14",
  "login_time": "2023-04-15 15:45:14",
  "type": true,
  "note": "54vnzt",
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
  "code": 815,
  "message": "success",
  "data": "o131d4"
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
curl -X POST -i /user/delete.do --data 'id=192'
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
  "code": 72,
  "message": "success",
  "data": "3e70xd"
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
curl -X POST -i /user/getUserInfoByUsername.do --data 'username=marilou.lubowitz'
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
  "code": 801,
  "message": "success",
  "data": {
    "id": "192",
    "username": "marilou.lubowitz",
    "nickname": "hoyt.schimmel",
    "creator": "16oe8e",
    "create_time": "2023-04-15 15:45:14",
    "updater": "u3rvwx",
    "last_update_time": "2023-04-15 15:45:14",
    "login_time": "2023-04-15 15:45:14",
    "type": true,
    "note": "xi81k9",
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
curl -X POST -i /user/getUserInfoByUserId.do --data 'user_id=192'
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
  "code": 564,
  "message": "success",
  "data": {
    "id": "192",
    "username": "marilou.lubowitz",
    "nickname": "hoyt.schimmel",
    "creator": "kjh26f",
    "create_time": "2023-04-15 15:45:14",
    "updater": "qnt7lw",
    "last_update_time": "2023-04-15 15:45:14",
    "login_time": "2023-04-15 15:45:14",
    "type": true,
    "note": "47nmsb",
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
  "code": 60,
  "message": "success",
  "data": {
    "id": "192",
    "username": "marilou.lubowitz",
    "nickname": "hoyt.schimmel",
    "creator": "esgdaf",
    "create_time": "2023-04-15 15:45:14",
    "updater": "ky3l8k",
    "last_update_time": "2023-04-15 15:45:14",
    "login_time": "2023-04-15 15:45:14",
    "type": true,
    "note": "jjm5aa",
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
curl -X POST -i /user/getRoleListByUserId.do --data 'user_id=192'
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
  "code": 127,
  "message": "success",
  "data": [
    {
      "id": "192",
      "name": "marilou.lubowitz",
      "description": "jf8b0h",
      "deleted": 89
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
  "user_id": "192",
  "role_ids": [
    "ad6v24"
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
  "code": 572,
  "message": "success",
  "data": "5ve93e"
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
curl -X POST -i /user/getJwt.do --data 'jwt_id=192&issuer=chin7m&subject=mczj12&audience=pre3oe&authorities=0ygov2,0ygov2'
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
  "code": 762,
  "message": "success",
  "data": "kb94c8"
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
curl -X POST -i /user/generateJwt.do --data 'jwt_id=192&issuer=e65dn8&subject=405rbw&audience=4gjrza&authorities=zovt1q,zovt1q'
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
  "code": 81,
  "message": "success",
  "data": "g0o12b"
}
```

## broker管理

