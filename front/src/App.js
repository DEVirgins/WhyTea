import './App.css';
import 'antd/dist/antd.css';
import './index.css';
import AuthPage from './pages/authPage';
import DrawerMenu from "./components/drawer";
import SearchPage from './pages/SearchPage';
import Routers from "./roots/router";

const isSignIn = false;

function App() {
    return (
        <div className="main__container">
            {
                isSignIn ?
                    <Routers />
                    : <AuthPage />
            }
            {/*<DrawerMenu />*/}
        </div>
    );
}

export default App;
