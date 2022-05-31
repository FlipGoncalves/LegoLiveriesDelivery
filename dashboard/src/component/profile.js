import React, {Component} from 'react';
import logo from '../assets/../assets/img/favicon.png';
import '../App.css';
import '../assets/css/material-dashboard.css';
import '../assets/css/material-dashboard.css.map';
import '../assets/css/material-dashboard.min.css';
import '../assets/css/nucleo-svg.css';
import '../assets/css/nucleo-icons.css';
import { Link } from 'react-router-dom';


class Profile extends Component {
  render() {
    return (
      <div className="App">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"></meta>
        <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet"></link>
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
        <script async defer src="https://buttons.github.io/buttons.js"></script>

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"></meta>
        <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet"></link>
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
        <script async defer src="https://buttons.github.io/buttons.js"></script>

        <aside class="sidenav navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-3   bg-gradient-dark" id="sidenav-main">
        <div class="sidenav-header">
          <i class="fas fa-times p-3 cursor-pointer text-white opacity-5 position-absolute end-0 top-0 d-none d-xl-none" aria-hidden="true" id="iconSidenav"></i>
          <a class="navbar-brand m-0" href=" https://demos.creative-tim.com/material-dashboard/pages/dashboard " target="_blank">
            <img src={logo} class="navbar-brand-img h-100" alt="main_logo" />
            <span class="ms-1 font-weight-bold text-white">LegoLiveries</span>
          </a>
        </div>
        <hr class="horizontal light mt-0 mb-2" />
        <div class="collapse navbar-collapse  w-auto  max-height-vh-100" id="sidenav-collapse-main">
          <ul class="navbar-nav">
            <li class="nav-item">
              <Link to="/">
                <a class="nav-link text-white active bg-gradient-primary">
                  <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                    <i class="material-icons opacity-10">dashboard</i>
                  </div>
                  <span class="nav-link-text ms-1">Dashboard</span>
                </a>
              </Link>
            </li>
            <li class="nav-item mt-3">
              <h6 class="ps-4 ms-2 text-uppercase text-xs text-white font-weight-bolder opacity-8">Account pages</h6>
            </li>
            <li class="nav-item">
              <Link to="/profile">
                <a class="nav-link text-white ">
                  <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                    <i class="material-icons opacity-10">person</i>
                  </div>
                  <span class="nav-link-text ms-1">Profile</span>
                </a>
              </Link>
            </li>
            <li class="nav-item">
              <Link to="/sign-in">
                <a class="nav-link text-white ">
                  <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                    <i class="material-icons opacity-10">login</i>
                  </div>
                  <span class="nav-link-text ms-1">Sign In</span>
                </a>
              </Link>
            </li>
            <li class="nav-item">
              <Link to="/sign-up">
                <a class="nav-link text-white ">
                  <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                    <i class="material-icons opacity-10">assignment</i>
                  </div>
                  <span class="nav-link-text ms-1">Sign Up</span>
                </a>
              </Link>
            </li>
          </ul>
        </div>
      </aside>
      <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">
        <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" navbar-scroll="true">
          <div class="container-fluid py-1 px-3">
            <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
              <div class="ms-md-auto pe-md-3 d-flex align-items-center">
                <div class="input-group input-group-outline">
                  <label class="form-label">Type here...</label>
                  <input type="text" class="form-control" />
                </div>
              </div>
              <ul class="navbar-nav  justify-content-end">
                <li class="nav-item d-flex align-items-center">
                  <a class="nav-link text-body font-weight-bold px-0">
                    <i class="fa fa-user me-sm-1"></i>
                    <span class="d-sm-inline d-none">Sign In</span>
                  </a>
                </li>
                <li class="nav-item d-xl-none ps-3 d-flex align-items-center">
                  <a class="nav-link text-body p-0" id="iconNavbarSidenav">
                    <div class="sidenav-toggler-inner">
                      <i class="sidenav-toggler-line"></i>
                      <i class="sidenav-toggler-line"></i>
                      <i class="sidenav-toggler-line"></i>
                    </div>
                  </a>
                </li>
                <li class="nav-item px-3 d-flex align-items-center">
                  <a class="nav-link text-body p-0">
                    <i class="fa fa-cog fixed-plugin-button-nav cursor-pointer"></i>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </nav>

        <div class="container-fluid px-2 px-md-4">
            <div class="page-header min-height-300 border-radius-xl mt-4">
                <span class="mask  bg-gradient-primary  opacity-6"></span>
            </div>
            <div class="card card-body mx-3 mx-md-4 mt-n6">
                <div class="row gx-4 mb-2">
                <div class="col-auto">
                    <div class="avatar avatar-xl position-relative">
                    <img src={require("../assets/img/bruce-mars.jpg")} alt="profile_image" class="w-100 border-radius-lg shadow-sm" />
                    </div>
                </div>
                <div class="col-auto my-auto">
                    <div class="h-100">
                    <h5 class="mb-1">
                        Richard Davis
                    </h5>
                    <p class="mb-0 font-weight-normal text-sm">
                        CEO / Co-Founder
                    </p>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 my-sm-auto ms-sm-auto me-sm-0 mx-auto mt-3">
                    <div class="nav-wrapper position-relative end-0">
                    <ul class="nav nav-pills nav-fill p-1" role="tablist">
                        <li class="nav-item">
                        <a class="nav-link mb-0 px-0 py-1 active " data-bs-toggle="tab"  role="tab" aria-selected="true">
                            <i class="material-icons text-lg position-relative">home</i>
                            <span class="ms-1">App</span>
                        </a>
                        </li>
                        <li class="nav-item">
                        <a class="nav-link mb-0 px-0 py-1 " data-bs-toggle="tab"  role="tab" aria-selected="false">
                            <i class="material-icons text-lg position-relative">email</i>
                            <span class="ms-1">Messages</span>
                        </a>
                        </li>
                        <li class="nav-item">
                        <a class="nav-link mb-0 px-0 py-1 " data-bs-toggle="tab"  role="tab" aria-selected="false">
                            <i class="material-icons text-lg position-relative">settings</i>
                            <span class="ms-1">Settings</span>
                        </a>
                        </li>
                    </ul>
                    </div>
                </div>
                </div>
                <div class="row">
                <div class="row">
                    <div class="col-12 col-xl-4">
                    <div class="card card-plain h-100">
                        <div class="card-header pb-0 p-3">
                        <h6 class="mb-0">Platform Settings</h6>
                        </div>
                        <div class="card-body p-3">
                        <h6 class="text-uppercase text-body text-xs font-weight-bolder">Account</h6>
                        <ul class="list-group">
                            <li class="list-group-item border-0 px-0">
                            <div class="form-check form-switch ps-0">
                                <input class="form-check-input ms-auto" type="checkbox" id="flexSwitchCheckDefault" defaultChecked />
                                <label class="form-check-label text-body ms-3 text-truncate w-80 mb-0">Email me when someone follows me</label>
                            </div>
                            </li>
                            <li class="list-group-item border-0 px-0">
                            <div class="form-check form-switch ps-0">
                                <input class="form-check-input ms-auto" type="checkbox" id="flexSwitchCheckDefault1" />
                                <label class="form-check-label text-body ms-3 text-truncate w-80 mb-0">Email me when someone answers on my post</label>
                            </div>
                            </li>
                            <li class="list-group-item border-0 px-0">
                            <div class="form-check form-switch ps-0">
                                <input class="form-check-input ms-auto" type="checkbox" id="flexSwitchCheckDefault2" defaultChecked />
                                <label class="form-check-label text-body ms-3 text-truncate w-80 mb-0">Email me when someone mentions me</label>
                            </div>
                            </li>
                        </ul>
                        <h6 class="text-uppercase text-body text-xs font-weight-bolder mt-4">Application</h6>
                        <ul class="list-group">
                            <li class="list-group-item border-0 px-0">
                            <div class="form-check form-switch ps-0">
                                <input class="form-check-input ms-auto" type="checkbox" id="flexSwitchCheckDefault3" />
                                <label class="form-check-label text-body ms-3 text-truncate w-80 mb-0">New launches and projects</label>
                            </div>
                            </li>
                            <li class="list-group-item border-0 px-0">
                            <div class="form-check form-switch ps-0">
                                <input class="form-check-input ms-auto" type="checkbox" id="flexSwitchCheckDefault4" defaultChecked />
                                <label class="form-check-label text-body ms-3 text-truncate w-80 mb-0">Monthly product updates</label>
                            </div>
                            </li>
                            <li class="list-group-item border-0 px-0 pb-0">
                            <div class="form-check form-switch ps-0">
                                <input class="form-check-input ms-auto" type="checkbox" id="flexSwitchCheckDefault5" />
                                <label class="form-check-label text-body ms-3 text-truncate w-80 mb-0">Subscribe to newsletter</label>
                            </div>
                            </li>
                        </ul>
                        </div>
                    </div>
                    </div>
                    <div class="col-12 col-xl-4">
                    <div class="card card-plain h-100">
                        <div class="card-header pb-0 p-3">
                        <div class="row">
                            <div class="col-md-8 d-flex align-items-center">
                            <h6 class="mb-0">Profile Information</h6>
                            </div>
                            <div class="col-md-4 text-end">
                            <a >
                                <i class="fas fa-user-edit text-secondary text-sm" data-bs-toggle="tooltip" data-bs-placement="top" title="Edit Profile"></i>
                            </a>
                            </div>
                        </div>
                        </div>
                        <div class="card-body p-3">
                        <p class="text-sm">
                            Hi, I’m Alec Thompson, Decisions: If you can’t decide, the answer is no. If two equally difficult paths, choose the one more painful in the short term (pain avoidance is creating an illusion of equality).
                        </p>
                        <hr class="horizontal gray-light my-4" />
                        <ul class="list-group">
                            <li class="list-group-item border-0 ps-0 pt-0 text-sm"><strong class="text-dark">Full Name:</strong> &nbsp; Alec M. Thompson</li>
                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Mobile:</strong> &nbsp; (44) 123 1234 123</li>
                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Email:</strong> &nbsp; alecthompson@mail.com</li>
                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Location:</strong> &nbsp; USA</li>
                            <li class="list-group-item border-0 ps-0 pb-0">
                            <strong class="text-dark text-sm">Social:</strong> &nbsp;
                            <a class="btn btn-facebook btn-simple mb-0 ps-1 pe-2 py-0" >
                                <i class="fab fa-facebook fa-lg"></i>
                            </a>
                            <a class="btn btn-twitter btn-simple mb-0 ps-1 pe-2 py-0" >
                                <i class="fab fa-twitter fa-lg"></i>
                            </a>
                            <a class="btn btn-instagram btn-simple mb-0 ps-1 pe-2 py-0" >
                                <i class="fab fa-instagram fa-lg"></i>
                            </a>
                            </li>
                        </ul>
                        </div>
                    </div>
                    </div>
                    <div class="col-12 col-xl-4">
                    <div class="card card-plain h-100">
                        <div class="card-header pb-0 p-3">
                        <h6 class="mb-0">Conversations</h6>
                        </div>
                        <div class="card-body p-3">
                        <ul class="list-group">
                            <li class="list-group-item border-0 d-flex align-items-center px-0 mb-2 pt-0">
                            <div class="avatar me-3">
                                <img src={require("../assets/img/kal-visuals-square.jpg")} alt="kal" class="border-radius-lg shadow" />
                            </div>
                            <div class="d-flex align-items-start flex-column justify-content-center">
                                <h6 class="mb-0 text-sm">Sophie B.</h6>
                                <p class="mb-0 text-xs">Hi! I need more information..</p>
                            </div>
                            <a class="btn btn-link pe-3 ps-0 mb-0 ms-auto w-25 w-md-auto" >Reply</a>
                            </li>
                            <li class="list-group-item border-0 d-flex align-items-center px-0 mb-2">
                            <div class="avatar me-3">
                                <img src={require("../assets/img/marie.jpg")} alt="kal" class="border-radius-lg shadow" />
                            </div>
                            <div class="d-flex align-items-start flex-column justify-content-center">
                                <h6 class="mb-0 text-sm">Anne Marie</h6>
                                <p class="mb-0 text-xs">Awesome work, can you..</p>
                            </div>
                            <a class="btn btn-link pe-3 ps-0 mb-0 ms-auto w-25 w-md-auto" >Reply</a>
                            </li>
                            <li class="list-group-item border-0 d-flex align-items-center px-0 mb-2">
                            <div class="avatar me-3">
                                <img src={require("../assets/img/ivana-square.jpg")} alt="kal" class="border-radius-lg shadow" />
                            </div>
                            <div class="d-flex align-items-start flex-column justify-content-center">
                                <h6 class="mb-0 text-sm">Ivanna</h6>
                                <p class="mb-0 text-xs">About files I can..</p>
                            </div>
                            <a class="btn btn-link pe-3 ps-0 mb-0 ms-auto w-25 w-md-auto" >Reply</a>
                            </li>
                            <li class="list-group-item border-0 d-flex align-items-center px-0 mb-2">
                            <div class="avatar me-3">
                                <img src={require("../assets/img/team-4.jpg")} alt="kal" class="border-radius-lg shadow" />
                            </div>
                            <div class="d-flex align-items-start flex-column justify-content-center">
                                <h6 class="mb-0 text-sm">Peterson</h6>
                                <p class="mb-0 text-xs">Have a great afternoon..</p>
                            </div>
                            <a class="btn btn-link pe-3 ps-0 mb-0 ms-auto w-25 w-md-auto" >Reply</a>
                            </li>
                            <li class="list-group-item border-0 d-flex align-items-center px-0">
                            <div class="avatar me-3">
                                <img src={require("../assets/img/team-3.jpg")} alt="kal" class="border-radius-lg shadow" />
                            </div>
                            <div class="d-flex align-items-start flex-column justify-content-center">
                                <h6 class="mb-0 text-sm">Nick Daniel</h6>
                                <p class="mb-0 text-xs">Hi! I need more information..</p>
                            </div>
                            <a class="btn btn-link pe-3 ps-0 mb-0 ms-auto w-25 w-md-auto" >Reply</a>
                            </li>
                        </ul>
                        </div>
                    </div>
                    </div>
                    <div class="col-12 mt-4">
                    <div class="mb-5 ps-3">
                        <h6 class="mb-1">Projects</h6>
                        <p class="text-sm">Architects design houses</p>
                    </div>
                    <div class="row">
                        <div class="col-xl-3 col-md-6 mb-xl-0 mb-4">
                        <div class="card card-blog card-plain">
                            <div class="card-header p-0 mt-n4 mx-3">
                            <a class="d-block shadow-xl border-radius-xl">
                                <img src={require("../assets/img/home-decor-1.jpg")} alt="img-blur-shadow" class="img-fluid shadow border-radius-xl" />
                            </a>
                            </div>
                            <div class="card-body p-3">
                            <p class="mb-0 text-sm">Project #2</p>
                            <a >
                                <h5>
                                Modern
                                </h5>
                            </a>
                            <p class="mb-4 text-sm">
                                As Uber works through a huge amount of internal management turmoil.
                            </p>
                            <div class="d-flex align-items-center justify-content-between">
                                <button type="button" class="btn btn-outline-primary btn-sm mb-0">View Project</button>
                                <div class="avatar-group mt-2">
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Elena Morison">
                                    <img alt="Image placeholder" src={require("../assets/img/team-1.jpg")} />
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Milly">
                                    <img alt="Image placeholder" src={require("../assets/img/team-2.jpg")} />
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Nick Daniel">
                                    <img alt="Image placeholder" src={require("../assets/img/team-3.jpg")} />
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Peterson">
                                    <img alt="Image placeholder" src={require("../assets/img/team-4.jpg")} />
                                </a>
                                </div>
                            </div>
                            </div>
                        </div>
                        </div>
                        <div class="col-xl-3 col-md-6 mb-xl-0 mb-4">
                        <div class="card card-blog card-plain">
                            <div class="card-header p-0 mt-n4 mx-3">
                            <a class="d-block shadow-xl border-radius-xl">
                                <img src={require("../assets/img/home-decor-2.jpg")} alt="img-blur-shadow" class="img-fluid shadow border-radius-lg" />
                            </a>
                            </div>
                            <div class="card-body p-3">
                            <p class="mb-0 text-sm">Project #1</p>
                            <a >
                                <h5>
                                Scandinavian
                                </h5>
                            </a>
                            <p class="mb-4 text-sm">
                                Music is something that every person has his or her own specific opinion about.
                            </p>
                            <div class="d-flex align-items-center justify-content-between">
                                <button type="button" class="btn btn-outline-primary btn-sm mb-0">View Project</button>
                                <div class="avatar-group mt-2">
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Nick Daniel">
                                    <img alt="Image placeholder" src={require("../assets/img/team-3.jpg")} />
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Peterson">
                                    <img alt="Image placeholder" src={require("../assets/img/team-4.jpg")} />
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Elena Morison">
                                    <img alt="Image placeholder" src={require("../assets/img/team-1.jpg")} />
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Milly">
                                    <img alt="Image placeholder" src={require("../assets/img/team-2.jpg")} />
                                </a>
                                </div>
                            </div>
                            </div>
                        </div>
                        </div>
                        <div class="col-xl-3 col-md-6 mb-xl-0 mb-4">
                        <div class="card card-blog card-plain">
                            <div class="card-header p-0 mt-n4 mx-3">
                            <a class="d-block shadow-xl border-radius-xl">
                                <img src={require("../assets/img/home-decor-3.jpg")} alt="img-blur-shadow" class="img-fluid shadow border-radius-xl" />
                            </a>
                            </div>
                            <div class="card-body p-3">
                            <p class="mb-0 text-sm">Project #3</p>
                            <a >
                                <h5>
                                Minimalist
                                </h5>
                            </a>
                            <p class="mb-4 text-sm">
                                Different people have different taste, and various types of music.
                            </p>
                            <div class="d-flex align-items-center justify-content-between">
                                <button type="button" class="btn btn-outline-primary btn-sm mb-0">View Project</button>
                                <div class="avatar-group mt-2">
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Peterson">
                                    <img alt="Image placeholder" src={require("../assets/img/team-4.jpg")}/>
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Nick Daniel">
                                    <img alt="Image placeholder" src={require("../assets/img/team-3.jpg")}/>
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Milly">
                                    <img alt="Image placeholder" src={require("../assets/img/team-2.jpg")}/>
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Elena Morison">
                                    <img alt="Image placeholder" src={require("../assets/img/team-1.jpg")}/>
                                </a>
                                </div>
                            </div>
                            </div>
                        </div>
                        </div>
                        <div class="col-xl-3 col-md-6 mb-xl-0 mb-4">
                        <div class="card card-blog card-plain">
                            <div class="card-header p-0 mt-n4 mx-3">
                            <a class="d-block shadow-xl border-radius-xl">
                                <img src="https://images.unsplash.com/photo-1606744824163-985d376605aa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80" alt="img-blur-shadow" class="img-fluid shadow border-radius-xl" />
                            </a>
                            </div>
                            <div class="card-body p-3">
                            <p class="mb-0 text-sm">Project #4</p>
                            <a >
                                <h5>
                                Gothic
                                </h5>
                            </a>
                            <p class="mb-4 text-sm">
                                Why would anyone pick blue over pink? Pink is obviously a better color.
                            </p>
                            <div class="d-flex align-items-center justify-content-between">
                                <button type="button" class="btn btn-outline-primary btn-sm mb-0">View Project</button>
                                <div class="avatar-group mt-2">
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Peterson">
                                    <img alt="Image placeholder" src={require("../assets/img/team-4.jpg")}/>
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Nick Daniel">
                                    <img alt="Image placeholder" src={require("../assets/img/team-3.jpg")}/>
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Milly">
                                    <img alt="Image placeholder" src={require("../assets/img/team-2.jpg")}/>
                                </a>
                                <a  class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Elena Morison">
                                    <img alt="Image placeholder" src={require("../assets/img/team-1.jpg")}/>
                                </a>
                                </div>
                            </div>
                            </div>
                        </div>
                        </div>
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

export default Profile;
