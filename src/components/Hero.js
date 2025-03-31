import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import developerImage from "../Asset/developer-illustration.png";
import laptop from "../Asset/laptopWindow.png";
import laptop1 from "../Asset/laptopWindow1.png";
import mypage from "../Asset/mypage.jpg"
import tuchpad from "../Asset/tupch1.png";
import { API_HOST, PORTFOLIO_EMAIL } from "../ApiConfig/ApiConfig";
import axios from "axios";

const Hero = () => {
  const navigate = useNavigate();
  const [mousePosition, setMousePosition] = useState({ x: 0, y: 0 });
  const [binaryRows, setBinaryRows] = useState([]);
  const [showBinary, setShowBinary] = useState(true);
  const [portfolio, setPortfolio] = useState([]);


  useEffect(() => {
    fetchPortfolio();
  }, []);

  const fetchPortfolio = async () => {

    try {
      const response = await axios.get(`${API_HOST}/portfolio/getByEmail/${PORTFOLIO_EMAIL}`, {
      });

      if (response.status === 200) {
        setPortfolio(response.data?.response || []);
        console.log(response.data?.response);
      }
    } catch (error) {
      console.error("Error fetching UserPortfolios details:", error.response || error);
    }
  };


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
    }, 50);
    return () => clearInterval(interval);
  }, []);

  const handleTouchpadClick = () => {
    setShowBinary(!showBinary);
  };

  const handleViewWork = () => {
    navigate("/workroot");
  };

  return (
    <section
      id="home"
      className="relative bg-black text-white h-screen flex items-center justify-center overflow-hidden"
    >
      <div
        className="absolute w-96 h-96 rounded-full bg-gradient-to-br from-green-500 to-transparent blur-3xl pointer-events-none"
        style={{
          left: mousePosition.x - 192,
          top: mousePosition.y - 192,
        }}
      ></div>

      <div className="text-center z-10">
        {/* <h1 className="text-black sm:text-red-700 md:text-green-700  lg:text-blue-700 xl:text-yellow-700 2xl:text-pink-700  text-8xl">responsive</h1> */}
        <h1 className="md:text-2xl lg:text-3xl xl:text-4xl 2xl:text-5xl font-bold mb-4">
          Hi, I'm <span className="text-teal-400">{portfolio?.user?.name}</span>
        </h1>
        <p className="md:text-lg lg:text-xl xl:text-2xl 2xl:text-2xl mb-6 text-gray-300">
          A Passionate{" "}
          <span className="text-teal-400">{portfolio?.user?.title}</span>
        </p>
        <button
          onClick={handleViewWork}
          className="bg-teal-500 px-8 py-3 rounded-full text-lg shadow-lg hover:bg-teal-600 transition transform hover:-translate-y-1 hover:scale-110"
        >
          View My Work
        </button>
      </div>

      <img
        src={developerImage}
        alt="Developer Illustration"
        className="absolute bottom-10 right-10 w-1/5 opacity-75"
      />

      <img
        src={laptop}
        alt="Developer Illustration"
        className="absolute xl:bottom-10 xl:left-5  xl:w-[35%] lg:bottom-10 lg:left-5  lg:w-[35%]lg 2xl:bottom-10 2xl:left-5  2xl:w-[35%] md:top-[9%] md:left-15  md:w-[35%] mdopacity-75"
      />
      <img
        src={tuchpad}
        alt="Touchpad"
        className="absolute xl:bottom-[15.3%] xl:left-[13.3%] xl:w-[9%] xl:h-[7%] lg:bottom-[15.3%] lg:left-[13.3%] lg:w-[9%] lg:h-[7%] 2xl:bottom-[15.3%] 2xl:left-[13.3%] 2xl:w-[9%] 2xl:h-[7%] md:top-[21.2%] md:left-[45%] md:w-[9%] md:h-[1.8%] opacity-75 cursor-pointer border border-red-600"
        onClick={handleTouchpadClick}
      />

      <div
        className="absolute xl:top-[19.1%] xl:left-[6.3%] bg-gray-900 rounded-md xl:w-[25.2%] xl:h-[34.6%] flex flex-col justify-center items-center overflow-hidden md:top-[11%] md:left-[37.4%] md:w-[25.2%] md:h-[7.2%]  2xl:top-[18.7%] 2xl:left-[5.7%] 2xl:w-[25%] 2xl:h-[38.9%] lg:top-[39.6%] lg:left-[6.7%] lg:w-[24.9%] lg:h-[27.4%]"
        style={{
          boxShadow: "0px 0px 50px rgba(0, 255, 0, 0.5)",
        }}
      >
        {showBinary ? (
          <div className="absolute inset-0 text-center py-3 text-green-500 opacity-90 text-3xl leading-relaxed pointer-events-none">
            {binaryRows.map((binary, idx) => (
              <div
                key={idx}
                style={{
                  animation: `scrollBinary ${1 + Math.random()
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
