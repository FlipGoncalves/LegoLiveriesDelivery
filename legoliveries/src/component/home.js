import React from 'react';

const Home = () => {
    return (
        <div className="App">
                    <div class="container">
                
                    <section class="section-main padding-y">
                    <main class="card">
                        <div class="card-body">
                    
                    <div class="row">
                        {/* <aside class="col-lg col-md-3 flex-lg-grow-0">
                            <nav class="nav-home-aside">
                                <h6 class="title-category">MY MARKETS <i class="d-md-none icon fa fa-chevron-down"></i></h6>
                                <ul class="menu-category">
                                    <li><a href="#">Fashion and clothes</a></li>
                                    <li><a href="#">Automobile and motors</a></li>
                                    <li><a href="#">Gardening and agriculture</a></li>
                                    <li><a href="#">Electronics and tech</a></li>
                                    <li><a href="#">Packaginf and printing</a></li>
                                    <li><a href="#">Home and kitchen</a></li>
                                    <li><a href="#">Digital goods</a></li>
                                </ul>
                            </nav>
                        </aside> */}
                        
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
                    <a class="carousel-control-prev" href="#carousel1_indicator" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carousel1_indicator" role="button" data-slide="next">
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
                                    <a href="#" class="btn btn-secondary btn-sm"> Source now </a>
                                </div> 
                                <img src="assets/images/items/1.jpg" height="80" class="img-bg" />
                                </div>
                    
                                <div class="card-banner border-bottom">
                                <div class="py-3" style={{width:80}}>
                                    <h6 class="card-title">Winter clothing </h6>
                                    <a href="#" class="btn btn-secondary btn-sm"> Source now </a>
                                </div> 
                                <img src="assets/images/items/2.jpg" height="80" class="img-bg" />
                                </div>
                    
                                <div class="card-banner border-bottom">
                                <div class="py-3" style={{width:80}}>
                                    <h6 class="card-title">Home inventory</h6>
                                    <a href="#" class="btn btn-secondary btn-sm"> Source now </a>
                                </div> 
                                <img src="assets/images/items/6.jpg" height="80" class="img-bg" />
                                </div>
                    
                            </aside>
                        </div>
                    </div> 
                    
                        </div> 
                    </main> 
                    
                    </section>
                
                
                    {/* <section class="padding-bottom">
                    <header class="section-heading heading-line">
                        <h4 class="title-section text-uppercase">Apparel</h4>
                    </header>
                    
                    <div class="card card-home-category">
                    <div class="row no-gutters">
                        <div class="col-md-3">
                        
                        <div class="home-category-banner bg-light-orange">
                            <h5 class="title">Best trending clothes only for summer</h5>
                            <p>Consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
                            <a href="#" class="btn btn-outline-primary rounded-pill">Source now</a>
                            <img src="assets/images/items/2.jpg" class="img-bg" />
                        </div>
                    
                        </div> 
                        <div class="col-md-9">
                    <ul class="row no-gutters bordered-cols">
                        <li class="col-6 col-lg-3 col-md-4">
                    <a href="#" class="item"> 
                        <div class="card-body">
                            <h6 class="title">Well made women clothes with trending collection  </h6>
                            <img class="img-sm float-right" src="assets/images/items/1.jpg" /> 
                            <p class="text-muted"><i class="fa fa-map-marker-alt"></i> Guanjou, China</p>
                        </div>
                    </a>
                        </li>
                        <li class="col-6 col-lg-3 col-md-4">
                    <a href="#" class="item"> 
                        <div class="card-body">
                            <h6 class="title">Great clothes with trending collection  </h6>
                            <img class="img-sm float-right" src="assets/images/items/2.jpg" /> 
                            <p class="text-muted"><i class="fa fa-map-marker-alt"></i> Beijing, China</p>
                        </div>
                    </a>
                        </li>
                        <li class="col-6 col-lg-3 col-md-4">
                    <a href="#" class="item"> 
                        <div class="card-body">
                            <h6 class="title">Demo clothes with sample collection  </h6>
                            <img class="img-sm float-right" src="assets/images/items/3.jpg" /> 
                            <p class="text-muted"><i class="fa fa-map-marker-alt"></i> Tokyo, Japan</p>
                        </div>
                    </a>
                        </li>
                        <li class="col-6 col-lg-3 col-md-4">
                    <a href="#" class="item"> 
                        <div class="card-body">
                            <h6 class="title">Home and kitchen electronic  stuff collection  </h6>
                            <img class="img-sm float-right" src="assets/images/items/4.jpg" /> 
                            <p class="text-muted"><i class="fa fa-map-marker-alt"></i> Tashkent, Uzb</p>
                        </div>
                    </a>  
                        </li>
                        <li class="col-6 col-lg-3 col-md-4">
                    <a href="#" class="item"> 
                        <div class="card-body">
                            <h6 class="title">Home and kitchen electronic  stuff collection  </h6>
                            <img class="img-sm float-right" src="assets/images/items/5.jpg" /> 
                            <p class="text-muted"><i class="fa fa-map-marker-alt"></i> London, Britain</p>
                        </div>
                    </a>
                        </li>
                        <li class="col-6 col-lg-3 col-md-4">
                    <a href="#" class="item"> 
                        <div class="card-body">
                            <h6 class="title">Home and kitchen electronic  stuff collection  </h6>
                            <img class="img-sm float-right" src="assets/images/items/6.jpg" /> 
                            <p class="text-muted"><i class="fa fa-map-marker-alt"></i> Guanjou, China</p>
                        </div>
                    </a>
                        </li>
                        <li class="col-6 col-lg-3 col-md-4">
                    <a href="#" class="item"> 
                        <div class="card-body">
                            <h6 class="title">Well made clothes with trending collection </h6>
                            <img class="img-sm float-right" src="assets/images/items/7.jpg" /> 
                            <p class="text-muted"><i class="fa fa-map-marker-alt"></i> Hong Kong, China</p>
                    
                        </div>
                    </a>
                        </li>
                        <li class="col-6 col-lg-3 col-md-4">
                    <a href="#" class="item"> 
                        <div class="card-body">
                            <h6 class="title">Home and kitchen interior  stuff collection   </h6>
                            <img class="img-sm float-right" src="assets/images/items/6.jpg" /> 
                            <p class="text-muted"><i class="fa fa-map-marker-alt"></i> Guanjou, China</p>
                        </div>
                    </a>
                        </li>
                    </ul>
                        </div>
                    </div> 
                    </div>
                    </section> */}

                
                    <section  class="padding-bottom-sm">
                    
                    <header class="section-heading heading-line">
                        <h4 class="title-section text-uppercase">Recommended items</h4>
                    </header>
                    
                    <div class="row row-sm">
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/1.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Just another product name</a>
                                    <div class="price mt-1">$179.00</div>
                                </figcaption>
                            </div>
                        </div> 
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/2.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Some item name here</a>
                                    <div class="price mt-1">$280.00</div> 
                                </figcaption>
                            </div>
                        </div>
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/3.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Great product name here</a>
                                    <div class="price mt-1">$56.00</div> 
                                </figcaption>
                            </div>
                        </div>
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/4.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Just another product name</a>
                                    <div class="price mt-1">$179.00</div> 
                                </figcaption>
                            </div>
                        </div> 
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/5.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Just another product name</a>
                                    <div class="price mt-1">$179.00</div>
                                </figcaption>
                            </div>
                        </div> 
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/6.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Some item name here</a>
                                    <div class="price mt-1">$280.00</div>
                                </figcaption>
                            </div>
                        </div> 
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/7.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Great product name here</a>
                                    <div class="price mt-1">$56.00</div>
                                </figcaption>
                            </div>
                        </div>
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/9.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Just another product name</a>
                                    <div class="price mt-1">$179.00</div> 
                                </figcaption>
                            </div>
                        </div> 
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/4.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Just another product name</a>
                                    <div class="price mt-1">$179.00</div> 
                                </figcaption>
                            </div>
                        </div> 
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/5.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Just another product name</a>
                                    <div class="price mt-1">$179.00</div> 
                                </figcaption>
                            </div>
                        </div> 
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/6.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Some item name here</a>
                                    <div class="price mt-1">$280.00</div> 
                                </figcaption>
                            </div>
                        </div> 
                        <div class="col-xl-2 col-lg-3 col-md-4 col-6">
                            <div class="card card-sm card-product-grid">
                                <a href="#" class="img-wrap"> <img src="assets/images/items/7.jpg" /> </a>
                                <figcaption class="info-wrap">
                                    <a href="#" class="title">Great product name here</a>
                                    <div class="price mt-1">$56.00</div> 
                                </figcaption>
                            </div>
                        </div> 
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
                                            <li> <a href="#">About us</a></li>
                                            <li> <a href="#">Career</a></li>
                                            <li> <a href="#">Find a store</a></li>
                                            <li> <a href="#">Rules and terms</a></li>
                                            <li> <a href="#">Sitemap</a></li>
                                        </ul>
                                    </aside>
                                    <aside class="col-md col-6">
                                        <h6 class="title">Account</h6>
                                        <ul class="list-unstyled">
                                            <li> <a href="#"> User Login </a></li>
                                            <li> <a href="#"> User register </a></li>
                                            <li> <a href="#"> Account Setting </a></li>
                                            <li> <a href="#"> My Orders </a></li>
                                        </ul>
                                    </aside>
                                    <aside class="col-md">
                                        <h6 class="title">Social</h6>
                                        <ul class="list-unstyled">
                                            <li><a href="#"> <i class="fab fa-facebook"></i> Facebook </a></li>
                                            <li><a href="#"> <i class="fab fa-twitter"></i> Twitter </a></li>
                                            <li><a href="#"> <i class="fab fa-instagram"></i> Instagram </a></li>
                                            <li><a href="#"> <i class="fab fa-youtube"></i> Youtube </a></li>
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
    )
}

export default Home;