import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewUser() {

    const [user, setUser] = useState({
        name: "",
        username: "",
        email: "",
        id: ""

    })

    const { id } = useParams()
    useEffect(() => {

        fetchUsers().then((result) => setUser(result.data))
    }, [])

    async function fetchUsers() {
        const result = await axios.get(`http://localhost:8080/users/${id}`)
        return result
    }
    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 mt-2 p-4">
                    <h2 className="text-center m-4">User information</h2>
                    <div className="card">
                        <div className="card-header">
                            <p>User id: {user.id}</p>
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item">
                                    <b>Name: </b>
                                    {user.name}
                                </li>
                                <li className="list-group-item">
                                    <b>UserName: </b>
                                    {user.username}
                                </li>
                                <li className="list-group-item">
                                    <b>Email: </b>
                                    {user.email}
                                </li>
                            </ul>
                            <Link className="btn btn-danger" to={"/"}>Cancel</Link>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    )
}