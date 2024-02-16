import axios from "axios";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

export default function AllUsers() {
  const [users, setUsers] = useState([])
  // const {id} = useParams();

  useEffect(() => {
    loadUsers();
  }, []);
  const loadUsers = async () => {
    const result = await axios.get("http://localhost:8080/users");
    setUsers(result.data);
  }
  const deleteUser = async (id: any) => {
    await axios.delete(`http://localhost:8080/users/${id}`)
    loadUsers()
  }
  return (
    <>
      <div className='p-4'><table className="table border shadow">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Username</th>
            <th scope="col">Name</th>
            <th scope="col">Mail</th>
            <th scope="col">Action</th>

          </tr>
        </thead>
        <tbody>
          {
            users.map((user: any, index) => {
              return (
                <tr key={index}>
                  <th scope="row" key={user.id}>{index}</th>
                  <td>{user.username}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>

                    <Link className='btn btn-outline-primary' to={`/users/${user.id} `}>View</Link>

                    <Link className='btn btn-outline-info' to={`/users/edit/${user.id} `}>Edit</Link>

                    <button className='btn btn-danger' onClick={() => { deleteUser(user.id) }}>Remove</button>
                  </td>
                </tr>
              );
            })
          }


        </tbody>
      </table></div>
    </>
  )

}