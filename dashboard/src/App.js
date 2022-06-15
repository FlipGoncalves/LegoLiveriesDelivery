import React, {Component} from 'react';
import logo from './assets//img/favicon.png';
import './App.css';
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
      sales: 0,
      orders: 0,
      riders: 0,
      comp_orders: 0
    };
  }
  

  render() {

    const RequestMapping = () => {

        let resp_rider = fetch('http://localhost:8080/api/statistics', {  
            method: 'GET'
        }).then((data) => {
            data.json().then((obj) => {
              this.setState({sales: obj["sales"], orders: obj["numorders"], riders: obj["numriders"], comp_orders: obj["completedorders"]})
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
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-header p-3 pt-2">
              <div class="icon icon-lg icon-shape bg-gradient-dark shadow-dark text-center border-radius-xl mt-n4 position-absolute">
                <i class="material-icons opacity-10">weekend</i>
              </div>
              <div class="text-end pt-1">
                <p class="text-sm mb-0 text-capitalize">Sales</p>
                <h4 class="mb-0">{this.state.sales}</h4>
              </div>
            </div>
            <hr class="dark horizontal my-0" />
            <div class="card-footer p-3">
              <p class="mb-0"></p>
            </div>
          </div>
        </div>
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-header p-3 pt-2">
              <div class="icon icon-lg icon-shape bg-gradient-primary shadow-primary text-center border-radius-xl mt-n4 position-absolute">
                <i class="material-icons opacity-10">person</i>
              </div>
              <div class="text-end pt-1">
                <p class="text-sm mb-0 text-capitalize">Total Orders</p>
                <h4 class="mb-0">{this.state.orders}</h4>
              </div>
            </div>
            <hr class="dark horizontal my-0" />
            <div class="card-footer p-3">
              <p class="mb-0"></p>
            </div>
          </div>
        </div>
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-header p-3 pt-2">
              <div class="icon icon-lg icon-shape bg-gradient-success shadow-success text-center border-radius-xl mt-n4 position-absolute">
                <i class="material-icons opacity-10">person</i>
              </div>
              <div class="text-end pt-1">
                <p class="text-sm mb-0 text-capitalize">Total Riders</p>
                <h4 class="mb-0">{this.state.riders}</h4>
              </div>
            </div>
            <hr class="dark horizontal my-0" />
            <div class="card-footer p-3">
              <p class="mb-0"></p>
            </div>
          </div>
        </div>
        <div class="col-xl-3 col-sm-6">
          <div class="card">
            <div class="card-header p-3 pt-2">
              <div class="icon icon-lg icon-shape bg-gradient-info shadow-info text-center border-radius-xl mt-n4 position-absolute">
                <i class="material-icons opacity-10">weekend</i>
              </div>
              <div class="text-end pt-1">
                <p class="text-sm mb-0 text-capitalize">Completed Orders</p>
                <h4 class="mb-0">{this.state.comp_orders}</h4>
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
                                      text: 'Sales by month',
                                    }
                                  },
                                }} data={{labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                                          datasets: [
                                            {
                                              label: 'Number of sales',
                                              data: [1,2,3,4,5,6,7,8,9,10,11,12],
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
              <h6 class="mb-0 ">Sales by Month</h6>
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
                <Line options={{ responsive: true,
                                  plugins: {
                                    legend: {
                                      position: 'top',
                                    },
                                    title: {
                                      display: true,
                                      text: 'Sales by month',
                                    }
                                  },
                                }} data={{labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                                          datasets: [
                                            {
                                              label: 'Number of sales',
                                              data: [1,2,3,4,5,6,7,8,9,10,11,12],
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
              <h6 class="mb-0 "> Number of Orders </h6>
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
                <Line options={{ responsive: true,
                                  plugins: {
                                    legend: {
                                      position: 'top',
                                    },
                                    title: {
                                      display: true,
                                      text: 'Sales by month',
                                    }
                                  },
                                }} data={{labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                                          datasets: [
                                            {
                                              label: 'Number of sales',
                                              data: [1,2,3,4,5,6,7,8,9,10,11,12],
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
