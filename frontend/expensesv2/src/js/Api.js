const baseUrl = "http://localhost:8080"


export async function createUser(username, password) {
  try {
    const url = `${baseUrl}/createUser?username=${username}&password=${password}`;

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}


export async function Login(username, password) {
  try {
    const url = `${baseUrl}/Login?username=${username}&password=${password}`;

    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}



export async function fetchTypes(userId) {
  try {
    const url = `${baseUrl}/getTypesByUser?id=${userId}`;

    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}

export async function addTypes(userId, name) {
  try {
    const url = `${baseUrl}/addType?userId=${userId}&name=${name}`;

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}


export async function updateTypes(userId, oldName, newName) {
  try {
    const url = `${baseUrl}/updateTypesByUser`;

    const response = await fetch(url, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        "id": userId,
        "oldName": oldName,
        "newName": newName
      }),
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}


export async function deleteType(userId, name) {
  try {
    const url = `${baseUrl}/deleteTypesByUser`;

    const response = await fetch(url, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        "id": userId,
        "name": name
      }),
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}


export async function fetchTotals(userId) {
  try {
    const url = `${baseUrl}/getTotalsByUser?id=${userId}`;

    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}

export async function addTotals(userId, name, date, value) {
  try {
    const url = `${baseUrl}/addTotal?userId=${userId}&name=${name}&date=${date}&value=${value}`;

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}

export async function updateTotals(userId, oldName, newName, oldDate, newDate, oldValue, newValue) {
  try {
    const url = `${baseUrl}/updateTotalsByUser`;

    const response = await fetch(url, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        "id": userId,
        "oldName": oldName,
        "newName": newName,
        "oldDate": oldDate,
        "newDate": newDate,
        "oldValue": Number(oldValue),
        "newValue": Number(newValue)
      }),
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}


export async function deleteTotal(userId, name, date, value) {
  try {
    const url = `${baseUrl}/deleteTotalsByUser`;

    const response = await fetch(url, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        "id": Number(userId),
        "name": name,
        "date": date,
        "value": Number(value)
      }),
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}


export async function fetchTransactions(userId) {
  try {
    const url = `${baseUrl}/getTransactionByUser?id=${userId}`;

    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}

export async function addTransaction(userId, typeName, date, value, isRevenue) {
  try {
    const url = `${baseUrl}/addTransaction?userId=${userId}&date=${date}&typeName=${typeName}&value=${value}&isRevenue=${isRevenue}`;

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}

export async function updateTransaction(userId, oldDate, oldType, oldValue, oldIsRevenue, newDate, newType, newValue, newIsRevenue) {
  try {
    const url = `${baseUrl}/updateTransactionByUser`;

    const response = await fetch(url, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        "id": userId,
        "oldDate": oldDate,
        "oldType": oldType,
        "oldValue": oldValue,
        "oldIsRevenue": oldIsRevenue,
        "newDate": newDate,
        "newType": newType,
        "newValue": newValue,
        "newIsRevenue": newIsRevenue
      }),
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}


export async function deleteTransaction(userId, date, typeName, value, isRevenue) {
  try {
    const url = `${baseUrl}/deleteTransactionByUser`;

    const response = await fetch(url, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        "id": userId,
        "date": date,
        "typeName": typeName,
        "value": value,
        "isRevenue": isRevenue
      }),
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}

export async function purgeTypes(userId) {
   try {
    const url = `${baseUrl}/purgeTypes?userId=${userId}`;

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}

export async function purgeTotals(userId) {
  try {
    const url = `${baseUrl}/purgeTotals?userId=${userId}`;

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      return "error";
    }
  } catch (error) {
    return error;
  }
}