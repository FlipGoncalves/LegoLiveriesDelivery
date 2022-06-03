import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Profile from './component/profile'
import SignIn from './component/signin'
import SignUp from './component/signup'
import Management from './component/management';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={ <App /> }></Route>
        <Route exact path='/profile' element={< Profile />}></Route>
        <Route exact path='/sign-up' element={< SignUp />}></Route>
        <Route exact path='/sign-in' element={< SignIn />}></Route>
        <Route exact path='/management' element={< Management />}></Route>
      </Routes>
    </Router>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
serviceWorkerRegistration.unregister();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
