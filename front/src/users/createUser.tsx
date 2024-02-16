import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function CreateUser() {

    let navigate = useNavigate()

    const [user, setUser] = useState({
        name: "",
        username: "",
        email: ""
    })
    const [isValidMail, setIsValidMail] = useState("")
    const [isUserExist, setIsUserExist] = useState("")
    const [isEmailExist, setIsEmailExist] = useState("")
    const [registeterError, setRegisteterError] = useState("")

    const { name, username, email } = user

    const onInputChange = (e: any) => {
        if (e.target.name === "username") {
            userExist(e.target.value)
        }
        else if (e.target.name === "email") {
            isValidEmail(e.target.value) ? setIsValidMail("") : setIsValidMail("Invalid email");
            eamailExist(e.target.value)
        }

        setUser({ ...user, [e.target.name]: e.target.value })
    }
    const onSubmit = async (e: any) => {
        e.preventDefault()
        await axios.post("http://localhost:8080/users", user)
        navigate("/")
    }
    async function register(e: any) {
        e.preventDefault()
        if (isValidEmail(email) && isUserExist === "" && isEmailExist === "" && name !== "") {
            await onSubmit(e);
        }
        else {
            setRegisteterError("Feel the form correctly and try again")
        }
    }
    function userExist(username: string) {
        axios.get(`http://localhost:8080/users/check/username/?username=${username}`)
            .then((res) => {
                if (res.data) {
                    setIsUserExist("Username already exist")
                }
                else {
                    setIsUserExist("")
                }
            })
    }
    function eamailExist(email: string) {
        axios.get(`http://localhost:8080/users/check/email/?email=${email}`)
            .then((res) => {
                if (res.data) {
                    setIsEmailExist("Email already exist")
                }
                else {
                    setIsEmailExist("")
                }
            })
    }

    function isValidEmail(email: string): boolean {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 mt-2 p-4">
                    <h2 className="text-center m-4">Register user</h2>
                    <p className="text-center m-4">{registeterError}</p>
                    <form>

                        <div className="mb-3">
                            <label htmlFor="name">Name</label>
                            <input className="form-control" type="text" name="name" id="name" value={name} onChange={(e) => onInputChange(e)} />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="username">Username</label>
                            <input className="form-control" type="text" name="username" id="username" value={username} onChange={(e) => onInputChange(e)} />
                            <p>{isUserExist}</p>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="email">Email</label>
                            <input className="form-control" type="text" name="email" id="email" value={email} onChange={(e) => onInputChange(e)} />
                            <p>{isValidMail}{isEmailExist}</p>
                        </div>
                        <div className="d-flex justify-content-around">
                            <Link className="btn btn-danger" type="submit" to={"/"}>Cancel</Link>
                            <button className="btn btn-primary" type="submit" onClick={(e) => register(e)}>Register</button>

                        </div>

                    </form>

                </div>
            </div>
        </div>
    );
}
