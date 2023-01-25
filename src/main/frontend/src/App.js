import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Pagination from 'react-js-pagination';
import "./bootstrap.css";
import { Button } from 'react-bootstrap';
import { useNavigate } from "react-router-dom";

function CityList() {
  // set initial values for variables
  const [authenticated, setAuthenticated] = useState(false); 
  const [cities, setCities] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [citiesPerPage, setCitiesPerPage] = useState(10);
  const [totalCities, setTotalCities] = useState(0);
  const [searchTerm, setSearchTerm] = useState('');
  const [editingCityId, setEditingCityID] = useState(0);
  const [usrname, setUsrname] = useState(localStorage.getItem("usr") || null); //local storage value
  const [pswR, setPswR] = useState(localStorage.getItem("psw") || null); //local storage value
  const navigate = useNavigate();

  // useEffect hook to check if the user is logged in when the component is mounted
  useEffect(() => {
    // get logged user from local storage
    const loggedInUser = localStorage.getItem("authenticated");
    if (loggedInUser) {
      setAuthenticated(loggedInUser); // if user is set, set authenticated variable 
    }
  }, []);

  // if any user is not authenticated, navigate to login
  if (!authenticated) {
    navigate("/login");
  }


//fetch the cities from the API when the component is mounted or when one of the values changes
  useEffect(() => {
    const fetchCities = async () => {
      let response;
      if (searchTerm) {
        setCurrentPage(1);
        response = await axios.get(`http://localhost:8080/api/search?name=${searchTerm}`);
        setCities(response.data.content);
        setTotalCities(response.data.totalElements);
      } else {
        response = await axios.get(`http://localhost:8080/api/cities?page=${currentPage - 1}&size=${citiesPerPage}`);
        setCities(response.data.content);
        setTotalCities(response.data.totalElements);
        setCitiesPerPage(response.data.size);
      }
    };
    fetchCities();
  }, [searchTerm, currentPage, citiesPerPage]);

  // pagination component on page change
  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  // if search text is changed
  const handleSearch = (term) => {
    setSearchTerm(term);
    setCurrentPage(1);
  };

  // flag for changing edit/save button states
  const [editing, setEditing] = useState(false);

  const handleEdit = (city) => {
    setEditing(true);
    setEditingCityID(city.id);
  }

  // not does a real logout, sets the local storage user values to null and navigates to login 
  const handleLogout = () => {
    setUsrname(null);
    setPswR(null);
    setAuthenticated(false);
    localStorage.setItem("authenticated", false);
    localStorage.setItem("usr", null);
    localStorage.setItem("psw", null);
    navigate("/login");
  }

  // retrieve the new value of city name text box 
  const handleChange = (e, id) => {
    const newCities = cities.map(city => {
      if (city.id === id) {
        return { ...city, [e.target.name]: e.target.value }
      }
      return city;
    });
    setCities(newCities);
  }

  // retrieve the new value of city photo link text box 
  const handleChangePhoto = (e, id) => {
    const newCities = cities.map(city => {
      if (city.id === id) {
        return { ...city, [e.target.name]: e.target.value }
      }
      return city;
    });
    setCities(newCities);
  }

  /* save the new values of city using authenticated API call.
   if a user which has ROLE_ALOW_EDIT in the back end clicks to save button, it changes the value in the database.
   if another user clicks to save, error on authentication is logged to console. the value seems to be changed in the view
   but it does not save it to database. to see it, user have to refresh the page or re-login
   this is a bug and must be solved!
  */
  const handleSave = async (city) => {
    try {
      var session_url = `http://localhost:8080/api/${city.id}`;
      await axios.put(session_url, city, {
        auth: {
          username: usrname,
          password: pswR
        }
      }).then(function (response) {
        console.log('Authenticated');
      }).catch(function (error) {
        console.log('Error on Authentication');
      });
      const currentCities = [...cities];
      setCities(currentCities);
    } catch (err) {
      console.log(err);
    }
    setEditing(false);
  };

  // create the corresponding rendered page for browser
  return (
    <div className="container">
      <div className="row">
        <div className="col-md-7 d-flex justify-content-center">
          <h2>Welcome to the World City Album</h2>
          </div>
          <div className="col-md-7 d-flex justify-content-center">
          <h6>(This will stay here for years, will be extended and maintained, this is enterprise-grade.)</h6>
        </div>
      </div>

      <Button variant="primary" onClick={() => handleLogout()}>Change User (Current: {usrname}) </Button>

      <div className="col-md-4 d-flex justify-right">
        <input type="text" value={searchTerm} onChange={e => handleSearch(e.target.value)} placeholder="Type for searching" className="form-control" />
      </div>

      <Pagination
        activePage={currentPage}
        itemsCountPerPage={citiesPerPage}
        totalItemsCount={totalCities}
        pageRangeDisplayed={9}
        onChange={handlePageChange}
        lastPageText={'Last'}
        firstPageText={'First'}
        activeClass={'active'}
        itemClass={'page-item'}
        linkClass={'page-link'}
      />

      <table className="table table-striped table-bordered">
        <thead className="thead-light">
          <tr>
            <th className="text-center">City Name</th>
            <th className="text-center">City Photo</th>
            <th className="text-center">Action</th>
          </tr>
        </thead>
        <tbody>
          {cities.map(city => (
            <tr key={city.id}> 
              <td className="text-center">{(editing && (city.id === editingCityId)) ?
                <input type="text" name="name" value={city.name} onChange={(e) => handleChange(e, city.id)} /> :
                city.name}</td>
              <td className="text-center">
                <img src={city.photo} alt={city.name} width="200" />
                {(editing && (city.id === editingCityId)) ?
                  <textarea name="photo" value={city.photo} onChange={(e) => handleChangePhoto(e, city.id)} rows="4" cols="50" /> :
                  <br></br>
                }
              </td>
              <td className="text-center">{(editing && (city.id === editingCityId)) ?
                <Button variant="primary" onClick={() => handleSave(city)}>Save</Button> :
                <Button variant="primary" onClick={() => handleEdit(city)}>Edit</Button>
              }</td>
            </tr>
          ))}
        </tbody>
      </table>

      <Pagination
        activePage={currentPage}
        itemsCountPerPage={citiesPerPage}
        totalItemsCount={totalCities}
        pageRangeDisplayed={9}
        onChange={handlePageChange}
        lastPageText={'Last'}
        firstPageText={'First'}
        activeClass={'active'}
        itemClass={'page-item'}
        linkClass={'page-link'}
      />
      <br></br>
      <br></br>
    </div>
  );
}

export default CityList;