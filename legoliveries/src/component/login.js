import React, { useState } from "react";
import { useNavigate} from 'react-router-dom';
import Footer from "./Footer";

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


const Login = ({setToken}) => {
    const navigate = useNavigate();
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();

    const handleSubmit = async e => {
        e.preventDefault();

        const infouser = { username, password }
        console.log(infouser);
        {/*const token = await loginUser({
            username,
            password
        });
        setToken(token);

        navigate('/log/User?=' + id)*/}

        navigate('/');
    }

    return (
    <div className="App">
    <div class="container">

        <section class="section-main padding-y">
            <main class="card">
                    <div class="card-body">
                        <form accept-charset="utf-8" onSubmit={handleSubmit}> {/*method="post"*/}
                            <div className="form-group">
                                <b><label htmlFor="name" style={{border: 1}}>User Name or Email</label></b>
                                <input className="form-control input-filled-valid" id="name" name="name" required=""
                                       type="text" value={username} onChange={(e) => setUserName(e.target.value)}/>
                            </div>
                            <div className="form-group">
                                <b><label htmlFor="password">Password</label></b>
                                <input className="form-control input-filled-valid" id="password" name="password"
                                       required="" type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                            </div>
                            <div className="row pt-3">
                                 <div className="col-md-6">
                                    <a className="float-left align-text-to-button" href="/reset_password">
                                        Forgot your password?
                                    </a>
                                </div>
                                <div className="col-md-6">
                                    <input className="btn btn-md btn-primary btn-outlined float-right" id="_submit"
                                           name="_submit" type="submit" value="Submit" />
                                </div>
                            </div>
                            {/*  <input id="nonce" name="nonce" type="hidden"
                                   value="4de40d521855b547366a912e4e1bc559f4d858b92ab4e1779ecaa67e1efcfaf8"/> */}
                        </form>
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

export default Login;