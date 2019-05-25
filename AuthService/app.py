#!flask/bin/python
import json
from datetime import datetime, timedelta
from flask import Flask, jsonify, abort, make_response, request
#import jwt
from functools import wraps
from flask_jwt_extended import (
    JWTManager, jwt_required, create_access_token,
    jwt_refresh_token_required, create_refresh_token,
    get_jwt_identity, fresh_jwt_required, get_raw_jwt
)

from models import User

app = Flask(__name__)

app.config['JWT_SECRET_KEY'] = 'ChangeMe'
app.config['JWT_TOKEN_LOCATION'] = 'json'
# app.config['JWT_ACCESS_TOKEN_EXPIRES'] = 40  # In seconds, default = 900 (15min) / OR can be setup to False
app.config['JWT_BLACKLIST_ENABLED'] = True
app.config['JWT_BLACKLIST_TOKEN_CHECKS'] = ['refresh']

jwt = JWTManager(app)

blacklist = set()

User.objects.create(username='test', password='test')


@jwt.token_in_blacklist_loader
def check_if_token_in_blacklist(decrypted_token):
    jti = decrypted_token['jti']
    return jti in blacklist


@app.route('/v1/user', methods=['POST'])
def user_create():
    if not request.json or 'username' not in request.json or 'password' not in request.json:
        abort(400)
    try:
        User.objects.get(username=request.json['username'])
        return jsonify({'message': "User {} already exist.".format(request.json['username'])}), 400
    except User.DoesNotExist:
        User.objects.create(username=request.json['username'], password=request.json['password'])
        return jsonify({'message': 'User Created'}), 201


# Standard login endpoint. Will return a fresh access token and
# a refresh token
@app.route('/v1/login', methods=['POST'])
def login():
    if not request.json or 'username' not in request.json or 'password' not in request.json:
        abort(400)

    username = request.json.get('username', None)
    password = request.json.get('password', None)

    try:
        user = User.objects.get(username=username)
        user.match_password(password)
    except (User.DoesNotExist, User.PasswordDoesNotMatch):
        return jsonify({'message': 'Wrong credentials'}), 401

    ret = {
        'access_token': create_access_token(identity=username),
        'refresh_token': create_refresh_token(identity=username)
    }
    return jsonify(ret), 200


# The jwt_refresh_token_required decorator insures a valid refresh
# token is present in the request before calling this endpoint. We
# can use the get_jwt_identity() function to get the identity of
# the refresh token, and use the create_access_token() function again
# to make a new access token for this identity.
@app.route('/v1/token', methods=['POST'])
@jwt_refresh_token_required
def refresh():
    current_user = get_jwt_identity()
    new_token = create_access_token(identity=current_user)
    ret = {'access_token': new_token}
    return jsonify(ret), 200


# Endpoint for revoking the current users access token
@app.route('/v1/token', methods=['DELETE'])
@jwt_refresh_token_required
def logout():
    jti = get_raw_jwt()['jti']
    blacklist.add(jti)
    return jsonify({"message": "Successfully deleted token"}), 200


# TESTING ENDPOINT
# Any valid JWT can access this endpoint
@app.route('/v1/protected', methods=['GET'])
@jwt_required
def protected():
    username = get_jwt_identity()
    return jsonify(logged_in_as=username), 200


if __name__ == '__main__':
    app.run(debug=True)
