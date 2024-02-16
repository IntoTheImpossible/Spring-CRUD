import { Link } from "react-router-dom";

export default function NavBar() {
    return (
        <>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
                <a className="navbar-brand mx-4" href="/">Application</a>
                <Link className="btn btn-primary outline mx-4" to={"/users/create"} >Create user</Link>
            </nav>
        </>
    )
}