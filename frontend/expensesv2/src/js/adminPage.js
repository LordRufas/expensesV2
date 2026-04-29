export function AdminPage({username, setPage}) {

   function goToMainPage(){
    setPage('main')
  }

  function goToHistoryPage(){
    setPage('history')
  }

  return (<><h1>Welcome to admin,{username} </h1>
  <button onClick={goToMainPage}>admin</button>
  <button onClick={goToHistoryPage}>history</button></>);
}