import React, { useState } from "react";
import { useNavigate} from 'react-router-dom';
import '../App.css';
import Footer from "./Footer";
import Navbar from "./Navbar";
import axios from 'axios';

{/*async function loginUser(credentials) {
    return fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}*/}


const Register = () => {
    const navigate = useNavigate();
    const [username, setUserName] = useState();
    const [password1, setPassword1] = useState();
    const [password2, setPassword2] = useState();
    const [email, setEmail] = useState();
    const [error_message, setError] = useState("");


    const handleSubmit = async e => {
        e.preventDefault();

        setError("")

        if(password1 === password2){

            axios.post('http://localhost:8080/clients/register', {
                username: username,
                email: email,
                password: password1
            })
            .then((response) => {
                console.log(response);
                setError("")
                navigate("/")
            })
            .catch((error) => {
                console.log(error);
                setError("ERROR during sign up")
                return
            });

        }
        
        setError("Passwords not equal")
        return
    }

    return (
        <div>
            <Navbar />
        <div class='container'>
            <section className="section-main padding-y">
                <main className="card">
        <div className="row">
            <div className="col-md-6 offset-md-3">
                <form method="post" accept-charset="utf-8" autoComplete="off" role="form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <b><label>User Name</label></b>
                        <input className="form-control" id="name" name="name" required type="text" value={username} onChange={(e) => setUserName(e.target.value)}/>
                            <small className="form-text text-muted">
                                Your username on the site
                            </small>
                    </div>
                    <div className="form-group">
                        <b><label>Email</label></b>
                        <input className="form-control input-filled-valid" id="email" name="email" required
                               type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                            <small className="form-text text-muted">
                                Never shown to the public
                            </small>
                    </div>
                    <div className="form-group">
                        <b><label>Password</label></b>
                        <input className="form-control input-filled-valid" id="password1" name="password1" required
                               type="password" value={password1} onChange={(e) => setPassword1(e.target.value)} />
                            <small className="form-text text-muted">
                                Password used to log into your account
                            </small>
                    </div>

                    <div className="form-group">
                        <b><label>Password Again</label></b>
                        <input className="form-control input-filled-valid" id="password2" name="password2" required
                               type="password" value={password2} onChange={(e) => setPassword2(e.target.value)} />
                            <small className="form-text text-muted">
                                Confirm your Password
                            </small>
                    </div>
                        {/* <input id="nonce" name="nonce" type="hidden"
                           value="bdaa00bce142ab38fbe1761a767ab97df9db0e34e260defac78ee197fd818cbf"/> */}

                        {error_message !== "" ? <p id="error">{error_message}</p> : <></>}

                        <div className="row pt-3">
                            <div className="col-md-12">
                                <input className="btn btn-md btn-primary btn-outlined float-right" id="_submit"
                                       name="_submit" type="submit" value="Submit"/>
                            </div>
                        </div>


                </form>

            </div>

        </div>
                </main>
            </section>
        </div>
            <Footer />
        </div>
    );
}

{/*Login.propTypes = {
    setToken: PropTypes.func.isRequired
} */}

export default Register;