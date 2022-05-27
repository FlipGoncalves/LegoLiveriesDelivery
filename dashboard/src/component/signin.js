import React, {Component} from 'react';
import logo from '../assets/../assets/img/favicon.png';
import '../App.css';
import '../assets/css/material-dashboard.css';
import '../assets/css/material-dashboard.css.map';
import '../assets/css/material-dashboard.min.css';
import '../assets/css/nucleo-svg.css';
import '../assets/css/nucleo-icons.css';
import { Link } from 'react-router-dom';


class SignIn extends Component {
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



      <main class="main-content  mt-0">
        <div class="page-header align-items-start min-vh-100">
            <div class="container my-auto">
                <div class="row">
                <div class="col-lg-4 col-md-8 col-12 mx-auto">
                    <div class="card z-index-0 fadeIn3 fadeInBottom">
                    <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                        <div class="bg-gradient-primary shadow-primary border-radius-lg py-3 pe-1">
                        <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">Sign in</h4>
                        <div class="row mt-3">
                            <div class="col-2 text-center ms-auto">
                            <a class="btn btn-link px-3" href="javascript:;">
                                <i class="fa fa-facebook text-white text-lg"></i>
                            </a>
                            </div>
                            <div class="col-2 text-center px-1">
                            <a class="btn btn-link px-3" href="javascript:;">
                                <i class="fa fa-github text-white text-lg"></i>
                            </a>
                            </div>
                            <div class="col-2 text-center me-auto">
                            <a class="btn btn-link px-3" href="javascript:;">
                                <i class="fa fa-google text-white text-lg"></i>
                            </a>
                            </div>
                        </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <form role="form" class="text-start">
                        <div class="input-group input-group-outline my-3">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control"/>
                        </div>
                        <div class="input-group input-group-outline mb-3">
                            <label class="form-label">Password</label>
                            <input type="password" class="form-control"/>
                        </div>
                        <div class="form-check form-switch d-flex align-items-center mb-3">
                            <input class="form-check-input" type="checkbox" id="rememberMe"/>
                            <label class="form-check-label mb-0 ms-2" for="rememberMe">Remember me</label>
                        </div>
                        <div class="text-center">
                            <button type="button" class="btn bg-gradient-primary w-100 my-4 mb-2">Sign in</button>
                        </div>
                        <p class="mt-4 text-sm text-center">
                            Don't have an account?
                            <a href=".pages/sign-up" class="text-primary text-gradient font-weight-bold">Sign up</a>
                        </p>
                        </form>
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

export default SignIn;
