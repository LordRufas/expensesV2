export function Table({headers, info}){
    return (<>
    <table>
        <thead>
           { headers.map((header) => (
                <th>{header.name}</th>
            ))
            }
        </thead>
        <tbody>
            {
            info.map((data) =>
            <tr>{data.name}</tr>) 
        }
        </tbody>
        </table></>);
}