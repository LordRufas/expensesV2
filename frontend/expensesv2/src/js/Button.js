export function LogoutButton({setPage}){
      function goToPage(){
    setPage('login')
  }
  
    return (<><button on onClick={goToPage}>logout</button></>)
  }

export function HistoryButton({setPage}){
      function goToPage(){
    setPage('history')
  }
  
    return (<><button on onClick={goToPage}>transações passadas</button></>)
}


export function AdminButton({setPage}){
      function goToPage(){
    setPage('admin')
  }
  
    return (<><button on onClick={goToPage}>Editar valores</button></>)
}

export function MainPageButton({setPage}){
      function goToPage(){
    setPage('main')
  }
  
    return (<><button on onClick={goToPage}>Voltar à pagina principal</button></>)
}

export function Calendar(){
  return <Calendar></Calendar>
}

export function Title ({username}){
  return (<h1>Bem vind@ {username} </h1>)
}