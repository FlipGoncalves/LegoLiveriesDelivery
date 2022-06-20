import React, {Component} from 'react';
import logo from './assets//img/favicon.png';
import './App.css';
import './assets/css/material-dashboard.css';
import './assets/css/material-dashboard.css.map';
import './assets/css/material-dashboard.min.css';
import './assets/css/nucleo-svg.css';
import './assets/css/nucleo-icons.css';
import Aside from './component/aside'
import { Link } from 'react-router-dom';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  BarElement,
  defaults
} from 'chart.js';
import { Line, Bar } from 'react-chartjs-2';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  Title,
  Tooltip,
  Legend
);

// rgb(92,184,96) -> green from the chart card

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      count: 1,
      orders: 0,
      riders: 0,
      comp_orders: 0,
      labels: [],
      data: []
    };
  }
  

  render() {

    const RequestMapping = () => {

        let resp_rider = fetch('http://localhost:9001/api/statistics', {  
            method: 'GET'
        }).then((data) => {
            data.json().then((obj) => {
              console.log(obj)
              this.setState({
                  orders: obj["numOrders"], riders: obj["numRiders"], comp_orders: obj["completedOrders"], 
                  labels: [Object.keys(obj["orderByStore"]), Object.keys(obj["compOrderByStore"]), Object.keys(obj["reviewPerRider"])],
                  data: [Object.values(obj["orderByStore"]), Object.values(obj["compOrderByStore"]), Object.values(obj["reviewPerRider"])]
              })
            });
        })

    }

    if (this.state.count === 1) {
        RequestMapping();
        this.state.count += 1;
    }

    defaults.color = 'white';

    return (
      <div className="App">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet"></link>
      <Aside></Aside>

      <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">


        <div class="container-fluid py-4" style={{marginTop: 100 }}>
      <div class="row">
        <div class="col-xl-4 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-header p-3 pt-2">
              <div class="icon icon-lg icon-shape bg-gradient-primary shadow-primary text-center border-radius-xl mt-n4 position-absolute">
                <i class="material-icons opacity-10">person</i>
              </div>
              <div class="text-end pt-1">
                <p class="text-sm mb-0 text-capitalize">Total Orders</p>
                <h4 class="mb-0" id="orders">{this.state.orders}</h4>
              </div>
            </div>
            <hr class="dark horizontal my-0" />
            <div class="card-footer p-3">
              <p class="mb-0"></p>
            </div>
          </div>
        </div>
        <div class="col-xl-4 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-header p-3 pt-2">
              <div class="icon icon-lg icon-shape bg-gradient-success shadow-success text-center border-radius-xl mt-n4 position-absolute">
                <i class="material-icons opacity-10">person</i>
              </div>
              <div class="text-end pt-1">
                <p class="text-sm mb-0 text-capitalize">Total Riders</p>
                <h4 class="mb-0" id="riders">{this.state.riders}</h4>
              </div>
            </div>
            <hr class="dark horizontal my-0" />
            <div class="card-footer p-3">
              <p class="mb-0"></p>
            </div>
          </div>
        </div>
        <div class="col-xl-4 col-sm-6">
          <div class="card">
            <div class="card-header p-3 pt-2">
              <div class="icon icon-lg icon-shape bg-gradient-info shadow-info text-center border-radius-xl mt-n4 position-absolute">
                <i class="material-icons opacity-10">weekend</i>
              </div>
              <div class="text-end pt-1">
                <p class="text-sm mb-0 text-capitalize">Completed Orders</p>
                <h4 class="mb-0" id="compl">{this.state.comp_orders}</h4>
              </div>
            </div>
            <hr class="dark horizontal my-0" />
            <div class="card-footer p-3">
              <p class="mb-0"></p>
            </div>
          </div>
        </div>
      </div>
      <div class="row mt-4">
        <div class="col-lg-4 col-md-6 mt-4 mb-4">
          <div class="card z-index-2 ">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2 bg-transparent">
              <div class="bg-gradient-primary shadow-primary border-radius-lg py-3 pe-1">
                <div class="chart">
                  <Bar options={{ responsive: true,
                                  plugins: {
                                    legend: {
                                      position: 'top',
                                    },
                                    title: {
                                      display: true,
                                      text: 'Number of Orders By Store',
                                    }
                                  },
                                }} data={{labels: this.state.labels[0],
                                          datasets: [
                                            {
                                              label: 'Number of Orders',
                                              data: this.state.data[0],
                                              borderColor: 'rgb(53, 162, 235)',
                                              backgroundColor: 'rgba(53, 162, 235, 0.7)',
                                              color: 'white',
                                            },
                                          ],
                                        }} className="class-canvas"/>
                </div>
              </div>
            </div>
            <div class="card-body">
              <h6 class="mb-0 "> Number of Orders By Store </h6>
              <p class="text-sm "></p>
              <hr class="dark horizontal" />
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6 mt-4 mb-4">
          <div class="card z-index-2  ">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2 bg-transparent">
              <div class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1">
                <div class="chart">
                <Bar options={{ responsive: true,
                                  plugins: {
                                    legend: {
                                      position: 'top',
                                    },
                                    title: {
                                      display: true,
                                      text: 'Number of Completed Orders by Store',
                                    }
                                  },
                                }} data={{labels: this.state.labels[1],
                                          datasets: [
                                            {
                                              label: 'Number of Completed Orders',
                                              data: this.state.data[1],
                                              borderColor: 'rgb(0, 0, 0)',
                                              backgroundColor: 'rgba(0, 0, 0, 0.7)',
                                              color: 'white',
                                            },
                                          ],
                                        }} className="class-canvas"/>
                </div>
              </div>
            </div>
            <div class="card-body">
              <h6 class="mb-0 "> Number of Completed Orders by Store </h6>
              <p class="text-sm "></p>
              <hr class="dark horizontal" />
            </div>
          </div>
        </div>
        <div class="col-lg-4 mt-4 mb-3">
          <div class="card z-index-2 ">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2 bg-transparent">
              <div class="bg-gradient-dark shadow-dark border-radius-lg py-3 pe-1">
                <div class="chart">
                <Bar options={{ responsive: true,
                                  plugins: {
                                    legend: {
                                      position: 'top',
                                    },
                                    title: {
                                      display: true,
                                      text: 'Average Review per Rider',
                                    }
                                  },
                                }} data={{labels: this.state.labels[2],
                                          datasets: [
                                            {
                                              label: 'Average Review',
                                              data: this.state.data[2],
                                              borderColor: 'rgb(255, 99, 132)',
                                              backgroundColor: 'rgba(255, 99, 132, 0.7)',
                                              color: 'white',
                                            },
                                          ],
                                        }} className="class-canvas"/>
                </div>
              </div>
            </div>
            <div class="card-body">
              <h6 class="mb-0 ">Completed Tasks</h6>
              <p class="text-sm "></p>
              <hr class="dark horizontal" />
            </div>
          </div>
        </div>
      </div>

      </div>
        </main>
      </div>
    );
  }
}

export default App;
