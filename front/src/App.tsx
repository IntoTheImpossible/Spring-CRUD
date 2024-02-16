import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import NavBar from './layout/NavBar'
import CreateUser from './users/createUser'
import AllUsers from './users/allUsers'
import EditUser from './users/editUser'
import ViewUser from './users/viewUser'


function App() {

  return (
    <>
      <Router>
        <NavBar />
        <Routes>
          <Route path="/" element={<AllUsers />} />
          <Route path="/users/create" element={<CreateUser />} />
          <Route path="/users/:id" element={<ViewUser />} />
          <Route path="/users/edit/:id" element={<EditUser />} />

        </Routes>

      </Router>

    </>
  )
}

export default App
