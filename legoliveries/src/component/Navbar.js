import React from "react";
import { Link } from 'react-router-dom';

const Navbar = () => {
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
                                    <div class="widget-header mr-3">
                                            <div class="icon-area">
                                                <i class="fa fa-user"></i>
                                            </div>
                                            <Link to="/login">Login</Link>
                                        </div>
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