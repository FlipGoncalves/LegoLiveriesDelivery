import React, {Component} from 'react';
import logo from '../assets//img/favicon.png';
import '../App.css';
import '../assets/css/material-dashboard.css';
import '../assets/css/material-dashboard.css.map';
import '../assets/css/material-dashboard.min.css';
import '../assets/css/nucleo-svg.css';
import '../assets/css/nucleo-icons.css';
import Aside from './aside'
import { Link } from 'react-router-dom';


class Management extends Component {

  constructor(props) {
    super(props);
    this.state = {
      orders: [],
      count: 1,
      riders: []
    };
  }

  addRiderToArray(item) {
    return (
        <tr>
          <td class="align-middle text-center">
            <div>
              <div class="d-flex flex-column justify-content-center">
                <h6 class="mb-0 text-sm">John Michael</h6>
                <p class="text-xs text-secondary mb-0">john@creative-tim.com</p>
              </div>
            </div>
          </td>
          <td>
            <p class="text-xs font-weight-bold mb-0">Aveiro</p>
          </td>
          <td class="align-middle text-center text-sm">
            <span class="badge badge-sm bg-gradient-success">Online</span>
            <span class="badge badge-sm bg-gradient-secondary">Offline</span>
          </td>
          <td class="align-middle text-center">
            <span class="text-secondary text-xs font-weight-bold">4.5</span>
          </td>
        </tr>
    )
  }

  addOrderToArray(item) {
    return (
      <tr>
        <td class="align-middle text-center">
          <div>
            <div class="d-flex flex-column justify-content-center">
              <h6 class="mb-0 text-sm">Address</h6>
            </div>
          </div>
        </td>
        <td>
          <p class="text-xs font-weight-bold mb-0">Store 1</p>
        </td>
        <td class="align-middle text-center">
          <span class="text-secondary text-xs font-weight-bold">Client 1</span>
        </td>
        <td class="align-middle text-center text-sm">
          <span class="badge badge-sm bg-gradient-success">Done</span>
          <span class="badge badge-sm bg-gradient-secondary">In Progress</span>
        </td>
        <button> Add Rider</button>
      </tr>
    )
  }
  
  render() {

    const RequestMapping = () => {

        let resp_rider = fetch('http://localhost:8080/api/riders', {  
            method: 'GET'
        }).then((data) => {
            this.setState({riders: []})
            data.json().then((list) => {
                let newArray = []
                list.forEach((item) => {
                    newArray.push(
                        this.addRiderToArray(item)
                    )
                }); 
                this.setState({ riders: newArray})
            });
        })

        let resp_order = fetch('http://localhost:8080/api/orders', {
            method: 'GET'
        }).then((data) => {
            this.setState({orders: []})
            data.json().then((list) => {
                let newArray = []
                list.forEach((item) => {
                    newArray.push(
                        this.addOrderToArray(item)
                    )
                }); 
                this.setState({ orders: newArray})
            });
        })

    }

    if (this.state.count === 1) {
        RequestMapping();
        this.state.count += 1;
    }

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
                  <table class="table align-items-center mb-0">
                    <thead>
                      <tr>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Address</th>
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
                  <table class="table align-items-center mb-0">
                    <thead>
                      <tr>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Rider</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Location</th>
                        <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Status</th>
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
      

        <div class="container-fluid py-4">
          <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
              <div class="card mt-4">
                <div class="card-header p-3">
                  <h5 class="mb-0">Notifications</h5>
                  <p class="text-sm mb-0">
                    Notifications on this page use Toasts from Bootstrap. Read more details <a href="https://getbootstrap.com/docs/5.0/components/toasts/" target="
              ">here</a>.
                  </p>
                </div>
                <div class="card-body p-3">
                  <div class="row">
                    <div class="col-lg-3 col-sm-6 col-12">
                      <button class="btn bg-gradient-success w-100 mb-0 toast-btn" type="button" data-target="successToast">Success</button>
                    </div>
                    <div class="col-lg-3 col-sm-6 col-12 mt-sm-0 mt-2">
                      <button class="btn bg-gradient-info w-100 mb-0 toast-btn" type="button" data-target="infoToast">Info</button>
                    </div>
                    <div class="col-lg-3 col-sm-6 col-12 mt-lg-0 mt-2">
                      <button class="btn bg-gradient-warning w-100 mb-0 toast-btn" type="button" data-target="warningToast">Warning</button>
                    </div>
                    <div class="col-lg-3 col-sm-6 col-12 mt-lg-0 mt-2">
                      <button class="btn bg-gradient-danger w-100 mb-0 toast-btn" type="button" data-target="dangerToast">Danger</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="position-fixed bottom-1 end-1 z-index-2">
            <div class="toast fade hide p-2 bg-white" role="alert" aria-live="assertive" id="successToast" aria-atomic="true">
              <div class="toast-header border-0">
                <i class="material-icons text-success me-2">
            check
          </i>
                <span class="me-auto font-weight-bold">Material Dashboard </span>
                <small class="text-body">11 mins ago</small>
                <i class="fas fa-times text-md ms-3 cursor-pointer" data-bs-dismiss="toast" aria-label="Close"></i>
              </div>
              <hr class="horizontal dark m-0" />
              <div class="toast-body">
                Hello, world! This is a notification message.
              </div>
            </div>
            <div class="toast fade hide p-2 mt-2 bg-gradient-info" role="alert" aria-live="assertive" id="infoToast" aria-atomic="true">
              <div class="toast-header bg-transparent border-0">
                <i class="material-icons text-white me-2">
            notifications
          </i>
                <span class="me-auto text-white font-weight-bold">Material Dashboard </span>
                <small class="text-white">11 mins ago</small>
                <i class="fas fa-times text-md text-white ms-3 cursor-pointer" data-bs-dismiss="toast" aria-label="Close"></i>
              </div>
              <hr class="horizontal light m-0" />
              <div class="toast-body text-white">
                Hello, world! This is a notification message.
              </div>
            </div>
            <div class="toast fade hide p-2 mt-2 bg-white" role="alert" aria-live="assertive" id="warningToast" aria-atomic="true">
              <div class="toast-header border-0">
                <i class="material-icons text-warning me-2">
            travel_explore
          </i>
                <span class="me-auto font-weight-bold">Material Dashboard </span>
                <small class="text-body">11 mins ago</small>
                <i class="fas fa-times text-md ms-3 cursor-pointer" data-bs-dismiss="toast" aria-label="Close"></i>
              </div>
              <hr class="horizontal dark m-0" />
              <div class="toast-body">
                Hello, world! This is a notification message.
              </div>
            </div>
            <div class="toast fade hide p-2 mt-2 bg-white" role="alert" aria-live="assertive" id="dangerToast" aria-atomic="true">
              <div class="toast-header border-0">
                <i class="material-icons text-danger me-2">
            campaign
          </i>
                <span class="me-auto text-gradient text-danger font-weight-bold">Material Dashboard </span>
                <small class="text-body">11 mins ago</small>
                <i class="fas fa-times text-md ms-3 cursor-pointer" data-bs-dismiss="toast" aria-label="Close"></i>
              </div>
              <hr class="horizontal dark m-0" />
              <div class="toast-body">
                Hello, world! This is a notification message.
              </div>
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

export default Management;
