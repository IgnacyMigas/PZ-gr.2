## Project setup
```
python install
pip install -r requirements.txt
```

### Run server
```
python app.py
```

## Available endpoints

### v1/users
#### POST
```
Create new user.
Request parameters

Body:

{
   "username":"<username>",
   "password":"<password>"
}

Response

Success: 201 Failure: 400
```
### v1/login
#### POST
```
Returns JWT authentication token and refresh token in login process.
Request parameters

Body:

{
   "username":"<username>",
   "password":"<password>"
}

Response

Success: 200

{
   "access-token":"<access-token>",
   "refresh-token":"<refresh-token>"
}

Failure: 400, 401
```
### v1/token
#### POST
```
Returns new JWT access-token in token refresh process.
Token refresh:

Body:

{
   "refresh-token":"<refresh-token>"
}

Response

Success: 200

{
   "access-token":"<access-token>"
}

Failure: 400, 401
```
#### DELETE
```
Invalidates refresh token.

Body:

{
   "refresh-token":"<refresh-token>"
}

Success: 200 Failure: 400
```
