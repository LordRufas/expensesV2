import * as Buttons from "./Button";
import { useState } from "react";

export function ResumeTable({ info, setInfo }) {
  return (
    <table border="1">
      <thead>
        <tr>
          <th>Nome </th>
          <th>Valor </th>
        </tr>
      </thead>
      {
        info &&
        <tbody>
          {
            info.map((data) => (<>
              <tr>
                <td>{data.name}</td>
                <td>{data.value} </td>
              </tr>
            </>))
          }
        </tbody>
      }
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
                <td><input class="calendar" type="number" value={data.value} placeholder={data.value} onChange={(e) => updateTotalValue(e.target.value, data, setInfo, info)} /></td>
                <td><Buttons.DeleteTotalButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}></Buttons.DeleteTotalButton></td>
              </tr>
            </>)) :
            info.map((data) => (<>
              <tr>
                <td>{data.name}</td>
                <td>{data.value}</td>
              </tr>
            </>))
          }
        </tbody>
      }
    </table >
  );
}

export function TransactionTable({ info, userId, editMode, setInfo, setErrorMessage }) {
  return (
    <table border="1">
      <thead>
        <tr>
          <th> data </th>

          <th> tipo </th>

          {editMode && <th> total </th>}

          <th> valor </th>
          {editMode && <th></th>}
        </tr>
      </thead>
      {
        info &&
        <tbody>
          {
            info.map((data) => (<>
            {
              data.isRevenue === true || data.isRevenue === "true" ? 
              <tr>
                <td id = "positiveValue"> {data.date} </td>
                <td id = "positiveValue">{data.typeName} </td>
                {editMode && <td id = "positiveValue">{data.total} </td>}
                <td id = "positiveValue">{data.value} </td>
                {editMode && <td><Buttons.DeleteTransactionButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTransactionButton ></td>}
              </tr>
              :
              <tr>
                <td id = "negativeValue"> {data.date} </td>
                <td id = "negativeValue">{data.typeName} </td>
                {editMode && <td id = "negativeValue">{data.total} </td>}
                <td id = "negativeValue">{data.value} </td>
                {editMode && <td><Buttons.DeleteTransactionButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTransactionButton ></td>}
              </tr>
            }
            </>))
          }
        </tbody>
      }
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