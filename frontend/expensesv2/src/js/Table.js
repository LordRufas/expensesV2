import * as Buttons from "./Button";
import * as API from "./Api"
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
        <tr>
          <th>Nome</th>
          <th>Valor</th>
          <th></th>
        </tr>
      </thead>{
        info &&
        <tbody>
          {editMode ?
            info.map((data) => (<>
              <tr>
                <td><input class="calendar" type="text" value={data.name} placeholder={data.name} onChange={(e) => updateTotalName(e.target.value, data, setInfo, info)} /></td>
                <td><input class="calendar" type="number" value={data.value} placeholder={data.value} onChange={(e) => updateTotalValue(e.target.value, data, setInfo, info)} /></td>
                <td>
                  <div class="child">
                    <Buttons.EditTotalButton setEditMode={setEditMode}></Buttons.EditTotalButton>
                    <Buttons.DeleteTotalButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTotalButton>
                  </div>
                </td>
              </tr>
            </>)) :
            info.map((data) => (<>
              <tr>
                <td>{data.name}</td>
                <td>{data.value}</td>
                <td><Buttons.EditButton setEditMode={setEditMode}></Buttons.EditButton ></td>
              </tr>
            </>))
          }
        </tbody>
      }
    </table>
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
              <tr>
                <td> {data.date} </td>
                <td>{data.typeName} </td>
                {editMode && <td>{data.total} </td>}
                <td>{data.value} </td>
                {editMode && <td><Buttons.DeleteTransactionButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}>apagar</Buttons.DeleteTransactionButton ></td>}
              </tr>
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
        <th>
          Nome
        </th>
        <th></th>
      </thead>
      {
        info &&

        <tbody>
          {
            editMode ?
              info.map((data) => (<>
                <tr>
                  <td> <input class="calendar" type="text" value={data.name} placeholder={data.name} onChange={(e) => updateTypes(e.target.value, data, setInfo, info)} /></td>
                  <td>
                    <div class="child">
                      <Buttons.EditTotalButton setEditMode={setEditMode}></Buttons.EditTotalButton>
                      <Buttons.DeleteTypeButton userId={userId} data={data} info={info} setInfo={setInfo} setErrorMessage={setErrorMessage}></Buttons.DeleteTypeButton >
                    </div>
                  </td>
                </tr>
              </>))
              :
              info.map((data) => (<>
                <tr>
                  <td>{data.name}</td>
                  <td><Buttons.EditButton setEditMode={setEditMode}></Buttons.EditButton ></td>
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