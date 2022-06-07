# Private Chat Server

Private Chat Server is an Application Server based on ktor. Chat with friends without storing messages on the server.
Since no messages are stored on the server, it is completely confidential. Used in the [Private Chat App](https://github.com/beomsu317/private-chat-app).

## Installation

First of all clone this repository.

```
git clone https://github.com/beomsu317/private-chat-server.git
```

Open the terminal and execute the `shadowJar` task to build Fat JAR.

```
./gradlew shadowJar
```

When this build completes, you can execute `build/libs/***-all.jar` file.

```
java -jar com.beomsu317.privatechatserver-0.0.1-all.jar
```

## User APIs

### Register

#### Request

|Method|     Route      |    Description     |
|:---:|:--------------:|:------------------:|
|POST| /user/register | User registration. |

|    Parameters   |                   Description                    |
|:---------------:|:------------------------------------------------:|
|   displayName   |   The display name of the user to sign up for.   |
|      email      |      The email of the user to sign up for.       |
|     password    |     The password of the user to sign up for.     |
| confirmPassword | The confirm password of the user to sign up for. |

### Sign in

#### Request

|Method|    Route    |  Description  |
|:---:|:-----------:|:-------------:|
|POST| /user/login | User sign in. |

|    Parameters   |               Description                |
|:---------------:|:----------------------------------------:|
|      email      |  The email of the user to sign in for.   |
|     password    | The password of the user to sign in for. |

#### Response

```json
{
    "result": {
        "token": "user session",
        "user": {
            "id": "user id",
            "email": "user email",
            "displayName": "user display name",
            "photoUrl": "user profile image url",
            "friends": "user friend list",
            "rooms": "user room list"
        }
    }
}
```

### Profile

#### Request

* Header requires Authorization(Bearer).

| Method |     Route     |    Description    |
|:------:|:-------------:|:-----------------:|
|  GET   | /user/profile | Get user profile. |

#### Response

```json
{
    "result": {
        "user": {
            "id": "user id",
            "email": "user email",
            "displayName": "user display name",
            "photoUrl": "user profile image url",
            "friends": "user friend list",
            "rooms": "user room list"
        }
    }
}
```

### Upload Profile Image

#### Request
* 
* Header requires Authorization(Bearer).

| Method |     Route     |        Description         |
|:------:|:-------------:|:--------------------------:|
|  POST  | /user/profile/upload-image | Upload user profile image. |

|     Parameters      |      Description       |
|:-------------------:|:----------------------:|
| multipart/form-data |     Upload image.      |

#### Response

```json
{
    "result": {
        "photoUrl": "http:/10.0.2.2:8080/user/profile/629f4c45518cdf09a0e90c9f"
    }
}
```

### Get Profile Image

#### Request

| Method |       Route        |         Description          |
|:------:|:------------------:|:----------------------------:|
|  GET   | /user/profile/{id} | Get {id} user profile image. |

## Friend APIs

### Search Friend

#### Request

* Header requires Authorization(Bearer).

| Method |        Route         |               Description               |
|:------:|:--------------------:|:---------------------------------------:|
|  POST  | /user/friends/search | Search for the friend you want to add.  |

|          Parameters           |   Description   |
|:-----------------------------:|:---------------:|
| searchText | Search keyword. |

#### Response

```json
{
    "result": {
        "friends": [
            {
                "id": "friend id",
                "email": "friend email",
                "photoUrl": "friend profile image url",
                "displayName": "friend display name",
                "numberOfFriends": "friend's number of friends",
                "numberOfRooms": "friend's number of rooms"
            }
        ]
    }
}
```

### Get Friend

#### Request

* Header requires Authorization(Bearer).

| Method |       Route        |           Description            |
|:------:|:------------------:|:--------------------------------:|
|  GET   | /user/friends/{id} | Get specific friend information. |

#### Response

```json
{
    "result": {
        "friend": {
            "id": "friend id",
            "email": "friend email",
            "photoUrl": "friend profile image url",
            "displayName": "friend display name",
            "numberOfFriends": "friend's number of friends",
            "numberOfRooms": "friend's number of rooms"
        }
    }
}
```

### Add Friend

#### Request

* Header requires Authorization(Bearer).

| Method |       Route       |           Description           |
|:------:|:-----------------:|:-------------------------------:|
|  POST  | /user/friends/add |      Add friend.       |

|    Parameters     | Elements |              Description               |
|:-----------------:|:--------:|:--------------------------------------:|
| List of `friends` |    id    |   ID of the friend you want to add.    |  
|                   | priority | Priority of the friend you want to add |

```json
{
    "result": {
        "friends": [
            {
                "id": "friend id",
                "email": "friend email",
                "photoUrl": "friend profile image url",
                "displayName": "friend display name",
                "numberOfFriends": "friend's number of friends",
                "numberOfRooms": "friend's number of rooms"
            }
        ]
    }
}
```

### DELETE Friend

#### Request

* Header requires Authorization(Bearer).

| Method |       Route       |  Description   |
|:------:|:-----------------:|:--------------:|
|  POST  | /user/friends/delete | Delete friend. |

|    Parameters     | Elements |             Description              |
|:-----------------:|:--------:|:------------------------------------:|
| List of `friends` |    id    | ID of the friend you want to delete. |

## Room APIs

### Create Room

#### Request

* Header requires Authorization(Bearer).

| Method |       Route       |       Description        |
|:------:|:-----------------:|:------------------------:|
|  POST  | /chat/room/create | Create chat rooms to chat with friends. |

| Parameters |            Description             |
|:----------:|:----------------------------------:|
|   userId   | ID of the friend you want to chat. |

#### Response

```json
{
    "result": {
        "room": {
            "id": "room id",
            "owner": "room owner",
            "users": "list of users joined this room" 
        }
    }
}
```

### Get Room Information

#### Request

* Header requires Authorization(Bearer).

| Method |           Route           |      Description      |
|:------:|:-------------------------:|:---------------------:|
|  GET   | /chat/room/info/{room id} | Get room information. |

#### Response

```json
{
    "result": {
        "room": {
            "id": "room id",
            "owner": "room owner",
            "users": "list of users joined this room"
        }
    }
}
```

### Leave Room

#### Request

* Header requires Authorization(Bearer).

| Method |           Route           |      Description       |
|:------:|:-------------------------:|:----------------------:|
|  POST  | /chat/room/leave |      Leave room.       |

| Parameters |            Description            |
|:----------:|:---------------------------------:|
|   roomId   | ID of the room you want to leave. |

## Socket APIs

### Connect

* Header requires Authorization(Bearer).

|      Route       |       Description       |
|:----------------:|:-----------------------:|
| /chat/chat-server | Connect to chat server. |

#### Message 

```json
{
    "roomId": "room id",
    "senderId": "sender id",
    "timestamp": "timestamp",
    "message": "chat message",
    "photoUrl": "sender profile image",
    "displayName": "sender display name"
}
```

## Tech stacks & Libraries

- [Kotlin](https://developer.android.com/kotlin) based, [Kotlin Flow](https://developer.android.com/kotlin/flow)
    + [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- [Ktor](https://ktor.io/)
  - [WebSocket](https://ktor.io/docs/websocket.html) for chat with users.
  - [Serialization](https://ktor.io/docs/serialization.html) for serialization.
  - [Json Web Token](https://ktor.io/docs/jwt.html) for user authentication.
- [Koin](https://insert-koin.io/) for dependency injection.
- [Kmongo](https://litote.org/kmongo/) for mongodb database.

## License

```
Copyright 2022 beomsu317

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```