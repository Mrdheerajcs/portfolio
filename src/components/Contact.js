import React from "react";

const Contact = () => {
  return (
    <section id="contact" className="py-16 bg-gray-100">
      <div className="container mx-auto px-8">
        <h2 className="text-3xl font-bold text-center mb-8">Contact Me</h2>
        <form className="max-w-md mx-auto">
          <input
            type="text"
            placeholder="Your Name"
            className="w-full p-3 mb-4 border border-gray-300 rounded"
          />
          <input
            type="email"
            placeholder="Your Email"
            className="w-full p-3 mb-4 border border-gray-300 rounded"
          />
          <textarea
            placeholder="Your Message"
            className="w-full p-3 mb-4 border border-gray-300 rounded"
            rows="4"
          ></textarea>
          <button className="bg-teal-500 px-6 py-3 rounded-full text-white hover:bg-teal-600">
            Send Message
          </button>
        </form>
      </div>
    </section>
  );
};

export default Contact;
