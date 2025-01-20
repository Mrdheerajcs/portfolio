import React, { useState, useEffect } from "react";
import axios from "axios";
import { API_HOST } from "../ApiConfig/ApiConfig";
import downloadImage from '../Asset/download.png';

const Projects = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchProjects = async () => {
    try {
      const response = await axios.get(`${API_HOST}/projects/getAll`);
      setProjects(response.data.response);
    } catch (error) {
      console.error("Fetch projects error:", error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProjects();
  }, []);

  return (
    <section id="projects" className="py-16 bg-gray-800 text-white">
      <div className="container mx-auto px-8">
        <h2 className="text-3xl font-bold text-center mb-8">Projects</h2>
        {loading ? (
          <div className="flex justify-center items-center">
            <div className="loader animate-spin w-16 h-16 border-4 border-blue-500 border-t-transparent rounded-full"></div>
          </div>
        ) : (
          <div className="grid md:grid-cols-3 gap-6">
            {projects.map((project, index) => (
              <div
                key={index}
                className="bg-gray-900 p-6 rounded-lg shadow-lg hover:shadow-xl"
              >
                {/* Project Image */}
                {project.imageUrl && (
                  <img
                    // src={project.imageUrl}
                    src={downloadImage}
                    alt={project.name}
                    className="w-full h-48 object-cover rounded-lg mb-4"
                  />
                )}
                <h3 className="text-xl font-bold mb-2">{project.name}</h3>
                <p className="text-gray-400 mb-4">{project.description}</p>
                <div className="flex justify-between mb-4">
                  <a
                    href={project.repositoryUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded"
                  >
                    Repository
                  </a>
                  <a
                    href={project.liveDemoUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded"
                  >
                    Live Demo
                  </a>
                </div>
                {/* Tech Stack */}
                <div className="mt-4">
                  <h4 className="text-lg font-bold mb-2">Tech Stack:</h4>
                  <ul className="flex flex-wrap gap-2">
                    {(Array.isArray(project.techStack)
                      ? project.techStack
                      : project.techStack?.split(",") || []
                    ).map((tech, techIndex) => (
                      <li
                        key={techIndex}
                        className="bg-gray-700 text-white py-1 px-3 rounded-full text-sm"
                      >
                        {tech}
                      </li>
                    ))}
                  </ul>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </section>
  );
};

export default Projects;
