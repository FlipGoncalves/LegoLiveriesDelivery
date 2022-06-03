import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router,Routes, Route, Link } from 'react-router-dom';
import Login from './component/login';
import Home from './component/home'
import Register from "./component/Register";
import ResetPassword from "./component/ResetPassword";

class App extends Component {
    render() {
        return (
            <Router>
                    <Routes>
                        <Route exact path='/' element={< Home />}></Route>
                        <Route exact path='/login' element={< Login />}></Route>
                        <Route exact path='/register' element={<Register />}></Route>
                        {/*  <Route exact path='/reset_password' element={<ResetPassword />}></Route> */}
                    </Routes>
            </Router>
        );
    }   
}

export default App;
