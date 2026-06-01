import * as Buttons from "./Button";
import { useState,useMemo } from "react";


const currencyFormatter = new Intl.NumberFormat('pt-pt', {
  minimumFractionDigits: 2,
  maximumFractionDigits: 2,
});

export function Balance({ data }) {
  let balance = 0;
  let negative = 0;
  let positive = 0;

  for (const item of data) {
    if (item.isRevenue === "true") {
      positive += Number(item.value);
      balance += Number(item.value);
    }
    else {
      negative += Number(item.value);
      balance -= Number(item.value);
    }
  }

  return (
    <>{
      data && data.length > 0 && <div>
        <table>
          <thead>
            <tr>
              <th>Soma das receitas</th>
              <th>Soma das despesas</th>
              <th>Balanço final</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{positive.toFixed(2)}</td>
              <td>{negative.toFixed(2)}</td>
              {balance > 0 && <td id="positiveValue">{balance.toFixed(2)} </td>}
              {balance < 0 && <td id="negativeValue">{balance.toFixed(2)} </td>}
              {balance === 0 && <td>{balance.toFixed(2)} </td>}
            </tr>
          </tbody>
        </table>
      </div>} </>
  );
}

export function ResumeTable({ info }) {
  
  const [sortConfig, setSortConfig] = useState({ key: null, direction: 'asc' });
  const sortedInfo = useMemo(() => {
    if (!info) return [];
    let sortableItems = [...info];

    if (sortConfig.key !== null) {
      sortableItems.sort((a, b) => {
        let aValue = a[sortConfig.key];
        let bValue = b[sortConfig.key];

        
        if (typeof aValue === 'string') {
          return sortConfig.direction === 'asc' 
            ? aValue.localeCompare(bValue) 
            : bValue.localeCompare(aValue);
        }

        
        return sortConfig.direction === 'asc' 
          ? aValue - bValue 
          : bValue - aValue;
      });
    }
    return sortableItems;
  }, [info, sortConfig]);

  
  const requestSort = (key) => {
    let direction = 'asc';
    if (sortConfig.key === key && sortConfig.direction === 'asc') {
      direction = 'desc';
    }
    setSortConfig({ key, direction });
  };

  
  const getClassNamesFor = (name) => {
    if (!sortConfig.key) return;
    return sortConfig.key === name ? (sortConfig.direction === 'asc' ? ' ▲' : ' ▼') : '';
  };

  return (
    <table border="1">
      <thead>
        <tr>
          <th onClick={() => requestSort('name')} style={{ cursor: 'pointer', userSelect: 'none' }}>
            Nome {getClassNamesFor('name')}
          </th>
          <th onClick={() => requestSort('value')} style={{ cursor: 'pointer', userSelect: 'none' }}>
            Valor {getClassNamesFor('value')}
          </th>
        </tr>
      </thead>
      {info && (
        <tbody>
          {sortedInfo.map((data, index) => {
            
            let rowClass = '';
            if (data.value > 0) rowClass = 'positiveValue';
            if (data.value < 0) rowClass = 'negativeValue';

            return (
              <tr key={data.id || index}>
                <td id={rowClass}>{data.name}</td>
                <td id={rowClass}>{currencyFormatter.format(data.value.toFixed(2))}</td>
              </tr>
            );
          })}
        </tbody>
      )}
    </table>
  );
}

export function TotalTable({ info, setInfo, userId, setErrorMessage }) {
  const [editMode, setEditMode] = useState(false);
  return (
    <table border="1">
      <thead>
        {!editMode ?
          <tr>
            <th>Nome</th>
            <th>
              <div className="headerEdit">
                <span>Valor</span>

                <Buttons.EditButton
                  setEditMode={setEditMode}
                />
              </div>
            </th>
          </tr>
          :
          <tr>
            <th>Nome</th>
            <th>Valor</th>
            <th>
              <Buttons.SaveButton setEditMode={setEditMode} userId={userId} info={info} setInfo={setInfo} table={"totals"} setErrorMessage={setErrorMessage}></Buttons.SaveButton >
            </th>
          </tr>
        }
      </thead>
      {
        info &&
        <tbody>
          {editMode ?
            info.map((data) => (<>
              <tr>
                <td><input class="calendar" type="text" value={data.name} placeholder={data.name} onChange={(e) => updateTotalName(e.target.value, data, setInfo, info)} /></td>
                <td><input class="calendar" type="number" value={currencyFormatter.format(data.value)} placeholder={data.value} onChange={(e) => updateTotalValue(e.target.value, data, setInfo, info)} /></td>
                <td><Buttons.DeleteTotalButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}></Buttons.DeleteTotalButton></td>
              </tr>
            </>)) :
            info.map((data) => (<>
              <tr>
                <td>{data.name}</td>
                <td>{currencyFormatter.format(data.value)}</td>
              </tr>
            </>))
          }
        </tbody>
      }
    </table >
  );
}

