import React, { useState } from "react";
import { useNavigate, Link } from 'react-router-dom';
import Footer from "./Footer";
import Navbar from './Navbar';
import axios from 'axios';


const AllOrders = () => {

    const [orders, setOrders] = useState([]);

    const handleSubmit = async e => {
        e.preventDefault();
        
        console.log("here")

        axios.get('http://localhost:8080/orders/client/'+localStorage.getItem("clientId"))
        .then((response) => {
            console.log(response.data);
            let array = [];
        
            response.data.forEach((item) => {
                array.push(
                    addOrderToArray(item)
                )
            }); 

            setOrders(array)

        })
        .catch((error) => {
            console.log(error);
            return
        });
    }

    function addOrderToArray(item) {

        console.log(item)

        return (
        <div class="container">
            <h6>Order No: {item["orderId"]}</h6>
            <div class="row">
                <div class="col-xs-6">
                    <ul type="none">
                        <li class="left">Scheduled Time of Delivery: {item["scheduledTimeOfDelivery"]}</li>
                        <li class="left">Total Price: {item["totalPrice"]}</li>
                        <li class="left">Status: {item["status"]}</li>
                    </ul>
                </div>
            </div>
        </div>
        )
    }

    const submit = handleSubmit

    return (
    <div>
    <div class="container">

        <Navbar />

            <section class="section-main padding-y">
                <main class="card">
                    <div>
                        <h3>Orders Details</h3>
                        {orders}
                    </div>
                </main>
            </section>
        </div>
        
    </div>
  );
}

export default AllOrders;