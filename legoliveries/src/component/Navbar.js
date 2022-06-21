import React from "react";
import { Link } from 'react-router-dom';

const Navbar = () => {

    function logout() {
        localStorage.setItem('user', null);
        console.log("LOG OUT")
    }
    
    return (
        <div className="App">
            <header class="section-header">
                    <section class="header-main border-bottom">
                        <div class="container">
                            <div class="row align-items-center">
                            <div class="col-xl-2 col-lg-3 col-md-12">
                                    <Link to="/">
                                        <a class="brand-wrap">
                                            LegoLiveries
                                        </a> 
                                    </Link>
                                </div>

                                <div class="col-xl-4 col-lg-4 col-md-6">
                                    <div class="widgets-wrap float-md-right">
                                        {localStorage.getItem('user') != 'null' && localStorage.getItem('user') != null ? 
                                        <>
                                        <div class="widget-header mr-3">
                                            <Link to="/orders">
                                                <a class="widget-view">
                                                    <div class="icon-area">
                                                        <i class="fa fa-store"></i> 
                                                    </div>
                                                    <small class="text"> Orders </small>
                                                </a>
                                            </Link>
                                        </div>
                                        <div class="widget-header mr-3">
                                            <a class="widget-view">
                                                <div class="icon-area">
                                                    <i class="fa fa-user"></i>
                                                </div>
                                                <small class="text"> My profile </small>
                                            </a>
                                        </div>
                                        <div class="widget-header mr-3">
                                            <Link to="/login">
                                                <button style={{outline: 'none', backgroundColor: 'transparent', border: 'none'}} onClick={logout}>
                                                    <a class="widget-view">
                                                        <div class="icon-area">
                                                            <i class="fa fa-user"></i>
                                                        </div>
                                                        <small class="text"> Log Out </small>
                                                    </a>
                                                </button>
                                            </Link>
                                        </div>
                                        </>: 
                                        <>
                                        <div class="widget-header mr-3">
                                            <Link to="/login">
                                                <a class="widget-view">
                                                    <div class="icon-area">
                                                        <i class="fa fa-user"></i>
                                                    </div>
                                                    <small class="text"> Login </small>
                                                </a>
                                            </Link>
                                        </div>
                                        <div class="widget-header mr-3">
                                            <Link to="/register">
                                                <a class="widget-view">
                                                    <div class="icon-area">
                                                        <i class="fa fa-user"></i>
                                                    </div>
                                                    <small class="text"> Register </small>
                                                </a>
                                            </Link>
                                        </div>
                                        </>}
                                    </div> 
                                </div>
                            </div>
                        </div> 
                    </section> 
                    
                    </header> 
        </div>
            );
}


export default Navbar;