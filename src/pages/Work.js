import React from "react";
import { useNavigate } from "react-router-dom";


const Work = () => {
  const navigate = useNavigate();


  const handleHome = () => {
    navigate("/");
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-indigo-500 to-blue-400 text-white font-sans">
      <header className="py-6 bg-indigo-700 shadow-lg">
        <h1 className="text-3xl font-bold text-center">
          Full-Stack Developer Roadmap
        </h1>
      </header>
      <main className="container mx-auto px-4 py-10">
        <section>
          <h2 className="text-2xl font-semibold text-center mb-8">
            Your Journey to Full-Stack Development
          </h2>
          <div className="grid lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-8">
            {/* Step 1: Frontend Development */}
            <div className="bg-white text-black rounded-lg shadow-lg p-6 transform transition duration-300 hover:scale-105">
              <h3 className="text-xl font-semibold mb-4 text-indigo-600">
                Frontend Development
              </h3>
              <p>
                Start by mastering HTML, CSS, and JavaScript. Learn React.js for
                building dynamic user interfaces.
              </p>
              <ul className="mt-4 space-y-2">
                <li>✅ HTML, CSS Basics</li>
                <li>✅ JavaScript ES6+</li>
                <li>✅ React.js Components, Hooks</li>
                <li>✅ Tailwind CSS for Styling</li>
              </ul>
            </div>
            {/* Step 2: Backend Development */}
            <div className="bg-white text-black rounded-lg shadow-lg p-6 transform transition duration-300 hover:scale-105">
              <h3 className="text-xl font-semibold mb-4 text-indigo-600">
                Backend Development
              </h3>
              <p>
                Build robust APIs with Java and Spring Boot. Learn to structure
                data and business logic effectively.
              </p>
              <ul className="mt-4 space-y-2">
                <li>✅ Java Basics</li>
                <li>✅ Spring Boot Framework</li>
                <li>✅ RESTful APIs</li>
                <li>✅ Authentication with Spring Security</li>
              </ul>
            </div>
            {/* Step 3: Database Management */}
            <div className="bg-white text-black rounded-lg shadow-lg p-6 transform transition duration-300 hover:scale-105">
              <h3 className="text-xl font-semibold mb-4 text-indigo-600">
                Database Management
              </h3>
              <p>
                Learn PostgreSQL for storing and querying data. Understand
                relationships and database optimization.
              </p>
              <ul className="mt-4 space-y-2">
                <li>✅ PostgreSQL Basics</li>
                <li>✅ Database Relationships</li>
                <li>✅ SQL Queries</li>
                <li>✅ Optimization Techniques</li>
              </ul>
            </div>
          </div>
        </section>
        {/* Final Section */}
        <section className="mt-16 text-center">
          <h2 className="text-2xl font-semibold mb-4">Ready to Start?</h2>
          <p className="mb-8">
            Take the first step today and become a professional full-stack
            developer.
          </p>
          <button onClick={handleHome} className="bg-indigo-700 hover:bg-indigo-600 text-white font-bold py-2 px-6 rounded-full transition-all duration-300">
            Go To Home
          </button>
        </section>
      </main>
      <footer className="py-4 bg-indigo-800 text-center">
        <p className="text-sm">
          © 2024 Full-Stack Roadmap. Built with ❤️ by Aspiring Developers.
        </p>
      </footer>
    </div>
  );
};

export default Work;


