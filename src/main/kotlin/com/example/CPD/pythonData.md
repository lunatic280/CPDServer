```
import requests

session = requests.Session()

#먼저 계정이 필요함
login_data = {
    "email": "1234",
    "password": "1234"
}

login_url= "http://localhost:8080/api/auth/login"
response = session.post(login_url, json=login_data)

print("로그인 응답:", response.status_code, response.cookies) 

data = {
    "stringTest": "string1",
    "intTest": 2,
    "booleanTest": False
}

url = "http://localhost:8080/test/test-json"

response = session.post(url, json=data)
print("데이터 응답", response.status_code, response.text)```
