import { useEffect, useState } from "react";
import axios from "axios";
import { API_HOST } from "../../ApiConfig/ApiConfig";
import Popup from "../Popup";
import { FaEdit, FaSearch, FaTrash } from "react-icons/fa";

const Projects = () => {
    const [searchTerm, setSearchTerm] = useState("");
    const [itemsPerPage, setItemsPerPage] = useState(5);
    const [currentPage, setCurrentPage] = useState(1);
    const [formData, setFormData] = useState({
        name: "",
        startDate: "",
        endDate: "",
        description: "",
        techStack: "",
        repositoryUrl: "",
        liveDemoUrl: "",
        imageUrl: ""
    });
    const [editingId, setEditingId] = useState(null);
    const userId = localStorage.getItem("id");
    const token = localStorage.getItem("authToken");
    const portfolioId = localStorage.getItem("portfolio");
    const [projectsData, setProjectsData] = useState([]);
    const [deleteId, setDeleteId] = useState(null);
    const [deleteModal, setDeleteModal] = useState(false);
    const [popupMessage, setPopupMessage] = useState(null);

    useEffect(() => {
        fetchProjects();
    }, []);

    const fetchProjects = async () => {
        try {
            const response = await axios.get(`${API_HOST}/projects/getByPortfolio/${portfolioId}`, {
                headers: { Authorization: `Bearer ${token}` },
            });

            if (response.status === 200) {
                setProjectsData(response.data?.response || []);
            }
        } catch (error) {
            console.error("Error fetching projects details:", error.response || error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const saveProject = async (method, url) => {
        if (!formData.name) {
            return alert("Please fill in all required fields.");
        }

        try {
            const projectPayload = {
                ...formData,
                userId,
                portfolioId,
                status: true,
                techStack: formData.techStack.split(",").map((tech) => tech.trim())
            };

            const response = await axios({
                method,
                url,
                data: projectPayload,
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            });

            if (response.status === 200) {
                showPopup("Project saved successfully!", "success");
                fetchProjects();
                resetForm();
            }
        } catch (error) {
            console.error(`Error ${method === "post" ? "adding" : "updating"} project:`, error.response || error);
            showPopup("Failed to save project.", "error");
        }
    };

    const handleAddProject = () => {
        saveProject("post", `${API_HOST}/projects/create`);
    };

    const handleSaveEdit = () => {
        if (!editingId) return;
        saveProject("put", `${API_HOST}/projects/updateById/${editingId}`);
    };

    const handleEdit = (project) => {
        // Handle techStack array to string conversion for editing
        const projectData = {
            ...project,
            techStack: Array.isArray(project.techStack) ? project.techStack.join(", ") : project.techStack
        };
        setFormData(projectData);
        setEditingId(project.id);
    };

    const handleDelete = (project) => {
        setDeleteId(project);
        setDeleteModal(true);
    };

    const confirmHandleDelete = async () => {
        try {
            const response = await axios.delete(`${API_HOST}/projects/deleteById/${deleteId.id}`, {
                headers: { Authorization: `Bearer ${token}` },
            });

            if (response.status === 200) {
                setProjectsData((prev) => prev.filter((project) => project.id !== deleteId.id));
                showPopup("Project deleted successfully!", "success");
                setDeleteModal(false);
            }
        } catch (error) {
            console.error("Error deleting project:", error.response || error);
            showPopup("Failed to delete project.", "error");
        }
    };

    const resetForm = () => {
        setFormData({
            name: "",
            startDate: "",
            endDate: "",
            description: "",
            techStack: "",
            repositoryUrl: "",
            liveDemoUrl: "",
            imageUrl: ""
        });
        setEditingId(null);
    };

    const showPopup = (message, type = "info") => {
        setPopupMessage({ message, type });
    };

    const formatDate = (dateString) => {
        if (!dateString) return "";
        const date = new Date(dateString);
        return date.toLocaleDateString("en-GB", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
        });
    };

    const filteredProjects = projectsData.filter((project) => {
        return Object.values(project)
            .join(" ")
            .toLowerCase()
            .includes(searchTerm.toLowerCase());
    });

    const totalItems = filteredProjects.length;
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    const paginatedProjects = filteredProjects.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    return (
        <div className="p-3">
            <h2 className="text-xl font-bold">Projects</h2>
            <div className="bg-white p-4 rounded-lg shadow-sm">
                {popupMessage && (
                    <Popup
                        message={popupMessage.message}
                        type={popupMessage.type}
                        onClose={() => setPopupMessage(null)}
                    />
                )}

                <div className="mb-4 bg-slate-100 p-4 rounded-lg">
                    <div className="grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-3">
                        {["name", "startDate", "endDate", "description", "techStack", "repositoryUrl", "liveDemoUrl", "imageUrl"].map((field) => (
                            <label key={field} className="block text-md font-medium text-gray-700">
                                {field.charAt(0).toUpperCase() + field.slice(1)}
                                {field === "name" && <strong className="text-red-700">*</strong>}
                                <input
                                    type={field.includes("Date") ? "date" : "text"}
                                    placeholder={`Enter ${field}`}
                                    name={field}
                                    value={formData[field] || ""}
                                    onChange={handleInputChange}
                                    className="mt-1 block w-full p-3 border rounded-md outline-none focus:ring-2 focus:ring-blue-500"
                                />
                            </label>
                        ))}
                    </div>
                    <div className="mt-3 flex justify-start">
                        {editingId === null ? (
                            <button onClick={handleAddProject} className="bg-blue-900 text-white rounded-2xl p-2 text-sm">
                                Add Project
                            </button>
                        ) : (
                            <button onClick={handleSaveEdit} className="bg-blue-900 text-white rounded-2xl p-2 text-sm">
                                Update Project
                            </button>
                        )}
                    </div>
                </div>

                <div className="mb-4 bg-slate-100 p-4 rounded-lg flex flex-col md:flex-row justify-between items-center gap-4">
                    <div className="flex items-center bg-blue-500 rounded-lg">
                        <label htmlFor="itemsPerPage" className="mr-2 ml-2 text-white text-sm">Show:</label>
                        <select
                            id="itemsPerPage"
                            className="border rounded-r-lg p-1.5 outline-none"
                            value={itemsPerPage}
                            onChange={(e) => setItemsPerPage(Number(e.target.value))}
                        >
                            {[5, 10, 15, 20].map(option => (
                                <option key={option} value={option}>{option}</option>
                            ))}
                        </select>
                    </div>

                    <div className="relative">
                        <input
                            type="text"
                            placeholder="Search Projects"
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="p-2 border rounded-lg pl-10 outline-none"
                        />
                        <FaSearch className="absolute top-3 left-2 h-5 w-5 text-gray-500" />
                    </div>
                </div>

                <div className="overflow-x-auto md:overflow-y-auto">
                    <table className="w-full mt-4 border-collapse">
                        <thead>
                            <tr className="bg-gray-200">
                                {["Project Name", "Start Date", "End Date", "Description", "Edit", "Delete"].map((head) => (
                                    <th key={head} className="p-2 border">{head}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {paginatedProjects.map((project) => (
                                <tr key={project.id}>
                                    <td className="p-2 border">{project.name}</td>
                                    <td className="p-2 border">{formatDate(project.startDate)}</td>
                                    <td className="p-2 border">{formatDate(project.endDate)}</td>
                                    <td className="p-2 border">{project.description}</td>
                                    <td className="p-2 border">
                                        <button className="flex bg-yellow-500 px-2 py-1 text-white mr-2 rounded-md" onClick={() => handleEdit(project)}>
                                            <FaEdit className="mr-1 mt-1" />Edit
                                        </button>
                                    </td>
                                    <td className="p-2 border">
                                        <button className="flex bg-red-500 px-2 py-1 text-white rounded-md" onClick={() => handleDelete(project)}>
                                            <FaTrash className="mr-1 mt-1" />Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                {totalPages > 1 && (
                    <div className="flex justify-center mt-4">
                        <button
                            onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))}
                            disabled={currentPage === 1}
                            className={`mx-1 px-3 py-1 rounded ${currentPage === 1 ? 'bg-gray-300' : 'bg-blue-500 text-white'}`}
                        >
                            Prev
                        </button>
                        {[...Array(totalPages).keys()].map(page => (
                            <button
                                key={page}
                                onClick={() => setCurrentPage(page + 1)}
                                className={`mx-1 px-3 py-1 rounded ${currentPage === page + 1 ? 'bg-blue-700 text-white' : 'bg-blue-500 text-white'}`}
                            >
                                {page + 1}
                            </button>
                        ))}
                        <button
                            onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))}
                            disabled={currentPage === totalPages}
                            className={`mx-1 px-3 py-1 rounded ${currentPage === totalPages ? 'bg-gray-300' : 'bg-blue-500 text-white'}`}
                        >
                            Next
                        </button>
                    </div>
                )}
            </div>

            {deleteModal && (
                <div className="fixed inset-0 flex items-center justify-center bg-gray-500 bg-opacity-50">
                    <div className="bg-white p-6 rounded-lg shadow-lg">
                        <h2 className="text-xl font-semibold mb-4">
                            Confirm Delete Project
                        </h2>
                        <p>
                            Are you sure you want to delete <strong>{deleteId?.name}</strong>?
                        </p>
                        <div className="mt-6 flex justify-end">
                            <button
                                onClick={() => setDeleteModal(false)}
                                className="bg-gray-300 text-gray-800 rounded-lg px-4 py-2 mr-2"
                            >
                                Cancel
                            </button>
                            <button
                                onClick={confirmHandleDelete}
                                className="bg-red-500 text-white rounded-lg px-4 py-2"
                            >
                                Confirm
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Projects;