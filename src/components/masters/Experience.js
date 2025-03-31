import { useEffect, useState } from "react";
import axios from "axios";
import { API_HOST } from "../../ApiConfig/ApiConfig";
import Popup from "../Popup";
import {FaEdit , FaSearch, FaTrash  } from "react-icons/fa";


const Experience = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [itemsPerPage, setItemsPerPage] = useState(5);
  const [currentPage, setCurrentPage] = useState(1);
  const [formData, setFormData] = useState({
    companyName: "",
    role: "",
    startDate: "",
    endDate: "",
    description: "",
  });
  const [editingId, setEditingId] = useState(null);
  const userId = localStorage.getItem("id");
  const portfoliosId = localStorage.getItem("portfolio");
  const token = localStorage.getItem("authToken");
  const [experienceData, setExperienceData] = useState([]);
  const [portfolioId, setPortfolioId] = useState(null);
  const [deleteId, setDeleteId] = useState([]);
  const [deleteModal, setDeleteModal] = useState(false);
  const [popupMessage, setPopupMessage] = useState(null);

  useEffect(() => {
    fetchUserPortfolio();
    fetchExperience();
  }, []);

  const fetchUserPortfolio = async () => {
    try {
      if(userId){
        console.log("userxdcg");
      }
      const response = await axios.get(`${API_HOST}/portfolio/getByuser/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.status === 200) {
        setPortfolioId(response.data?.id);
      }
    } catch (error) {
      console.error("Error fetching user details:", error.response || error);
    }
  };

  const fetchExperience = async () => {
    try {
      const response = await axios.get(`${API_HOST}/experiences/getByPortfolio/${portfoliosId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.status === 200) {
        setExperienceData(response.data?.response || []);
      }
    } catch (error) {
      console.error("Error fetching Experience details:", error.response || error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const saveExperience = async (method, url) => {
    if (!formData.companyName ) {
      return alert("Please fill in all required fields.");
    }

    try {
      const experiencePayload = {
        ...formData,
        user: userId,
        portfolio: portfolioId,
        status: true,
      };

      const response = await axios({
        method,
        url,
        data: experiencePayload,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status === 200) {
        showPopup("Experience saved successfully!", "success");
        fetchExperience();
        resetForm();
      }
    } catch (error) {
      console.error(`Error ${method === "post" ? "adding" : "updating"} Experience:`, error.response || error);
      showPopup(`Failed to saving Experience.`, "error");

    }
  };

  const handleAddExperience = () => {
    saveExperience("post", `${API_HOST}/experiences/create`);
  };

  const handleSaveEdit = () => {
    if (!editingId) return;
    saveExperience("put", `${API_HOST}/experiences/updateById/${editingId}`);
  };

  const handleEdit = (exp) => {
    setFormData(exp);
    setEditingId(exp.id);
  };

  const handleDelete = (exp) => {
    setDeleteId(exp);
    setDeleteModal(true);
  };

  const confirmHandleDelete = async () => {
    try {
      const response = await axios.delete(`${API_HOST}/experiences/deleteById/${deleteId.id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.status === 200) {
        setExperienceData((prev) => prev.filter((exp) => exp.id !== deleteId.id));
        showPopup("Experience Deleting successfully!", "success");
        setDeleteId(null);
        setDeleteModal(false);
      }
    } catch (error) {
      console.error("Error deleting Experience:", error.response || error);
      showPopup(`Failed to Deleting Experience.`, "error");
    }
  };

  const resetForm = () => {
    setFormData({
      companyName: "",
      role: "",
      startDate: "",
      endDate: "",
      description: "",
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

  const filteredExperiences = experienceData.filter((experience) => {
    return Object.values(experience)
      .join(" ")
      .toLowerCase()
      .includes(searchTerm.toLowerCase());
  });

  const totalItems = filteredExperiences.length;
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  const paginatedExperiences = filteredExperiences.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  return (
    <div className="p-3">
      <h2 className="text-xl font-bold">Experience</h2>
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
            {[
              { label: "Company", name: "companyName" },
              { label: "Role", name: "role" },
              { label: "Start Year", name: "startDate" },
              { label: "End Year", name: "endDate" },
              { label: "Description", name: "description" },
            ].map(({ label, name }) => (
              <label key={name} className="block text-md font-medium text-gray-700">
                {label} <strong className="text-red-700">*</strong>
                <input
                  type="text"
                  placeholder={`Enter ${label}`}
                  name={name}
                  value={formData[name] || ""}
                  onChange={handleInputChange}
                  className="mt-1 block w-full p-3 border rounded-md outline-none focus:ring-2 focus:ring-blue-500"
                />
              </label>
            ))}
          </div>
          <div className="mt-3 flex justify-start">
            {editingId === null ? (
              <button onClick={handleAddExperience} className="bg-blue-900 text-white rounded-2xl p-2 text-sm">
                Add Experience
              </button>
            ) : (
              <button onClick={handleSaveEdit} className="bg-blue-900 text-white rounded-2xl p-2 text-sm">
                Update Experience
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
              placeholder="Search Experience"
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
                {["Company", "Role", "Start", "Completed", "Description", "Edit", "Delete"].map((head) => (
                  <th key={head} className="p-2 border">{head}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {paginatedExperiences.map((exp) => (
                <tr key={exp.id}>
                  {["companyName", "role", "startDate", "endDate", "description"].map((key) => (
                    <td key={key} className="p-2 border">{exp[key]}</td>
                  ))}
                  <td className="p-2 border">
                    <button className="flex bg-yellow-500 px-2 py-1 text-white mr-2 rounded-md" onClick={() => handleEdit(exp)}><FaEdit className=" mr-1 mt-1"/>Edit</button>
                  </td>
                  <td className="p-2 border">
                    <button className="flex bg-red-500 px-2 py-1 text-white rounded-md" onClick={() => handleDelete(exp)}><FaTrash className=" mr-1 mt-1"/>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

      </div>
      {deleteModal && (
        <div className="fixed inset-0 flex items-center justify-center bg-gray-500 bg-opacity-50">
          <div className="bg-white p-6 rounded-lg shadow-lg">
            <h2 className="text-xl font-semibold mb-4">
              Confirm Delete Experience
            </h2>
            <p>
              Are you sure! you want to Delete {" "}
              <strong>{deleteId?.role}</strong> from<strong> {deleteId?.companyName}</strong>?
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
                className="bg-blue-500 text-white rounded-lg px-4 py-2"
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

export default Experience;
