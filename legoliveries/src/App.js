import React, { Component } from 'react';
import './App.css';
import { Link } from 'react-router-dom';

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
          items: [],
          count: 1
        };
      }

    render() {

        const RequestMapping = () => {
        
            let resp = fetch('http://localhost:8080/lego/all_legos', {
                method: 'GET'
            }).then((data) => {
                this.setState({items: []})
                data.json().then((list) => {
                    let newArray = []
                    list.forEach((item) => {
                        newArray.push(<div class="col-xl-2 col-lg-3 col-md-4 col-6">
                                                            <div class="card card-sm card-product-grid">
                                                                <a class="img-wrap"> <img src="assets/images/items/1.jpg" /> </a>
                                                                <figcaption class="info-wrap">
                                                                    <a class="title">{item["name"]}</a>
                                                                    <div class="price mt-1">{item["price"]}</div>
                                                                </figcaption>
                                                            </div>
                                                        </div>)
                    }); 
                    this.setState({ items: newArray})
                });
            })

        }

        const searchRequest = () => {
            let searchparams = document.getElementById("search").value;
            let value_category = document.getElementById("category").value;

            if (value_category === "all") {
                RequestMapping();
            } else {

                let resp = fetch('http://localhost:8080/lego/get_lego/'+value_category+'?'+value_category+'='+searchparams, {
                    method: 'GET'
                }).then((data) => {
                    if (data.status === 200) {
                        this.setState({items: []})
                        data.json().then((list) => {
                            let newArray = []
                            list.forEach((item) => {
                            newArray.push(<div class="col-xl-2 col-lg-3 col-md-4 col-6">
                                                                <div class="card card-sm card-product-grid">
                                                                    <a class="img-wrap"> <img src="assets/images/items/1.jpg" /> </a>
                                                                    <figcaption class="info-wrap">
                                                                        <a class="title">{item["name"]}</a>
                                                                        <div class="price mt-1">{item["price"]}</div>
                                                                    </figcaption>
                                                                </div>
                                                            </div>)
                            }); 
                            this.setState({ items: newArray})
                        })
                    } else {
                        alert("ERRO")
                    }
                })
                            
            }
        }

        if (this.state.count === 1) {
            RequestMapping();
            this.state.count += 1;
        }


        return (
                <div className="App">
                    <header class="section-header">
                    <section class="header-main border-bottom">
                        <div class="container">
                            <div class="row align-items-center">
                                <div class="col-xl-2 col-lg-3 col-md-12">
                                    <a  class="brand-wrap">
                                    LegoLiveries
                                    </a> 
                                </div>
                                <div class="col-xl-6 col-lg-5 col-md-6">
                                        <div class="input-group w-100">
                                            <select class="custom-select border-right"  name="category_name" id="category">
                                                    <option value="all">All</option>
                                                    <option value="name">Name</option>
                                                    <option value="price">Price</option>
                                            </select>
                                            <input type="text" class="form-control" placeholder="Search" id="search"/>
                                            <div class="input-group-append">
                                            <button class="btn btn-primary" onClick={searchRequest}>
                                                <i class="fa fa-search"></i> Search
                                            </button>
                                            </div>
                                        </div>
                                </div> 

                                <div class="col-xl-4 col-lg-4 col-md-6">
                                    <div class="widgets-wrap float-md-right">
                                    <div class="widget-header mr-3">
                                            <div class="icon-area">
                                                <i class="fa fa-user"></i>
                                            </div>
                                            <Link to="/login">Login</Link>
                                        </div>
                                        <div class="widget-header mr-3">
                                            <a  class="widget-view">
                                                <div class="icon-area">
                                                    <i class="fa fa-shopping-cart"></i>
                                                </div>
                                                <small class="text"> Cart </small>
                                            </a>
                                        </div>
                                        <div class="widget-header mr-3">
                                            <a  class="widget-view">
                                                <div class="icon-area">
                                                    <i class="fa fa-store"></i> 
                                                </div>
                                                <small class="text"> Orders </small>
                                            </a>
                                        </div>
                                        <div class="widget-header mr-3">
                                            <a  class="widget-view">
                                                <div class="icon-area">
                                                    <i class="fa fa-user"></i>
                                                    <span class="notify">3</span>
                                                </div>
                                                <small class="text"> My profile </small>
                                            </a>
                                        </div>
                                    </div> 
                                </div>
                            </div>
                        </div> 
                    </section> 
                    
                    </header> 

                    <div class="container">
                
                    <section class="section-main padding-y">
                    <main class="card">
                        <div class="card-body">
                    
                    <div class="row">
                    <div class="col-md-9 col-xl-7 col-lg-7">
                    
                
                    <div id="carousel1_indicator" class="slider-home-banner carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carousel1_indicator" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel1_indicator" data-slide-to="1"></li>
                        <li data-target="#carousel1_indicator" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                        <img src="assets/images/banners/slide1.jpg" alt="First slide" /> 
                        </div>
                        <div class="carousel-item">
                        <img src="assets/images/banners/slide2.png" alt="Second slide" />
                        </div>
                        <div class="carousel-item">
                        <img src="assets/images/banners/slide3.jpg" alt="Third slide" />
                        </div>
                    </div>
                    <a class="carousel-control-prev" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                    </div> 
                
                    
                        </div> 
                        <div class="col-md d-none d-lg-block flex-grow-1">
                            <aside class="special-home-right">
                                <h6 class="bg-blue text-center text-white mb-0 p-2">Popular category</h6>
                                
                                <div class="card-banner border-bottom">
                                <div class="py-3" style={{width:80}}>
                                    <h6 class="card-title">Men clothing</h6>
                                    <a  class="btn btn-secondary btn-sm"> Source now </a>
                                </div> 
                                <img src="assets/images/items/1.jpg" height="80" class="img-bg" />
                                </div>
                    
                                <div class="card-banner border-bottom">
                                <div class="py-3" style={{width:80}}>
                                    <h6 class="card-title">Winter clothing </h6>
                                    <a  class="btn btn-secondary btn-sm"> Source now </a>
                                </div> 
                                <img src="assets/images/items/2.jpg" height="80" class="img-bg" />
                                </div>
                    
                                <div class="card-banner border-bottom">
                                <div class="py-3" style={{width:80}}>
                                    <h6 class="card-title">Home inventory</h6>
                                    <a  class="btn btn-secondary btn-sm"> Source now </a>
                                </div> 
                                <img src="assets/images/items/6.jpg" height="80" class="img-bg" />
                                </div>
                    
                            </aside>
                        </div>
                    </div> 
                    
                        </div> 
                    </main> 
                    
                    </section>
                
                
                    <section  class="padding-bottom-sm">
                        <header class="section-heading heading-line">
                            <h4 class="title-section text-uppercase">Recommended items</h4>
                        </header>
                        
                        <div class="row row-sm">
                            {this.state.items}
                        </div>
                    </section>

                    <article class="my-4">
                        <img src="assets/images/banners/ad-sm.png" class="w-100" />
                    </article>
                    </div>  
                
                    
                
                    <section class="section-subscribe padding-y-lg">
                    <div class="container">
                    
                    <p class="pb-2 text-center text-white">Delivering the latest product trends and industry news straight to your inbox</p>
                    
                    <div class="row justify-content-md-center">
                        <div class="col-lg-5 col-md-6">
                    <form class="form-row">
                            <div class="col-md-8 col-7">
                            <input class="form-control border-0" placeholder="Your Email" type="email" />
                            </div>
                            <div class="col-md-4 col-5">
                            <button type="submit" class="btn btn-block btn-warning"> <i class="fa fa-envelope"></i> Subscribe </button>
                            </div> 
                    </form>
                    <small class="form-text text-white-50">We’ll never share your email address with a third-party. </small>
                        </div> 
                    </div>
                        
                    
                    </div>
                    </section>
                
                    <footer class="section-footer bg-secondary">
                        <div class="container">
                            <section class="footer-top padding-y-lg text-white">
                                <div class="row">
                                    <aside class="col-md col-6">
                                        <h6 class="title">Company</h6>
                                        <ul class="list-unstyled">
                                            <li> <a >About us</a></li>
                                            <li> <a >Career</a></li>
                                            <li> <a >Find a store</a></li>
                                            <li> <a >Rules and terms</a></li>
                                            <li> <a >Sitemap</a></li>
                                        </ul>
                                    </aside>
                                    <aside class="col-md col-6">
                                        <h6 class="title">Account</h6>
                                        <ul class="list-unstyled">
                                            <li> <a > User Login </a></li>
                                            <li> <a > User register </a></li>
                                            <li> <a > Account Setting </a></li>
                                            <li> <a > My Orders </a></li>
                                        </ul>
                                    </aside>
                                    <aside class="col-md">
                                        <h6 class="title">Social</h6>
                                        <ul class="list-unstyled">
                                            <li><a > <i class="fab fa-facebook"></i> Facebook </a></li>
                                            <li><a > <i class="fab fa-twitter"></i> Twitter </a></li>
                                            <li><a > <i class="fab fa-instagram"></i> Instagram </a></li>
                                            <li><a > <i class="fab fa-youtube"></i> Youtube </a></li>
                                        </ul>
                                    </aside>
                                </div> 
                            </section>  
                    
                            <section class="footer-bottom text-center">
                            
                                
                                    <p class="text-muted"> &copy; 2021 LegoLiveries, All rights reserved </p>
                                    <br />
                            </section>
                        </div>
                    </footer>
                </div>
        );
    }   
}

export default App;
