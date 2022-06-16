from matplotlib.font_manager import json_load
import requests

legos = json_load("legoList.json")
addressesEngine = json_load("addressEngineList.json")
addressesLegoliveries = json_load("addressLegoliveriesList.json")
clients = json_load("clientList.json")
riders = json_load("riderList.json")
stores = json_load("storeList.json")
usersEngine = json_load("userEngineList.json")
usersLegoliveries = json_load("userLegoliveriesList.json")
order = json_load("order.json")

"""for j in order:
    print(j)
    r = requests.post(url="http://localhost:8080/order", json=j)
    print(r.status_code)

"""
print("ADDRESSES ENGINE")
for j in addressesEngine:
    print(j)
    r = requests.post(url="http://localhost:9001/api/address", json=j)
    print(r.status_code)

print("ADDRESSES LEGOLIVERIES")
for j in addressesLegoliveries:
    print(j)
    r = requests.post(url="http://localhost:8080/address", json=j)
    print(r.status_code)

print("USERS ENGINE")
for j in usersEngine:
    print(j)
    r = requests.post(url="http://localhost:9001/api/user/register", json=j)
    print(r.status_code)

print("USERS LEGOLIVERIES")
for j in usersLegoliveries:
    print(j)
    r = requests.post(url="http://localhost:8080/user/register", json=j)
    print(r.status_code)

print("STORES ENGINE")
for j in stores:
    print(j)
    r = requests.post(url="http://localhost:9001/api/store", json=j)
    print(r.status_code)

print("RIDERS ENGINE")
for j in riders:
    print(j)
    r = requests.post(url="http://localhost:9001/api/rider", json=j)
    print(r.status_code)

print("CLIENT LEGOLIVERIES")
for j in clients:
    print(j)
    r = requests.post(url="http://localhost:8080/client", json=j)
    print(r.status_code)

for j in legos:
    print(j)
    r = requests.post(url="http://localhost:8080/lego", json=j)
    print(r.status_code)