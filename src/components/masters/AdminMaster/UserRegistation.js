import { useEffect, useState } from "react";
import axios from "axios";
import { API_HOST } from "../../../ApiConfig/ApiConfig";
import Popup from "../../Popup";
import { FaEdit, FaEye, FaEyeSlash, FaSearch, FaTrash } from "react-icons/fa";


const UserRegistation = () => {
    const [searchTerm, setSearchTerm] = useState("");
    const [itemsPerPage, setItemsPerPage] = useState(5);
    const [currentPage, setCurrentPage] = useState(1);
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        title: "",
        phone: "",
        about: "",
        role: "",
    });
    const [editingId, setEditingId] = useState(null);
    const userId = localStorage.getItem("id");
    const token = localStorage.getItem("authToken");
    const [userRegistationData, setUserRegistationData] = useState([]);
    const [portfolioId, setPortfolioId] = useState(null);
    const [deleteId, setDeleteId] = useState([]);
    const [deleteModal, setDeleteModal] = useState(false);
    const [popupMessage, setPopupMessage] = useState(null);
    const [showNewPassword, setShowNewPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [confirmPassword, setConfirmPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");



    useEffect(() => {
        fetchUserRegistation();
    }, []);

    const fetchUserRegistation = async () => {
        try {
            const response = await axios.get(`${API_HOST}/users/getAll`, {
                headers: { Authorization: `Bearer ${token}` },
            });

            if (response.status === 200) {
                setUserRegistationData(response.data?.response || []);
                console.log(response.data);
            }
        } catch (error) {
            console.error("Error fetching UserRegistation details:", error.response || error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const saveUserRegistation = async (method, url) => {
        if (!formData.name || !formData.title) {
            return alert("Please fill in all required fields.");
        }

        try {
            const userRegistationPayload = {
                ...formData,
                status: true,
                password: newPassword
            };

            const response = await axios({
                method,
                url,
                data: userRegistationPayload,
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            });

            if (response.status === 200) {
                showPopup("UserRegistation saved successfully!", "success");
                fetchUserRegistation();
                resetForm();
            }
        } catch (error) {
            console.error(`Error ${method === "post" ? "adding" : "updating"} UserRegistation:`, error.response || error);
            showPopup(`Failed to saving UserRegistation.`, "error");

        }
    };


    const handleAddUserRegistation = () => {
        if (newPassword === confirmPassword) {
            saveUserRegistation("post", `${API_HOST}/users/create`);
        } else {
            showPopup("Password and Confirm Password does't Match", "warning");
        }
    };


    const handleSaveEdit = () => {
        if (!editingId) return;
        saveUserRegistation("put", `${API_HOST}/users/update/${editingId}`);
    };

    const handleEdit = (edu) => {
        setFormData(edu);
        setEditingId(edu.id);
    };

    const handleDelete = (edu) => {
        setDeleteId(edu);
        setDeleteModal(true);
    };

    const confirmHandleDelete = async () => {
        try {
            const response = await axios.delete(`${API_HOST}/users/deleteById/${deleteId.id}`, {
                headers: { Authorization: `Bearer ${token}` },
            });

            if (response.status === 200) {
                setUserRegistationData((prev) => prev.filter((edu) => edu.id !== deleteId.id));
                showPopup("UserRegistation Deleting successfully!", "success");
                setDeleteId(null);
                setDeleteModal(false);
            }
        } catch (error) {
            console.error("Error deleting UserRegistation:", error.response || error);
            showPopup(`Failed to Deleting UserRegistation.`, "error");
        }
    };

    const resetForm = () => {
        setFormData({
            name: "",
            email: "",
            title: "",
            phone: "",
            about: "",
        });
        setEditingId(null);
        setConfirmPassword("");
        setNewPassword("");
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

    const toggleNewPasswordVisibility = () => {
        setShowNewPassword((prev) => !prev);
    };

    const toggleConfirmPasswordVisibility = () => {
        setShowConfirmPassword((prev) => !prev);
    };


    console.log(confirmPassword, " ppp ", newPassword);

    const filteredUserRegistations = userRegistationData.filter((userRegistation) => {
        return Object.values(userRegistation)
            .join(" ")
            .toLowerCase()
            .includes(searchTerm.toLowerCase());
    });

    const totalItems = filteredUserRegistations.length;
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    const paginatedUserRegistations = filteredUserRegistations.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    return (
        <div className="p-3">
            <h2 className="text-xl font-bold">UserRegistation</h2>
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
                            { label: "Name", name: "name" },
                            { label: "Title", name: "title" },
                            { label: "Email", name: "email" },
                            { label: "Mobile No.", name: "phone" },
                            { label: "About", name: "about" },
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

                        <label className="block text-md font-medium text-gray-700">
                            Role <strong className="text-red-700">*</strong>
                            <select
                                className="mt-1 block w-full p-3 border rounded-md outline-none focus:ring-2 focus:ring-blue-500"
                                name="role"
                                onChange={handleInputChange}
                            >
                                <option value="">Select Roles</option>
                                <option value="ADMIN">ADMIN</option>
                                <option value="USER">USER</option>
                            </select>
                        </label>
                        {!editingId && (<><label className="block text-md font-medium text-gray-700">
                            Password <strong className="text-red-700">*</strong>
                            <div className="relative">
                                <input
                                    type={showNewPassword ? "text" : "password"}
                                    id="newPassword"
                                    value={newPassword}
                                    onChange={(e) => setNewPassword(e.target.value)}
                                    className="mt-1 block w-full p-3 border rounded-md outline-none focus:ring-2 focus:ring-blue-500"
                                    placeholder="Enter your password"
                                    required
                                />
                                <button
                                    type="button"
                                    className="absolute right-3 top-4"
                                    onClick={toggleNewPasswordVisibility}
                                >
                                    {showNewPassword ? (
                                        <FaEyeSlash className="text-rose-900 h-5 w-5" />
                                    ) : (
                                        <FaEye className="text-rose-900 h-5 w-5" />
                                    )}
                                </button>
                            </div>
                        </label>

                            <label className="block text-md font-medium text-gray-700">
                                Confirm Password <strong className="text-red-700">*</strong>

                                <div className="relative">
                                    <input
                                        type={showConfirmPassword ? "text" : "password"}
                                        visibility
                                        id="confirmPassword"
                                        value={confirmPassword}
                                        onChange={(e) => setConfirmPassword(e.target.value)}
                                        className="mt-1 block w-full p-3 border rounded-md outline-none focus:ring-2 focus:ring-blue-500"
                                        placeholder="Confirm your new password"
                                        required
                                    />
                                    <button
                                        type="button"
                                        className="absolute right-3 top-4"
                                        onClick={toggleConfirmPasswordVisibility}
                                    >
                                        {showConfirmPassword ? (
                                            <FaEyeSlash className="text-rose-900 h-5 w-5" />
                                        ) : (
                                            <FaEye className="text-rose-900 h-5 w-5" />
                                        )}
                                    </button>
                                </div>
                            </label></>)}

                    </div>

                    <div className="mt-3 flex justify-start">
                        {editingId === null ? (
                            <button onClick={handleAddUserRegistation} className="bg-blue-900 text-white rounded-2xl p-2 text-sm">
                                Add UserRegistation
                            </button>
                        ) : (
                            <button onClick={handleSaveEdit} className="bg-blue-900 text-white rounded-2xl p-2 text-sm">
                                Update UserRegistation
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
                            placeholder="Search UserRegistation"
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
                                {["Name", "Email", "Title", "Mobile No.", "Roles", "createdOn", "updatedOn", "Edit", "Delete"].map((head) => (
                                    <th key={head} className="p-2 border">{head}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {paginatedUserRegistations.map((edu) => (
                                <tr key={edu.id}>
                                    {["name", "email", "title", "phone", "role", "createdOn", "updatedOn"].map((key) => (
                                        <td key={key} className="p-2 border">{edu[key]}</td>
                                    ))}
                                    <td className="p-2 border">
                                        <button className="flex bg-yellow-500 px-2 py-1 text-white mr-2 rounded-md" onClick={() => handleEdit(edu)}><FaEdit className=" mr-1 mt-1" />Edit</button>
                                    </td>
                                    <td className="p-2 border">
                                        <button className="flex bg-red-500 px-2 py-1 text-white rounded-md" onClick={() => handleDelete(edu)}><FaTrash className=" mr-1 mt-1" />Delete</button>
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
                            Confirm Delete UserRegistation
                        </h2>
                        <p>
                            Are you sure! you want to Delete {" "}
                            <strong>{deleteId?.email}</strong> from<strong> {deleteId?.name}</strong>?
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

export default UserRegistation;
