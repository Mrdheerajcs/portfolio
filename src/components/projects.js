import React from "react";

const Projects = () => {
  const projects = [
    { title: "E-Commerce Website", description: "React & Spring Boot" },
    { title: "Document Management System", description: "React & Java" },
    { title: "Rocket Chat Clone", description: "React & MongoDB" },
  ];

  return (
    <section section id="projects" className="py-16 bg-gray-800 text-white">
      <div className="container mx-auto px-8">
        <h2 className="text-3xl font-bold text-center mb-8">Projects</h2>
        <div className="grid md:grid-cols-3 gap-6">
          {projects.map((project, index) => (
            <div
              key={index}
              className="bg-gray-900 p-6 rounded-lg shadow-lg hover:shadow-xl"
            >
              <h3 className="text-xl font-bold">{project.title}</h3>
              <p className="text-gray-400">{project.description}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default Projects;
