import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import developerImage from "../Asset/developer-illustration.png";
import laptop from "../Asset/laptopWindow.png";
import laptop1 from "../Asset/laptopWindow1.png";
import mypage from "../Asset/mypage.jpg"
import tuchpad from "../Asset/tupch1.png";

const Hero = () => {
  const navigate = useNavigate();
  const [mousePosition, setMousePosition] = useState({ x: 0, y: 0 });
  const [binaryRows, setBinaryRows] = useState([]);
  const [showBinary, setShowBinary] = useState(true);

  // Track mouse movement
  useEffect(() => {
    const handleMouseMove = (e) => {
      setMousePosition({ x: e.clientX, y: e.clientY });
    };
    window.addEventListener("mousemove", handleMouseMove);
    return () => window.removeEventListener("mousemove", handleMouseMove);
  }, []);

  // Generate dynamic binary rows
  useEffect(() => {
    const generateBinaryString = () =>
      Array(24)
        .fill(0)
        .map(() => (Math.random() > 0.5 ? "1" : "0"))
        .join("");

    const generateBinaryRows = () =>
      Array(50) // Reduced number for improved rendering performance
        .fill(0)
        .map(() => generateBinaryString());

    setBinaryRows(generateBinaryRows());

    const interval = setInterval(() => {
      setBinaryRows(generateBinaryRows());
    }, 1500); // Faster update interval for dynamic effect

    return () => clearInterval(interval);
  }, []);

  const handleTouchpadClick = () => {
    setShowBinary(!showBinary); // Toggle between binary and image
  };

  const handleViewWork = () => {
    navigate("/roadmap");
  };

  return (
    <section
      id="home"
      className="relative bg-black text-white h-screen flex items-center justify-center overflow-hidden"
    >
      {/* Cursor-Following Light */}
      <div
        className="absolute w-96 h-96 rounded-full bg-gradient-to-br from-green-500 to-transparent blur-3xl pointer-events-none"
        style={{
          left: mousePosition.x - 192, // Offset to center the light
          top: mousePosition.y - 192, // Offset to center the light
        }}
      ></div>

      {/* Main Content */}
      <div className="text-center z-10">
        <h1 className="text-6xl font-bold mb-4">
          Hi, I'm <span className="text-teal-400">Dheeraj Kumar</span>
        </h1>
        <p className="text-2xl mb-6 text-gray-300">
          A Passionate{" "}
          <span className="text-teal-400">Full-Stack Developer</span>
        </p>
        <button
          onClick={handleViewWork}
          className="bg-teal-500 px-8 py-3 rounded-full text-lg shadow-lg hover:bg-teal-600 transition transform hover:-translate-y-1 hover:scale-110"
        >
          View My Work
        </button>
      </div>

      {/* Developer Illustration */}
      <img
        src={developerImage}
        alt="Developer Illustration"
        className="absolute bottom-10 right-10 w-1/5 opacity-75"
      />

      {/* Binary Code Inside Laptop Effect */}

      <img
        src={laptop}
        alt="Developer Illustration"
        className="absolute bottom-10 left-5  w-[35%] opacity-75"
      />
      <img
        src={tuchpad}
        alt="Touchpad"
        className="absolute bottom-[15.3%] left-[13.3%] w-[9%] h-[7%] opacity-75 cursor-pointer"
        onClick={handleTouchpadClick}
      />

      {/* Binary Code or Photo */}
      <div
        className="absolute top-[27%] left-[5.9%] bg-gray-900 rounded-lg border-4 border-gray-700 w-[25.4%] h-[34.7%] flex flex-col justify-center items-center overflow-hidden"
        style={{
          boxShadow: "0px 0px 50px rgba(0, 255, 0, 0.5)",
        }}
      >
        {showBinary ? (
          <div className="absolute inset-0 text-green-500 opacity-90 text-3xl leading-relaxed pointer-events-none">
            {binaryRows.map((binary, idx) => (
              <div
                key={idx}
                style={{
                  animation: `scrollBinary ${
                    1 + Math.random()
                  }s linear infinite`,
                }}
                className="whitespace-nowrap text-center"
              >
                {binary}
              </div>
            ))}
          </div>
        ) : (
          <img
            src={laptop1}
            alt="My Photo"
            className="absolute w-full h-full object-cover"
          />
        )}
      </div>
    </section>
  );
};

export default Hero;
