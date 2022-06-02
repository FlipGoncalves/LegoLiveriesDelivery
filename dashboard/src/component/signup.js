import React, {Component} from 'react';
import logo from '../assets/../assets/img/favicon.png';
import '../App.css';
import '../assets/css/material-dashboard.css';
import '../assets/css/material-dashboard.css.map';
import '../assets/css/material-dashboard.min.css';
import '../assets/css/nucleo-svg.css';
import '../assets/css/nucleo-icons.css';
import { Link, useNavigate } from 'react-router-dom';
import Aside from './aside';


class SignUp extends Component {

  constructor(props) {
    super(props);
    this.state = {
      error_message: ""
    };
  }

  render() {

    const {navigation} = this.props;

    const signup = (event) => {
      event.preventDefault();

      let email = document.getElementById('email').value;
      let pass = document.getElementById('pass').value;
      let name = document.getElementById('name').value;
  
      if (name === "") {
        this.setState({error_message: "Please insert Name"})
        return
      } else if (email === "") {
        this.setState({error_message: "Please insert Email"})
        return
      } else if (pass === "") {
        this.setState({error_message: "Please insert Password"})
        return
      }

      console.log("Register")

      let formData = new FormData();
      formData.append("email", email);
      formData.append("username", name);
      formData.append("password", pass);   

      let resp = fetch('http://localhost:8080/api/register', {
          method: 'POST',
          body: JSON.stringify({
            email: formData.email,
            username: formData.username,
            password: formData.password
          }),
      }).then((data) => {
          console.log(data)
          if (data.status === 200) {
              this.setState({error_message: ""})
              this.setState({items: []})
              console.log("HERE")
              // navigation("/")
          } else {
              this.setState({error_message: "ERROR during sign up"})
          }
      })

      this.setState({error_message: "ERROR during sign up"})

    }

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

        <Aside></Aside>



      <main class="main-content  mt-0">
        <section>
        <div class="page-header min-vh-100">
            <div class="container">
            <div class="row">
                <div class="col-xl-4 col-lg-5 col-md-7 d-flex flex-column ms-auto me-auto ms-lg-auto me-lg-5">
                <div class="card card-plain">
                    <div class="card-header">
                    <h4 class="font-weight-bolder">Sign Up</h4>
                    <p class="mb-0">Enter your email and password to register</p>
                    </div>
                    <div class="card-body">
                    <form role="form" onSubmit={signup} method="POST">
                        <div class="input-group input-group-outline mb-3">
                        <input type="text" class="form-control" id="name" placeholder="Name"/>
                        </div>
                        <div class="input-group input-group-outline mb-3">
                        <input type="email" class="form-control" id="email" placeholder="Email"/>
                        </div>
                        <div class="input-group input-group-outline mb-3">
                        <input type="password" class="form-control" id="pass" placeholder="Password"/>
                        </div>

                        {this.state.error_message !== "" ? <>
                          <div>
                            <label class="form-check-label mb-0 ms-2" style={{color: 'red'}}>{this.state.error_message}</label>
                          </div>
                        </> : null}

                        <div class="text-center">
                        <button type="submit" class="btn btn-lg bg-gradient-primary btn-lg w-100 mt-4 mb-0">Sign Up</button>
                        </div>
                    </form>
                    </div>
                    <div class="card-footer text-center pt-0 px-lg-2 px-1">
                    <p class="mb-2 text-sm mx-auto">
                        Already have an account?
                        <Link to="/sign-in">
                          <a class="text-primary text-gradient font-weight-bold">Sign in</a>
                        </Link>
                    </p>
                    </div>
                </div>
                </div>
                <div class="col-6 d-lg-flex d-none h-100 my-auto pe-0 position-absolute top-0 start-0 text-center justify-content-center flex-column">
                <div class="position-relative bg-gradient-primary h-100 m-3 px-7 border-radius-lg d-flex flex-column justify-content-center">
                    <img src={require("../assets/img/illustration-signup.webp")} style={{backgroundSize: "cover"}}></img>
                </div>
                </div>
            </div>
            </div>
        </div>
        </section>
        </main>
      </div>
    );
  }
}

export default function(props) {
  const navigation = useNavigate();

  return <SignUp {...props} navigation={navigation} />;
}