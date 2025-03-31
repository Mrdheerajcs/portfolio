import React, { useState, useEffect } from "react";
import { API_HOST, PORTFOLIO_EMAIL } from "../ApiConfig/ApiConfig";
import axios from "axios";

const Education = () => {
    const [educations, setEducations] = useState([]);


    useEffect(() => {
        fetchPortfolio();
    }, []);

    const fetchPortfolio = async () => {

        try {
            const response = await axios.get(`${API_HOST}/portfolio/getByEmail/${PORTFOLIO_EMAIL}`, {
            });

            if (response.status === 200) {
                setEducations(response.data?.response?.educations || []);
            }
        } catch (error) {
            console.error("Error fetching UserPortfolios details:", error.response || error);
        }
    };

    return (
        <section id="education" className="py-16 bg-gray-400">
            <div className="container mx-auto px-8">
                <h2 className="text-3xl font-bold text-center mb-8">My Educations</h2>
                <div className="grid md:grid-cols-3 gap-6">
                    {educations.map((edu, index) => (
                        <div
                            key={index}
                            className="bg-white p-6 rounded-lg shadow-lg hover:shadow-xl"
                        >
                            <h1>University: {edu.boardName}</h1>
                            <h2>Institute: {edu.institutionName}</h2>
                            <h3>Course: {edu.degree}</h3>
                            <h4>Details: {edu.description}</h4>
                            <h5>Starting: {edu.startYear}</h5>
                            <h6>Completed: {edu.endYear}</h6>
                        </div>

                    ))}
                </div>
            </div>
        </section>
    );
};

export default Education;
