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
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"></meta>
        <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet"></link>
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
        <script async defer src="https://buttons.github.io/buttons.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

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
                <p class="text-sm mb-0 text-capitalize">Today's Riders</p>
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
      <footer class="footer py-1">
        <div class="container-fluid">
          <div class="row align-items-center justify-content-lg-between">
            <div class="col-lg-6 mb-lg-0 mb-4">
              <div class="copyright text-center text-sm text-muted text-lg-start">
                © <script>
                  document.write(new Date().getFullYear())
                </script>,
                made with <i class="fa fa-heart"></i> by
                <a href="https://www.creative-tim.com" class="font-weight-bold" target="_blank">Creative Tim</a>
                for a better web.
              </div>
            </div>
            <div class="col-lg-6">
              <ul class="nav nav-footer justify-content-center justify-content-lg-end">
                <li class="nav-item">
                  <a href="https://www.creative-tim.com" class="nav-link text-muted" target="_blank">Creative Tim</a>
                </li>
                <li class="nav-item">
                  <a href="https://www.creative-tim.com/presentation" class="nav-link text-muted" target="_blank">About Us</a>
                </li>
                <li class="nav-item">
                  <a href="https://www.creative-tim.com/blog" class="nav-link text-muted" target="_blank">Blog</a>
                </li>
                <li class="nav-item">
                  <a href="https://www.creative-tim.com/license" class="nav-link pe-0 text-muted" target="_blank">License</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </footer>

      </div>
        </main>
      </div>
    );
  }
}

export default App;
