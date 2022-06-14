from matplotlib.font_manager import json_load
import requests

url = "http://localhost:8080/lego/insert_lego"

json = json_load("legoList.json")

for j in json:
    r = requests.post(url=url, json=j)
    print(r.status_code)

#r = requests.post(url=url, )