import React, {Component} from 'react';
import logo from '../assets/../assets/img/favicon.png';
import '../App.css';
import { Link, useNavigate } from 'react-router-dom';
import Aside from './aside';
import axios from 'axios';


class SignIn extends Component {

  constructor(props) {
    super(props);
    this.state = {
      error_message: ""
    };
  }

  render() {

    const {navigation} = this.props;

    const signin = () => {
      let email = document.getElementById('email').value;
      let pass = document.getElementById('pass').value;
  
      if (email === "" && pass === "") {
        this.setState({error_message: "Please insert Email and Password"})
        return false
      } else if (pass === "") {
        this.setState({error_message: "Please insert Password"})
        return false
      } else if (email === "") {
        this.setState({error_message: "Please insert Email"})
        return false
      }
      
      this.setState({error_message: ""})

      console.log("Login")
      
      axios.get('http://localhost:9001/api/managers/'+email)
      .then((response) => {
        console.log(response);
        let user = response.data["user"]
        if (user["password"] != pass) {
          this.setState({error_message: "Wrong Credentials"})
          return
        }

        this.setState({error_message: ""})
        localStorage.setItem('email', email)
        localStorage.setItem('password', pass)
        console.log("HERE")
        navigation("/")
      })
      .catch((error) => {
        console.log(error);
        this.setState({error_message: "ERROR during sign in"})
      });
    }

    return (
      <div className="App">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet"></link>

        <Aside></Aside>



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
                            <a class="btn btn-link px-3">
                                <i class="fa fa-facebook text-white text-lg"></i>
                            </a>
                            </div>
                            <div class="col-2 text-center px-1">
                            <a class="btn btn-link px-3">
                                <i class="fa fa-github text-white text-lg"></i>
                            </a>
                            </div>
                            <div class="col-2 text-center me-auto">
                            <a class="btn btn-link px-3">
                                <i class="fa fa-google text-white text-lg"></i>
                            </a>
                            </div>
                        </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <form role="form" class="text-start">
                        <div class="input-group input-group-outline my-3">
                            <input type="email" class="form-control" id="email" placeholder="Email"/>
                        </div>
                        <div class="input-group input-group-outline mb-3">
                            <input type="password" class="form-control" id="pass" placeholder="Password"/>
                        </div>
                        <div class="form-check form-switch d-flex align-items-center mb-3">
                            <input class="form-check-input" type="checkbox" id="rememberMe"/>
                            <label class="form-check-label mb-0 ms-2">Remember me</label>
                        </div>

                        {this.state.error_message !== "" ? <>
                          <div>
                            <label class="form-check-label mb-0 ms-2" style={{color: 'red'}} id="error">{this.state.error_message}</label>
                          </div>
                        </> : null}

                        <div class="text-center">
                          {/* <Link to="/"> */}
                            <button type="button" class="btn bg-gradient-primary w-100 my-4 mb-2" onClick={signin} id="signin">Sign in</button>
                          {/* </Link> */}
                        </div>
                        <p class="mt-4 text-sm text-center">
                            Don't have an account?
                            <Link to="/sign-up">
                              <a class="text-primary text-gradient font-weight-bold">Sign up</a>
                            </Link>
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

export default function(props) {
  const navigation = useNavigate();

  return <SignIn {...props} navigation={navigation} />;
}
