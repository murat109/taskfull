import { useState } from "react";
import App from "./App";
import { useNavigate } from "react-router-dom";
import "./bootstrap.css";


/* login page using simple login form and storing values to local storage
    if any user is logged in previously, this page will not be seen and index page will be navigated.
    this is because browse cache. Try cleaning cache or using new secret browser window.
*/
const Login = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [authenticated, setAuthenticated] = useState(localStorage.getItem("authenticated") || false);
    const users = [{ username: "admin", password: "editor" }, { username: "user", password: "viewer" }];

    const handleSubmit = (e) => {
        e.preventDefault()
        const account = users.find((user) => user.username === username);
        if (account && account.password === password) {
            setAuthenticated(true)
            localStorage.setItem("authenticated", true);
            localStorage.setItem("usr", username);
            localStorage.setItem("psw", password);
            navigate("/index");
        } else {
            localStorage.setItem("authenticated", false);
        }
    };

    return (
        <div class="w-25 mx-auto">
            <form onSubmit={handleSubmit}>
                <div class="form-outline mb-4">
                    <input type="text" name="username" value={username}
                        onChange={(e) => setUsername(e.target.value)} class="form-control" />
                    <label class="form-label" for="username">User Name</label>
                </div>
                <div class="form-outline mb-4">
                    <input type="password" name="password"
                        onChange={(e) => setPassword(e.target.value)} class="form-control" />
                    <label class="form-label" for="password">Password</label>
                </div>
                <button type="submit" value="Submit" class="btn btn-primary btn-block mb-4">Sign in</button>
            </form></div>
    )
};

export default Login;