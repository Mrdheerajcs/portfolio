import React from "react";

const Hero = () => {
  return (
    <section
      id="home"
      className="bg-gray-800 text-white h-screen flex items-center justify-center"
    >
      <div className="text-center">
        <h1 className="text-5xl font-bold mb-4">Hello, I'm Dheeraj Kumar</h1>
        <p className="text-xl mb-6">A Full-Stack Developer</p>
        <button className="bg-teal-500 px-6 py-3 rounded-full text-lg shadow-lg hover:bg-teal-600">
          View My Work
        </button>
      </div>
    </section>
  );
};

export default Hero;
