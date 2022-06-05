from matplotlib.font_manager import json_load
import requests

url = "http://localhost:8080/lego/insert_lego"

json = json_load("legoList.json")
print(json)

for j in json:
    r = requests.post(url=url, json=j)

#r = requests.post(url=url, )