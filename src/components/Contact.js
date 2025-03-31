import { useState } from "react";
import axios from "axios";
import { API_HOST } from "../ApiConfig/ApiConfig";
import Popup from "./Popup";

const Contact = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    message: "",
  });

  const [isSending, setIsSending] = useState(false); // Track request state
  const [popupMessage, setPopupMessage] = useState(null);

  const user = localStorage.getItem("id");
  const portfolio = localStorage.getItem("portfolio");

  const handleAddMessage = async (e) => {
    e.preventDefault(); // Prevent page reload

    if (!formData.name || !formData.email || !formData.message) {
      showPopup("Please fill in all required fields.", "warning");
      return;
    }

    setIsSending(true); // Disable button while sending

    try {
      const messagePayload = {
        ...formData,
        user,
        portfolio,
        status: true,
      };

      const response = await axios.post(`${API_HOST}/message/send`, messagePayload, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.status === 200) {
        showPopup("Your message was sent successfully!", "success");
        resetForm();
      }
    } catch (error) {
      showPopup("Failed to send your message. Please try again.", "error");
    } finally {
      setIsSending(false); // Re-enable button after request completes
    }
  };

  const resetForm = () => {
    setFormData({
      name: "",
      email: "",
      message: "",
    });
  };

  const showPopup = (message, type = "info") => {
    setPopupMessage({ message, type });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <section id="contact" className="py-16 bg-gray-100">
      <div className="container mx-auto px-8">
        <h2 className="text-3xl font-bold text-center mb-8">Contact Me</h2>
        {popupMessage && <Popup message={popupMessage.message} type={popupMessage.type} onClose={() => setPopupMessage(null)} />}

        <form className="max-w-md mx-auto" onSubmit={handleAddMessage}>
          <input
            type="text"
            required
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            placeholder="Your Name"
            className="w-full p-3 mb-4 border border-gray-300 rounded"
          />
          <input
            type="email"
            name="email"
            required
            value={formData.email}
            onChange={handleInputChange}
            placeholder="Your Email"
            className="w-full p-3 mb-4 border border-gray-300 rounded"
          />
          <textarea
            name="message"
            required
            value={formData.message}
            onChange={handleInputChange}
            placeholder="Your Message"
            className="w-full p-3 mb-4 border border-gray-300 rounded"
            rows="4"
          ></textarea>
          <button
            type="submit"
            disabled={isSending} // Disable button while sending
            className={`px-6 py-3 rounded-full text-white transition ${
              isSending ? "bg-gray-400 cursor-not-allowed" : "bg-teal-500 hover:bg-teal-600"
            }`}
          >
            {isSending ? "Sending..." : "Send Message"}
          </button>
        </form>
      </div>
    </section>
  );
};

export default Contact;
