from flask import Flask, request
from flask_restful import Resource, Api

app = Flask(__name__)
api = Api(app)

class Index(Resource):
    def get(self):
        return {'about':'EloMakrelo'}
    
    def post(self):
        json = request.get_json()
        return {'sended': json}

api.add_resource(Index, '/')

# If we're running in stand alone mode, run the application
if __name__ == '__main__':
    app.run(debug=True)