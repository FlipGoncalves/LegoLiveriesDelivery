import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router,Routes, Route, Link } from 'react-router-dom';

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
          items: [],
          count: 1,
          cart: [],
          total_price: 0,
          total_products: 0,
        };
      }

    additemtocart(item) {

        this.setState({total_products: this.state.total_products + document.getElementById("qtty"+item["name"].replace(/\s/g, '')).value * 1})
        this.setState({total_price: this.state.total_price + document.getElementById("qtty"+item["name"].replace(/\s/g, '')).value * item["price"]})

        return (
            <div class="row">
                <a class="img-wrap"><img class="left" src="assets/images/items/1.jpg" height="130" width="auto"/></a>
                <div class="d-flex justify-content-between align-items-center">
                    <button type="button" class="close" aria-label="Close" onClick="Custombox.modal.close();">
                    </button>
                </div>
                <span class="d-block font-size-1">{item["name"]}</span>
                <span class="d-block text-primary font-weight-semi-bold">{item["price"]*document.getElementById("qtty"+item["name"].replace(/\s/g, '')).value}</span>
                <small class="d-block text-muted">{document.getElementById("qtty"+item["name"].replace(/\s/g, '')).value}</small>
            </div>
        )
    }

    additemtoarray(item) {
        return (
        <div>
            <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                        <div class="card card-sm card-product-grid">
                        <button type="button" class="btn openmodal" data-toggle="modal" data-target={"#"+item["name"].replace(/\s/g, '')}>
                            <a class="img-wrap"> <img src="assets/images/items/1.jpg" width= '70%'/> </a>
                            <figcaption class="info-wrap">
                                <a class="title">{item["name"]}</a>
                                <div class="price mt-1">{item["price"]}</div>
                            </figcaption>
                        </button>
                        </div>
                    </div>
                    <div class="modal fade" id={item["name"].replace(/\s/g, '')}>
                        <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                        
                            <div class="modal-header">
                            <h4 class="modal-title">{item["name"]}</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            
                            <div class="modal-body">
                                <div class="container">
                                <h6>Item Details</h6>
                                <div class="row">
                                    <div class="col">
                                        <img class="img-fluid" src="assets/images/items/1.jpg" />
                                    </div>
                                </div>
                                <h6>Order Details</h6>
                                <div class="row">
                                    <div class="col-xs-6">
                                        <ul type="none">
                                            <li class="left">Order number:</li>
                                            <li class="left">Price:</li>
                                        </ul>
                                    </div>
                                    <div class="col-xs-6">
                                        <ul class="right" type="none">
                                            <li class="right"></li>
                                            <li class="right">{item["price"]}</li>
                                        </ul>
                                    </div>
                                </div>
                                    <h6>Quantity</h6>
                                    <div class="row" style={{borderBottom: 'none'}}>
                                        <div class="col-xs-6">
                                            <select class="custom-select border-right" name="qtty" id={"qtty"+item["name"].replace(/\s/g, '')}>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                </div>
                                
                                <div class="modal-footer">
                                    <button type="button" class="btn" onClick={() => this.setState(prevState => ({cart: [...prevState.cart, this.additemtocart(item)]}))} data-toggle="modal" data-target={"#"+item["name"].replace(/\s/g, '')}>Add to cart</button>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
        )
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
                        newArray.push(
                            this.additemtoarray(item)
                        )
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
                                newArray.push(
                                    this.additemtoarray(item)
                                )
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
                                    <Link to="/">
                                        <a class="brand-wrap">
                                            LegoLiveries
                                        </a> 
                                    </Link>
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
                                            <button data-toggle="modal" data-target="#cart" style={{outline: 'none', backgroundColor: 'transparent', border: 'none'}}>
                                                <div class="icon-area">
                                                    <i class="fa fa-shopping-cart"></i>
                                                    <span class="notify">{this.state.cart.length}</span>
                                                </div>
                                                <small class="text"> Cart </small>
                                            </button>
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


                    <div class="modal fade" id="cart">
                        <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                        
                            <div class="modal-header">
                            <h4 class="modal-title">Cart</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            
                            <div class="modal-body">
                                {this.state.cart}
                                <div class="row">
                                    <div class="col-xs-6">
                                        <ul type="none">
                                            <li class="left">Total:</li>
                                            <li class="left">Products:</li>
                                        </ul>
                                    </div>
                                    <div class="col-xs-6">
                                        <ul class="right" type="none">
                                            <li class="right">{this.state.total_price}</li>
                                            <li class="right">{this.state.total_products}</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                                
                            <div class="modal-footer">
                                <button type="button" class="btn" data-toggle="modal" data-target="#cart" onClick={() => console.log(this.state)}>Order</button>
                            </div>
                                
                            </div>
                        </div>
                    </div>
                
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
