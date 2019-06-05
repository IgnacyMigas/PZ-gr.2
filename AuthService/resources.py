from flask_restful import Resource, reqparse
from models import UserModel, RevokedTokenModel
from flask_jwt_extended import (create_access_token, create_refresh_token, jwt_required, jwt_refresh_token_required, get_jwt_identity, get_raw_jwt)


parser = reqparse.RequestParser()
parser.add_argument('username', help='This field cannot be blank', required=True)
parser.add_argument('password', help='This field cannot be blank', required=True)


class UserRegistration(Resource):
    def post(self):
        data = parser.parse_args()
        
        if UserModel.find_by_username(data['username']):
            return {'message': 'User {} already exists'.format(data['username'])}
        
        new_user = UserModel(
<<<<<<< HEAD
            username = data['username'],
            password = UserModel.generate_hash(data['password'])
=======
            username=data['username'],
            password=UserModel.generate_hash(data['password'])
>>>>>>> parent of c906064... #61, #64, #65, #66
        )
        
        try:
            new_user.save_to_db()
            access_token = create_access_token(identity=data['username'])
            refresh_token = create_refresh_token(identity=data['username'])
            return {
<<<<<<< HEAD
                'message': 'User {} was created'.format(data['username']),
                'access_token': access_token,
                'refresh_token': refresh_token
                }
        except:
            return {'message': 'Something went wrong'}, 500
=======
                'msg': 'User created'.format(data['username']),
                }, 201
        except:
            return {'msg': 'Something went wrong'}, 500
>>>>>>> parent of c906064... #61, #64, #65, #66


class UserLogin(Resource):
    def post(self):
        data = parser.parse_args()
        current_user = UserModel.find_by_username(data['username'])

        if not current_user:
<<<<<<< HEAD
            return {'message': 'User {} doesn\'t exist'.format(data['username'])}
        
        if UserModel.verify_hash(data['password'], current_user.password):
            access_token = create_access_token(identity = data['username'])
            refresh_token = create_refresh_token(identity = data['username'])
            return {
                'message': 'Logged in as {}'.format(current_user.username),
=======
            return {'message': 'User {} doesn\'t exist'.format(data['username'])}, 401
        
        if UserModel.verify_hash(data['password'], current_user.password):
            access_token = create_access_token(identity=data['username'])
            refresh_token = create_refresh_token(identity=data['username'])
            return {
>>>>>>> parent of c906064... #61, #64, #65, #66
                'access_token': access_token,
                'refresh_token': refresh_token
                }
        else:
<<<<<<< HEAD
            return {'message': 'Wrong credentials'}
=======
            return {'msg': 'Wrong credentials'}

>>>>>>> parent of c906064... #61, #64, #65, #66


class UserLogoutAccess(Resource):
    @jwt_required
    def post(self):
<<<<<<< HEAD
        jti = get_raw_jwt()['jti']
        try:
            revoked_token = RevokedTokenModel(jti = jti)
            revoked_token.add()
            return {'message': 'Access token has been revoked'}
        except:
            return {'message': 'Something went wrong'}, 500
=======
        current_user = get_jwt_identity()
        access_token = create_access_token(identity=current_user)
        return {'access_token': access_token}
>>>>>>> parent of c906064... #61, #64, #65, #66


class UserLogoutRefresh(Resource):
    @jwt_refresh_token_required
<<<<<<< HEAD
    def post(self):
        jti = get_raw_jwt()['jti']
        try:
            revoked_token = RevokedTokenModel(jti = jti)
            revoked_token.add()
            return {'message': 'Refresh token has been revoked'}
        except:
            return {'message': 'Something went wrong'}, 500
=======
    def delete(self):
        jti = get_raw_jwt()['jti']
        try:
            revoked_token = RevokedTokenModel(jti=jti)
            revoked_token.add()
            return {"msg": "Successfully deleted token"}
        except:
            return {'msg': 'Something went wrong'}, 500
>>>>>>> parent of c906064... #61, #64, #65, #66


class TokenRefresh(Resource):
    @jwt_refresh_token_required
    def post(self):
        current_user = get_jwt_identity()
        access_token = create_access_token(identity = current_user)
        return {'access_token': access_token}


class AllUsers(Resource):
    def get(self):
<<<<<<< HEAD
        return UserModel.return_all()
    
    def delete(self):
        return UserModel.delete_all()


class SecretResource(Resource):
    @jwt_required
    def get(self):
        return {
            'answer': 42
        }
=======
        username = get_jwt_identity()
        return {'logged_in_as': username}, 200
>>>>>>> parent of c906064... #61, #64, #65, #66
