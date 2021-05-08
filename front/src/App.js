import './App.css';
import 'antd/dist/antd.css';
import './index.css';
import AuthPage from './pages/authPage';

function App() {
    // const [date, setDate] = useState(null);
    // const handleChange = value => {
    //     message.info(`Selected Date: ${value ? value.format('YYYY-MM-DD') : 'None'}`);
    //     setDate(value);
    // };

    // const openGoogleAuth = () => {
    //     var win = window.open('http://www.google.com', 'google', 'width=800,height=600,status=0,toolbar=0');
    //     var timer = setInterval(function () {
    //         if (win.closed) {
    //             window.localStorage.getItem()
    //         }
    //     }, 1000);
    // }

    return (
        <div className="main__container">
            <AuthPage />
        </div>
        // <div style={{ width: '100%', height: '100vh',  margin: '100px auto' }}>
        //     <button className='google__button' onClick={openGoogleAuth}>
        //         <img className='google__button__image' />
        //         <span className='google__button__span'>Login With Google</span>
        //     </button>
        // </div>
      // <div style={{ width: 400, margin: '100px auto' }}>
      //   <DatePicker onChange={handleChange} />
      //   <div style={{ marginTop: 16 }}>
      //     Selected Date: {date ? date.format('YYYY-MM-DD') : 'None'}
      //   </div>
      // </div>
    );
}

export default App;
