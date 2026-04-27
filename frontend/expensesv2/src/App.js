import { useState } from 'react';
import { LoginPage } from './js/loginPage';
import { MainPage } from './js/mainPage';

export default function App() {
  const [page, setPage] = useState('login');
  const [username, setUsername] = useState('');



return (
    <div>
      {page === 'login' ? (
        <LoginPage 
          page={page} 
          setPage={setPage}
          setUsername={setUsername} 
          username={username} 
        />
      ) : (
        <MainPage username={username} />
      )}
    </div>
  );
}