export function TransactionTable({ info, totals, setTotals, userId, editMode, setInfo, setErrorMessage }) {

  const [sortConfig, setSortConfig] = useState({ key: null, direction: 'asc' });

  const sortedInfo = useMemo(() => {
    if (!info) return [];
    let sortableItems = [...info];

    if (sortConfig.key !== null) {
      sortableItems.sort((a, b) => {
        let aValue = a[sortConfig.key];
        let bValue = b[sortConfig.key];
        

        if (typeof aValue === 'string') {
          return sortConfig.direction === 'asc'
            ? aValue.localeCompare(bValue)
            : bValue.localeCompare(aValue);
        }

        return sortConfig.direction === 'asc' 
          ? aValue - bValue 
          : bValue - aValue;
      });
    }
    return sortableItems;
  }, [info, sortConfig]);

  // 3. Request sort handler
  const requestSort = (key) => {
    let direction = 'asc';
    if (sortConfig.key === key && sortConfig.direction === 'asc') {
      direction = 'desc';
    }
    setSortConfig({ key, direction });
  };

  const getClassNamesFor = (name) => {
    if (!sortConfig.key) return '';
    return sortConfig.key === name ? (sortConfig.direction === 'asc' ? ' ▲' : ' ▼') : '';
  };

  return (
    <table border="1">
      <thead>
        <tr>
          <th> data </th>

          <th onClick={() => requestSort('typeName')} style={{ cursor: 'pointer', userSelect: 'none' }}>
            tipo {getClassNamesFor('typeName')}
          </th>

          {editMode && <th> total </th>}

          <th onClick={() => requestSort('value')} style={{ cursor: 'pointer', userSelect: 'none' }}>
            valor {getClassNamesFor('value')}
          </th>

          {editMode && <th></th>}
        </tr>
      </thead>
      
      {info && (
        <tbody>
          {sortedInfo.map((data, index) => {
            const isRevenue = data.isRevenue === true || data.isRevenue === "true";
            const rowClass = isRevenue ? "positiveValue" : "negativeValue";

            return (
              <tr key={data.id || index}>
                <td id={rowClass}> {data.date} </td>
                <td id={rowClass}>{data.typeName} </td>
                {editMode && <td id={rowClass}>{data.total} </td>}
                <td id={rowClass}>{currencyFormatter.format(data.value)} </td>
                {editMode && (
                  <td>
                    <Buttons.DeleteTransactionButton 
                      totals={totals} 
                      setTotals={setTotals} 
                      userId={userId} 
                      data={data} 
                      info={info} 
                      setInfo={setInfo} 
                      setErrorMessage={setErrorMessage}
                    >
                      apagar
                    </Buttons.DeleteTransactionButton>
                  </td>
                )}
              </tr>
            );
          })}
        </tbody>
      )}
    </table>
  );
}

export function TypesTable({ info, setInfo, userId, setErrorMessage }) {
  const [editMode, setEditMode] = useState(false);
  return (
    <table border="1">
      <thead>
        {!editMode ?
          <tr>
            <th>
              <div className="headerEdit">
                <span>Nome</span>

                <Buttons.EditButton
                  setEditMode={setEditMode}
                />
              </div>
            </th>
          </tr>
          :
          <tr>
            <th>Nome</th>
            <th> <Buttons.SaveButton setEditMode={setEditMode} userId={userId} info={info} setInfo={setInfo} table={"types"} setErrorMessage={setErrorMessage}></Buttons.SaveButton ></th>
          </tr>
        }
      </thead>
      {
        info &&

        <tbody>
          {
            editMode ?
              info.map((data) => (<>
                <tr>
                  <td> <input class="calendar" type="text" value={data.name} placeholder={data.name} onChange={(e) => updateTypes(e.target.value, data, setInfo, info)} /></td>
                  <td><Buttons.DeleteTypeButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}></Buttons.DeleteTypeButton></td>

                </tr>
              </>))
              :
              info.map((data) => (<>
                <tr>
                  <td>{data.name}</td>
                </tr>
              </>))
          }
        </tbody>
      }
    </table>
  );
}



function updateTypes(newValue, data, setInfo, info) {
  const newInfo = info.map((item) => {
    if (item === data) {
      return {
        ...item,
        name: newValue
      };
    }
    return item;
  });

  setInfo(newInfo)

}

function updateTotalName(newValue, data, setInfo, info) {
  const newInfo = info.map((item) => {
    if (item === data) {
      return {
        ...item,
        name: newValue
      };
    }
    return item;
  });

  setInfo(newInfo)
}

function updateTotalValue(newValue, data, setInfo, info) {
  const newInfo = info.map((item) => {
    if (item === data) {
      return {
        ...item,
        value: newValue
      };
    }
    return item;
  });

  setInfo(newInfo)
}