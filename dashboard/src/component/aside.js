import React, {Component} from 'react';
import logo from '../assets//img/favicon.png';
import '../App.css';
import { Link } from 'react-router-dom';

class Aside extends Component {

    logout() {
        localStorage.setItem('email', null);
        localStorage.setItem('password', null);
    }

    render() {

        let email = localStorage.getItem('email');
        let password = localStorage.getItem('password');
        
        return (
            <aside class="sidenav navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-3   bg-gradient-dark" id="sidenav-main">
        <div class="sidenav-header">
          <i class="fas fa-times p-3 cursor-pointer text-white opacity-5 position-absolute end-0 top-0 d-none d-xl-none" aria-hidden="true" id="iconSidenav"></i>
          <a class="navbar-brand m-0" target="_blank">
            <img src={logo} class="navbar-brand-img h-100" alt="main_logo" />
            <span class="ms-1 font-weight-bold text-white">LegoLiveries</span>
          </a>
        </div>
        <hr class="horizontal light mt-0 mb-2" />
        <div class="collapse navbar-collapse  w-auto  max-height-vh-100" id="sidenav-collapse-main">
          <ul class="navbar-nav">
            <li class="nav-item">
              <Link to="/management">
                <a class="nav-link text-white bg-gradient-primary">
                  <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                    <i class="material-icons opacity-10">dashboard</i>
                  </div>
                  <span class="nav-link-text ms-1">Dashboard</span>
                </a>
              </Link>
              {!(email === undefined || email === "null" || email === null) ? 
                <>
                <li class="nav-item">
                <Link to="/management">
                    <a class="nav-link text-white ">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">dashboard</i>
                    </div>
                    <span class="nav-link-text ms-1">Management</span>
                    </a>
                </Link>
                </li>
                </> : 
                null}
            </li>
            <li class="nav-item mt-3">
              <h6 class="ps-4 ms-2 text-uppercase text-xs text-white font-weight-bolder opacity-8">Account pages</h6>
            </li>
            {!(email === undefined || email === "null" || email === null) ? 
            <>
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
                    <a class="nav-link text-white " onClick={this.logout}>
                        <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">person</i>
                        </div>
                        <span class="nav-link-text ms-1">Log Out</span>
                    </a>
                </Link>
            </li>
            </> : 
            <>
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
            </>}
          
          </ul>
        </div>
      </aside>
        )
    }

}

export default Aside;