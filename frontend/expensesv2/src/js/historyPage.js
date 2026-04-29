export function HistoryPage({username, setPage}) {


     function goToMainPage(){
    setPage('main')
  }

  function goToAdminPage(){
    setPage('admin')
  }

  return (<><h1>Welcome to History,{username} </h1>
  <button onClick={goToMainPage}>main</button>
  <button onClick={goToAdminPage}>admin</button></>);
}