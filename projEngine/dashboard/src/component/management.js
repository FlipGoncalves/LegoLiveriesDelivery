import React, {Component} from 'react';
import logo from '../assets//img/favicon.png';
import '../App.css';
import Aside from './aside'
import { Link } from 'react-router-dom';
import axios from 'axios';


class Management extends Component {

  constructor(props) {
    super(props);
    this.state = {
      orders: [],
      count: 1,
      riders: [],
      error_message: ""
    };
  }

  addOrderToArray(item) {

    console.log(item)
    let store = item["store"]

    return (
      <tr>
        <td>
          <p class="text-xs font-weight-bold mb-0">{store["name"]}</p>
        </td>
        <td class="align-middle text-center">
          <span class="text-secondary text-xs font-weight-bold">{item["clientName"]}</span>
        </td>
        <td class="align-middle text-center text-sm">
          {item["status"] === 2 ? <span class="badge badge-sm bg-gradient-success">Done</span> : <>
          <span class="badge badge-sm bg-gradient-secondary">In Progress</span>
          </>}
        </td>
      </tr>
    )
  }

  addRiderToArray(item) {
    let user = item["user"]

    console.log(item)

    return (
        <tr>
          <td class="align-middle text-center">
            <div>
              <div class="d-flex flex-column justify-content-center">
                <h6 class="mb-0 text-sm">{user["username"]}</h6>
                <p class="text-xs text-secondary mb-0">{user["email"]}</p>
                <p class="text-xs text-secondary mb-0">{item["riderId"]}</p>
              </div>
            </div>
          </td>
          <td class="align-middle text-center">
            <span class="text-secondary text-xs font-weight-bold">{item["totalReviews"] === 0 ? 0 : Math.round(item["reviewSum"] / item["totalReviews"] * 100) / 100}</span>
          </td>
        </tr>
    )
  }
  
  render() {

    const RequestMapping = () => {

        let resp_rider = fetch('http://localhost:9001/api/riders', {  
            method: 'GET'
        }).then((data) => {
            this.setState({riders: []})
            data.json().then((list) => {
                let newArray = []
                // list["riders"]
                list.forEach((item) => {
                    newArray.push(
                        this.addRiderToArray(item)
                    )
                }); 
                this.setState({ riders: newArray})
            });
        })

        let resp_order = fetch('http://localhost:9001/api/orders', {
            method: 'GET'
        }).then((data) => {
            this.setState({orders: []})
            data.json().then((list) => {
                let newArray = []
                // list["orders"]
                list.forEach((item) => {
                    newArray.push(
                        this.addOrderToArray(item)
                    )
                }); 
                this.setState({ orders: newArray})
            });
        })

    }

    const addrider = () => {

      // post to api
      let name = document.getElementById("name").value;
      let password = document.getElementById("password").value;
      let email = document.getElementById("email").value;

      if (name === "") {
          this.setState({error_message: "Please insert Name"})
          return false
      } else if (email === "") {
          this.setState({error_message: "Please insert Email"})
          return false
      } else if (password === "") {
          this.setState({error_message: "Please insert Password"})
          return false
      }

      axios.post('http://localhost:9001/api/riders', {
        "username": name,
        "email": email,
        "password": password,
        "numRev": 0,
        "sumRev": 0
      })
      .then((response) => {
        console.log(response);
        this.setState({error_message: ""})
        console.log("HERE")
        RequestMapping()
        document.getElementById("name").value = "";
        document.getElementById("password").value = "";
        document.getElementById("email").value = "";
      })
      .catch((error) => {
        console.log(error);
        this.setState({error_message: "ERROR during rider add: " + error})
        return false;
      });
    }

    if (this.state.count === 1) {
        RequestMapping();
        this.state.count += 1;
    }

    return (
      <div className="App">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet"></link>

      <Aside></Aside>

      <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">

      <div class="container-fluid py-4">
        <div class="row">
          <div class="col-12">
            <div class="card my-4">
              <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                  <h6 class="text-white text-capitalize ps-3">Orders</h6>
                </div>
              </div>
              <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                  <table class="table align-items-center mb-0" id="orders">
                    <thead>
                      <tr>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Store</th>
                        <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Client Name</th>
                        <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Completion</th>
                        <th class="text-secondary opacity-7"></th>
                      </tr>
                    </thead>
                    <tbody>
                     {this.state.orders}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>

      <div class="container-fluid py-4">
        <div class="row">
          <div class="col-12">
            <div class="card my-4">
              <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                  <h6 class="text-white text-capitalize ps-3">Riders table</h6>
                </div>
              </div>
              <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                  <table class="table align-items-center mb-0" id="riders">
                    <thead>
                      <tr>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Rider</th>
                        <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Review</th>
                        <th class="text-secondary opacity-7"></th>
                      </tr>
                    </thead>
                    <tbody>
                      {this.state.riders}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>

        <form role="form" class="text-start">
            <div class="container my-auto" style={{paddingTop: 10}}>
                <div class="row">
                <div class="col-md-5">
                    <div class="card-body">
                          <div class="input-group input-group-outline my-3">
                              <input type="name" class="form-control" id="name" placeholder="Name"/>
                          </div>
                          <div class="input-group input-group-outline mb-3">
                              <input type="text" class="form-control" id="email" placeholder="Email"/>
                          </div>
                          <div class="input-group input-group-outline mb-3">
                              <input type="password" class="form-control" id="password" placeholder="Password"/>
                          </div>

                          <div class="text-center">
                              <button type="button" class="btn bg-gradient-secondary w-100 my-4 mb-2" onClick={addrider} id="add_rider">Add a Rider</button>
                          </div>
                    </div>
                </div>
                </div>
            </div>
            </form>
            {this.state.error_message !== "" ? <>
              <div>
                <label class="form-check-label mb-0 ms-2" style={{color: 'red'}} id="error">{this.state.error_message}</label>
              </div>
            </> : null}
            </div>
          </div>
        </main>
      </div>
    );
  }
}

export default Management;
