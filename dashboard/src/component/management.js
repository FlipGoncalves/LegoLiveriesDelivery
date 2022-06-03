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
  }
  
  render() {

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
      
      <div class="row mb-4">
        <div class="col-lg-8 col-md-6 mb-md-0 mb-4">
          <div class="card">
            <div class="card-header pb-0">
              <div class="row">
                <div class="col-lg-6 col-7">
                  <h6>Projects</h6>
                  <p class="text-sm mb-0">
                    <i class="fa fa-check text-info" aria-hidden="true"></i>
                    <span class="font-weight-bold ms-1">30 done</span> this month
                  </p>
                </div>
                <div class="col-lg-6 col-5 my-auto text-end">
                  <div class="dropdown float-lg-end pe-4">
                    <a class="cursor-pointer" id="dropdownTable" data-bs-toggle="dropdown" aria-expanded="false">
                      <i class="fa fa-ellipsis-v text-secondary"></i>
                    </a>
                    <ul class="dropdown-menu px-2 py-3 ms-sm-n4 ms-n5" aria-labelledby="dropdownTable">
                      <li><a class="dropdown-item border-radius-md">Action</a></li>
                      <li><a class="dropdown-item border-radius-md">Another action</a></li>
                      <li><a class="dropdown-item border-radius-md">Something else here</a></li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
            <div class="card-body px-0 pb-2">
              <div class="table-responsive">
                <table class="table align-items-center mb-0">
                  <thead>
                    <tr>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Companies</th>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Members</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Budget</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Completion</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img src={require("../assets/img/small-logos/logo-xd.svg")} class="avatar avatar-sm me-3" alt="xd" />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Material XD Version</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Tompson">
                            <img src={require("../assets/img/team-1.jpg")} alt="team1" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Romina Hadid">
                            <img src={require("../assets/img/team-2.jpg")} alt="team2" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Alexander Smith">
                            <img src={require("../assets/img/team-3.jpg")} alt="team3" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Jessica Doe">
                            <img src={require("../assets/img/team-4.jpg")} alt="team4" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $14,000 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">60%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div class="progress-bar bg-gradient-info w-60" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img src={require("../assets/img/small-logos/logo-atlassian.svg")} class="avatar avatar-sm me-3" alt="atlassian" />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Add Progress Track</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Romina Hadid">
                            <img src={require("../assets/img/team-2.jpg")} alt="team5" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Jessica Doe">
                            <img src={require("../assets/img/team-4.jpg")} alt="team6"/>
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $3,000 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">10%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div class="progress-bar bg-gradient-info w-10" role="progressbar" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img src={require("../assets/img/small-logos/logo-slack.svg")} class="avatar avatar-sm me-3" alt="team7" />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Fix Platform Errors</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Romina Hadid">
                            <img src={require("../assets/img/team-3.jpg")} alt="team8" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Jessica Doe">
                            <img src={require("../assets/img/team-1.jpg")} alt="team9" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> Not set </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">100%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div class="progress-bar bg-gradient-success w-100" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img src={require("../assets/img/small-logos/logo-spotify.svg")} class="avatar avatar-sm me-3" alt="spotify" />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Launch our Mobile App</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Tompson">
                            <img src={require("../assets/img/team-4.jpg")} alt="user1" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Romina Hadid">
                            <img src={require("../assets/img/team-3.jpg")} alt="user2" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Alexander Smith">
                            <img src={require("../assets/img/team-4.jpg")} alt="user3" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Jessica Doe">
                            <img src={require("../assets/img/team-1.jpg")} alt="user4" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $20,500 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">100%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div class="progress-bar bg-gradient-success w-100" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img src={require("../assets/img/small-logos/logo-jira.svg")} class="avatar avatar-sm me-3" alt="jira" />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Add the New Pricing Page</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Tompson">
                            <img src={require("../assets/img/team-4.jpg")} alt="user5" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $500 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">25%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div class="progress-bar bg-gradient-info w-25" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="25"></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img src={require("../assets/img/small-logos/logo-invision.svg")} class="avatar avatar-sm me-3" alt="invision" />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Redesign New Online Shop</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Tompson">
                            <img src={require("../assets/img/team-1.jpg")} alt="user6" />
                          </a>
                          <a class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Jessica Doe">
                            <img src={require("../assets/img/team-4.jpg")} alt="user7" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $2,000 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">40%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div class="progress-bar bg-gradient-info w-40" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="40"></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="card h-100">
            <div class="card-header pb-0">
              <h6>Orders overview</h6>
              <p class="text-sm">
                <i class="fa fa-arrow-up text-success" aria-hidden="true"></i>
                <span class="font-weight-bold">24%</span> this month
              </p>
            </div>
            <div class="card-body p-3">
              <div class="timeline timeline-one-side">
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="material-icons text-success text-gradient">notifications</i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">$2400, Design changes</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">22 DEC 7:20 PM</p>
                  </div>
                </div>
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="material-icons text-danger text-gradient">code</i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">New order #1832412</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">21 DEC 11 PM</p>
                  </div>
                </div>
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="material-icons text-info text-gradient">shopping_cart</i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">Server payments for April</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">21 DEC 9:34 PM</p>
                  </div>
                </div>
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="material-icons text-warning text-gradient">credit_card</i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">New card added for order #4395133</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">20 DEC 2:20 AM</p>
                  </div>
                </div>
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="material-icons text-primary text-gradient">key</i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">Unlock packages for development</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">18 DEC 4:54 AM</p>
                  </div>
                </div>
                <div class="timeline-block">
                  <span class="timeline-step">
                    <i class="material-icons text-dark text-gradient">payments</i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">New order #9583120</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">17 DEC</p>
                  </div>
                </div>
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
                  <h6 class="text-white text-capitalize ps-3">Authors table</h6>
                </div>
              </div>
              <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                  <table class="table align-items-center mb-0">
                    <thead>
                      <tr>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Author</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Function</th>
                        <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Status</th>
                        <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Employed</th>
                        <th class="text-secondary opacity-7"></th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>
                          <div class="d-flex px-2 py-1">
                            <div>
                              <img src={require("../assets/img/team-2.jpg")} class="avatar avatar-sm me-3 border-radius-lg" alt="user1" />
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                              <h6 class="mb-0 text-sm">John Michael</h6>
                              <p class="text-xs text-secondary mb-0">john@creative-tim.com</p>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-xs font-weight-bold mb-0">Manager</p>
                          <p class="text-xs text-secondary mb-0">Organization</p>
                        </td>
                        <td class="align-middle text-center text-sm">
                          <span class="badge badge-sm bg-gradient-success">Online</span>
                        </td>
                        <td class="align-middle text-center">
                          <span class="text-secondary text-xs font-weight-bold">23/04/18</span>
                        </td>
                        <td class="align-middle">
                          <a class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            Edit
                          </a>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2 py-1">
                            <div>
                              <img src={require("../assets/img/team-3.jpg")} class="avatar avatar-sm me-3 border-radius-lg" alt="user2" />
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                              <h6 class="mb-0 text-sm">Alexa Liras</h6>
                              <p class="text-xs text-secondary mb-0">alexa@creative-tim.com</p>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-xs font-weight-bold mb-0">Programator</p>
                          <p class="text-xs text-secondary mb-0">Developer</p>
                        </td>
                        <td class="align-middle text-center text-sm">
                          <span class="badge badge-sm bg-gradient-secondary">Offline</span>
                        </td>
                        <td class="align-middle text-center">
                          <span class="text-secondary text-xs font-weight-bold">11/01/19</span>
                        </td>
                        <td class="align-middle">
                          <a class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            Edit
                          </a>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2 py-1">
                            <div>
                              <img src={require("../assets/img/team-4.jpg")} class="avatar avatar-sm me-3 border-radius-lg" alt="user3" />
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                              <h6 class="mb-0 text-sm">Laurent Perrier</h6>
                              <p class="text-xs text-secondary mb-0">laurent@creative-tim.com</p>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-xs font-weight-bold mb-0">Executive</p>
                          <p class="text-xs text-secondary mb-0">Projects</p>
                        </td>
                        <td class="align-middle text-center text-sm">
                          <span class="badge badge-sm bg-gradient-success">Online</span>
                        </td>
                        <td class="align-middle text-center">
                          <span class="text-secondary text-xs font-weight-bold">19/09/17</span>
                        </td>
                        <td class="align-middle">
                          <a class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            Edit
                          </a>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2 py-1">
                            <div>
                              <img src={require("../assets/img/team-3.jpg")} class="avatar avatar-sm me-3 border-radius-lg" alt="user4" />
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                              <h6 class="mb-0 text-sm">Michael Levi</h6>
                              <p class="text-xs text-secondary mb-0">michael@creative-tim.com</p>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-xs font-weight-bold mb-0">Programator</p>
                          <p class="text-xs text-secondary mb-0">Developer</p>
                        </td>
                        <td class="align-middle text-center text-sm">
                          <span class="badge badge-sm bg-gradient-success">Online</span>
                        </td>
                        <td class="align-middle text-center">
                          <span class="text-secondary text-xs font-weight-bold">24/12/08</span>
                        </td>
                        <td class="align-middle">
                          <a class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            Edit
                          </a>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2 py-1">
                            <div>
                              <img src={require("../assets/img/team-2.jpg")} class="avatar avatar-sm me-3 border-radius-lg" alt="user5" />
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                              <h6 class="mb-0 text-sm">Richard Gran</h6>
                              <p class="text-xs text-secondary mb-0">richard@creative-tim.com</p>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-xs font-weight-bold mb-0">Manager</p>
                          <p class="text-xs text-secondary mb-0">Executive</p>
                        </td>
                        <td class="align-middle text-center text-sm">
                          <span class="badge badge-sm bg-gradient-secondary">Offline</span>
                        </td>
                        <td class="align-middle text-center">
                          <span class="text-secondary text-xs font-weight-bold">04/10/21</span>
                        </td>
                        <td class="align-middle">
                          <a class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            Edit
                          </a>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2 py-1">
                            <div>
                              <img src={require("../assets/img/team-4.jpg")} class="avatar avatar-sm me-3 border-radius-lg" alt="user6" />
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                              <h6 class="mb-0 text-sm">Miriam Eric</h6>
                              <p class="text-xs text-secondary mb-0">miriam@creative-tim.com</p>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-xs font-weight-bold mb-0">Programator</p>
                          <p class="text-xs text-secondary mb-0">Developer</p>
                        </td>
                        <td class="align-middle text-center text-sm">
                          <span class="badge badge-sm bg-gradient-secondary">Offline</span>
                        </td>
                        <td class="align-middle text-center">
                          <span class="text-secondary text-xs font-weight-bold">14/09/20</span>
                        </td>
                        <td class="align-middle">
                          <a class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            Edit
                          </a>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="card my-4">
              <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                  <h6 class="text-white text-capitalize ps-3">Projects table</h6>
                </div>
              </div>
              <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                  <table class="table align-items-center justify-content-center mb-0">
                    <thead>
                      <tr>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Project</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Budget</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Status</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder text-center opacity-7 ps-2">Completion</th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>
                          <div class="d-flex px-2">
                            <div>
                              <img src={require("../assets/img/small-logos/logo-asana.svg")} class="avatar avatar-sm rounded-circle me-2" alt="spotify" />
                            </div>
                            <div class="my-auto">
                              <h6 class="mb-0 text-sm">Asana</h6>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-sm font-weight-bold mb-0">$2,500</p>
                        </td>
                        <td>
                          <span class="text-xs font-weight-bold">working</span>
                        </td>
                        <td class="align-middle text-center">
                          <div class="d-flex align-items-center justify-content-center">
                            <span class="me-2 text-xs font-weight-bold">60%</span>
                            <div>
                              <div class="progress">
                                <div class="progress-bar bg-gradient-info" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style={{width: '60%'}}></div>
                              </div>
                            </div>
                          </div>
                        </td>
                        <td class="align-middle">
                          <button class="btn btn-link text-secondary mb-0">
                            <i class="fa fa-ellipsis-v text-xs"></i>
                          </button>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2">
                            <div>
                              <img src={require("../assets/img/small-logos/github.svg")} class="avatar avatar-sm rounded-circle me-2" alt="invision" />
                            </div>
                            <div class="my-auto">
                              <h6 class="mb-0 text-sm">Github</h6>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-sm font-weight-bold mb-0">$5,000</p>
                        </td>
                        <td>
                          <span class="text-xs font-weight-bold">done</span>
                        </td>
                        <td class="align-middle text-center">
                          <div class="d-flex align-items-center justify-content-center">
                            <span class="me-2 text-xs font-weight-bold">100%</span>
                            <div>
                              <div class="progress">
                                <div class="progress-bar bg-gradient-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style={{width: '100%'}}></div>
                              </div>
                            </div>
                          </div>
                        </td>
                        <td class="align-middle">
                          <button class="btn btn-link text-secondary mb-0" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-ellipsis-v text-xs"></i>
                          </button>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2">
                            <div>
                              <img src={require("../assets/img/small-logos/logo-atlassian.svg")} class="avatar avatar-sm rounded-circle me-2" alt="jira" />
                            </div>
                            <div class="my-auto">
                              <h6 class="mb-0 text-sm">Atlassian</h6>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-sm font-weight-bold mb-0">$3,400</p>
                        </td>
                        <td>
                          <span class="text-xs font-weight-bold">canceled</span>
                        </td>
                        <td class="align-middle text-center">
                          <div class="d-flex align-items-center justify-content-center">
                            <span class="me-2 text-xs font-weight-bold">30%</span>
                            <div>
                              <div class="progress">
                                <div class="progress-bar bg-gradient-danger" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="30" style={{width: '30%'}}></div>
                              </div>
                            </div>
                          </div>
                        </td>
                        <td class="align-middle">
                          <button class="btn btn-link text-secondary mb-0" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-ellipsis-v text-xs"></i>
                          </button>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2">
                            <div>
                              <img src={require("../assets/img/small-logos/bootstrap.svg")} class="avatar avatar-sm rounded-circle me-2" alt="webdev" />
                            </div>
                            <div class="my-auto">
                              <h6 class="mb-0 text-sm">Bootstrap</h6>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-sm font-weight-bold mb-0">$14,000</p>
                        </td>
                        <td>
                          <span class="text-xs font-weight-bold">working</span>
                        </td>
                        <td class="align-middle text-center">
                          <div class="d-flex align-items-center justify-content-center">
                            <span class="me-2 text-xs font-weight-bold">80%</span>
                            <div>
                              <div class="progress">
                                <div class="progress-bar bg-gradient-info" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="80" style={{width: '80%'}}></div>
                              </div>
                            </div>
                          </div>
                        </td>
                        <td class="align-middle">
                          <button class="btn btn-link text-secondary mb-0" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-ellipsis-v text-xs"></i>
                          </button>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2">
                            <div>
                              <img src={require("../assets/img/small-logos/logo-slack.svg")} class="avatar avatar-sm rounded-circle me-2" alt="slack" />
                            </div>
                            <div class="my-auto">
                              <h6 class="mb-0 text-sm">Slack</h6>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-sm font-weight-bold mb-0">$1,000</p>
                        </td>
                        <td>
                          <span class="text-xs font-weight-bold">canceled</span>
                        </td>
                        <td class="align-middle text-center">
                          <div class="d-flex align-items-center justify-content-center">
                            <span class="me-2 text-xs font-weight-bold">0%</span>
                            <div>
                              <div class="progress">
                                <div class="progress-bar bg-gradient-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="0" style={{width: '0%'}}></div>
                              </div>
                            </div>
                          </div>
                        </td>
                        <td class="align-middle">
                          <button class="btn btn-link text-secondary mb-0" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-ellipsis-v text-xs"></i>
                          </button>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="d-flex px-2">
                            <div>
                              <img src={require("../assets/img/small-logos/devto.svg")} class="avatar avatar-sm rounded-circle me-2" alt="xd" />
                            </div>
                            <div class="my-auto">
                              <h6 class="mb-0 text-sm">Devto</h6>
                            </div>
                          </div>
                        </td>
                        <td>
                          <p class="text-sm font-weight-bold mb-0">$2,300</p>
                        </td>
                        <td>
                          <span class="text-xs font-weight-bold">done</span>
                        </td>
                        <td class="align-middle text-center">
                          <div class="d-flex align-items-center justify-content-center">
                            <span class="me-2 text-xs font-weight-bold">100%</span>
                            <div>
                              <div class="progress">
                                <div class="progress-bar bg-gradient-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style={{width: '100%'}}></div>
                              </div>
                            </div>
                          </div>
                        </td>
                        <td class="align-middle">
                          <button class="btn btn-link text-secondary mb-0" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-ellipsis-v text-xs"></i>
                          </button>
                        </td>
                      </tr>
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
