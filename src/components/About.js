import React, { useState, useEffect } from "react";
import { API_HOST, PORTFOLIO_EMAIL } from "../ApiConfig/ApiConfig";
import axios from "axios";

const About = () => {
  const [users, setUsers] = useState([]);


  useEffect(() => {
    fetchPortfolio();
  }, []);

  const fetchPortfolio = async () => {

    try {
      const response = await axios.get(`${API_HOST}/portfolio/getByEmail/${PORTFOLIO_EMAIL}`, {
      });

      if (response.status === 200) {
        setUsers(response.data?.response?.user || []);
      }
    } catch (error) {
      console.error("Error fetching UserPortfolios details:", error.response || error);
    }
  };

  return (
    <section id="about" className="py-16 bg-gray-100">
      <div className="container mx-auto px-8">
        <h2 className="text-3xl font-bold text-center mb-8">About Me</h2>
        <p className="text-lg text-gray-700 text-center">
          {users?.aboutMe}
        </p>
      </div>
    </section>
  );
};

export default About;
