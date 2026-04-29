import { useState } from "react";


function ReturnToLoginPage({setPage}){

       const handleGoBack = async () => {
      setPage('login')
  };
    return (<> <button onClick={handleGoBack}>return</button></>)
}

export function NewUserPage({ setPage}){
 return(<>
    <ReturnToLoginPage setPage={setPage}></ReturnToLoginPage>
 </>)
}
