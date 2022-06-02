import React, { useState } from "react";
import { useNavigate} from 'react-router-dom';
import '../App.css';
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


const ResetPassword = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState();

    const handleSubmit = async e => {
        e.preventDefault();

        const infouser = { email }
        console.log(infouser);

        // sendmail...

        navigate('/');
    }

    return (
        <div className="App">
            <div className="row">
                <div className="col-md-6 offset-md-3">
                    <div>


                    </div>


                    <form accept-charset="utf-8" autoComplete="off" role="form"  className="form-horizontal" onSubmit={handleSubmit}>
                        <div className="row">
                            <div className="col-md-12">
                                <p>Please provide the email address associated with your account below.</p>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="email">Email</label>
                            <input className="form-control" id="email" name="email" required type="email" value={email} onChange={(e) => setEmail(e.target.value)}/>
                        </div>
                        <div className="row">
                            <div className="col-md-6 offset-md-6">
                                <input className="btn btn-md btn-primary float-right" id="_submit" name="_submit"
                                       type="submit" value="Submit"/>
                            </div>
                        </div>
                        {/* <input id="nonce" name="nonce" type="hidden"
                               value="bdaa00bce142ab38fbe1761a767ab97df9db0e34e260defac78ee197fd818cbf"/> */}
                    </form>


                </div>
            </div>
            <Footer />
        </div>
    );
}

{/*Login.propTypes = {
    setToken: PropTypes.func.isRequired
} */}

export default ResetPassword;