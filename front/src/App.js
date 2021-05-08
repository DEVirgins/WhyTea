import './App.css';
import 'antd/dist/antd.css';
import './index.css';
import AuthPage from './pages/authPage';
import DrawerMenu from "./components/drawer";
import SearchPage from './pages/SearchPage';
function App() {

    return (
        <div className="main__container">
            {/*<DrawerMenu />*/}
            {/*<AuthPage />*/}
            <SearchPage />
        </div>
    );
}

export default App;
