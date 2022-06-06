import React, {Component} from 'react';
import logo from '../assets/../assets/img/favicon.png';
import '../App.css';
import Aside from '../component/aside'
import { Link } from 'react-router-dom';


class Profile extends Component {
  render() {
    return (
      <div className="App">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet"></link>

        <Aside></Aside>

        <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">

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
                                    {localStorage.getItem('email')}
                                </h5>
                                <p class="mb-0 font-weight-normal text-sm">
                                    Manager
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
                </div>
            </div>
        </main>
      </div>
    );
  }
}

export default Profile;
