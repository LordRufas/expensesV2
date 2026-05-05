import { useState } from 'react';
import { LoginPage } from './js/loginPage';
import { MainPage } from './js/mainPage';
import { AdminPage } from './js/adminPage';
import { HistoryPage } from './js/historyPage';
import { NewUserPage } from './js/newUser';

export default function App() {
  const [page, setPage] = useState('login');
  const [username, setUsername] = useState('');
   const [errorMessage, SetErrorMessage] = useState('');
   const [userId, setUserId] = useState(-1);



return (
    <div>
      {page === 'login' ? (
        <LoginPage 
          page={page} 
          setPage={setPage}
          setUsername={setUsername} 
          username={username} 
          setUserId={setUserId}
          errorMessage={errorMessage}
          SetErrorMessage={SetErrorMessage}
        />
      ) : page === 'main' ? (<MainPage username={username} userId={userId}  setPage={setPage}/>) 
        : page === 'history' ? (<HistoryPage username={username} userId={userId}  setPage={setPage}/>)
        : <NewUserPage setPage={setPage}
          errorMessage={errorMessage}
          SetErrorMessage={SetErrorMessage}/>
    }
    </div>
  );
}