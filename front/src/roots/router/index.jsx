import React from 'react';
import SearchPage from "../../pages/SearchPage";
import AuthPage from "../../pages/authPage";
import { Switch, Route, BrowserRouter } from 'react-router-dom';

export const Routers = () => (
    <BrowserRouter>
        <Switch>
            {/*<Route exact path="/" component={AuthPage} />*/}
            <Route path="/" component={SearchPage} />
        </Switch>
    </BrowserRouter>
);

export default Routers;
